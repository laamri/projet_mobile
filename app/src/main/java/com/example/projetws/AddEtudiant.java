package com.example.projetws;



import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.*;
import com.example.projetws.beans.Etudiant;
import java.util.*;

public class AddEtudiant extends AppCompatActivity {

    private EditText nom, prenom;
    private Spinner ville;
    private RadioButton m, f;
    private MaterialButton btnAdd;
    private ProgressBar progressBar;
    private RequestQueue requestQueue;

    // ✨ Double projet/projet vu ton arborescence
    private static final String INSERT_URL =
            "http://10.0.2.2/projet/ws/createEtudiant.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_etudiant);

        // Liaison des vues
        nom         = findViewById(R.id.nom);
        prenom      = findViewById(R.id.prenom);
        ville       = findViewById(R.id.ville);
        m           = findViewById(R.id.m);
        f           = findViewById(R.id.f);
        btnAdd      = findViewById(R.id.add);
        progressBar = findViewById(R.id.progressBar);

        requestQueue = Volley.newRequestQueue(this);

        btnAdd.setOnClickListener(v -> {
            if (validerFormulaire()) {
                envoyerEtudiant();
            }
        });
    }

    // ✨ Validation avant envoi
    private boolean validerFormulaire() {
        if (nom.getText().toString().trim().isEmpty()) {
            nom.setError("Le nom est obligatoire");
            nom.requestFocus();
            return false;
        }
        if (prenom.getText().toString().trim().isEmpty()) {
            prenom.setError("Le prénom est obligatoire");
            prenom.requestFocus();
            return false;
        }
        return true;
    }

    private void envoyerEtudiant() {
        // ✨ Afficher le loader, désactiver le bouton
        progressBar.setVisibility(View.VISIBLE);
        btnAdd.setEnabled(false);

        StringRequest request = new StringRequest(
                Request.Method.POST,
                INSERT_URL,

                // ✅ Succès
                response -> {
                    progressBar.setVisibility(View.GONE);
                    btnAdd.setEnabled(true);

                    try {
                        // ✨ Parser notre JSON enrichi { "message": "...", "etudiants": [...] }
                        JsonObject json      = JsonParser.parseString(response).getAsJsonObject();
                        String message       = json.get("message").getAsString();
                        JsonArray tableau    = json.getAsJsonArray("etudiants");

                        // Parser chaque étudiant
                        Gson gson = new Gson();
                        List<Etudiant> liste = new ArrayList<>();
                        for (JsonElement el : tableau) {
                            liste.add(gson.fromJson(el, Etudiant.class));
                        }

                        // ✨ Snackbar de succès avec le nombre total
                        Snackbar.make(btnAdd,
                                "✅ " + message + " | Total : " + liste.size(),
                                Snackbar.LENGTH_LONG).show();

                        // Vider les champs après ajout ✨
                        nom.setText("");
                        prenom.setText("");
                        m.setChecked(true);

                    } catch (Exception e) {
                        afficherErreur("Erreur de parsing JSON : " + e.getMessage());
                    }
                },

                // ❌ Erreur réseau
                error -> {
                    progressBar.setVisibility(View.GONE);
                    btnAdd.setEnabled(true);
                    afficherErreur("Erreur réseau : " + error.getMessage());
                }

        ) {
            @Override
            protected Map<String, String> getParams() {
                String sexe = m.isChecked() ? "homme" : "femme";
                Map<String, String> params = new HashMap<>();
                params.put("nom",    nom.getText().toString().trim());
                params.put("prenom", prenom.getText().toString().trim());
                params.put("ville",  ville.getSelectedItem().toString());
                params.put("sexe",   sexe);
                return params;
            }
        };

        requestQueue.add(request);
    }

    // ✨ Méthode centralisée pour les erreurs
    private void afficherErreur(String msg) {
        Snackbar.make(btnAdd, "❌ " + msg, Snackbar.LENGTH_LONG)
                .setBackgroundTint(getColor(android.R.color.holo_red_dark))
                .show();
    }
}
