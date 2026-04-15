package com.example.gestionetudiants;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionetudiants.db.EtudiantService;
import com.example.gestionetudiants.model.Etudiant;

public class MainActivity extends AppCompatActivity {

    private EtudiantService service;
    private EditText etNom, etPrenom, etId;
    private TextView tvResultat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service    = new EtudiantService(this);
        etNom      = findViewById(R.id.etNom);
        etPrenom   = findViewById(R.id.etPrenom);
        etId       = findViewById(R.id.etId);
        tvResultat = findViewById(R.id.tvResultat);

        // ── Ajouter ──────────────────────────────────────────
        findViewById(R.id.btnValider).setOnClickListener(v -> {
            String nom    = etNom.getText().toString().trim();
            String prenom = etPrenom.getText().toString().trim();
            if (nom.isEmpty() || prenom.isEmpty()) {
                Toast.makeText(this, "Remplissez Nom et Prénom", Toast.LENGTH_SHORT).show();
                return;
            }
            long id = service.ajouter(new Etudiant(nom, prenom));
            Toast.makeText(this, "Ajouté avec id=" + id, Toast.LENGTH_SHORT).show();
            etNom.setText(""); etPrenom.setText("");
        });

        // ── Chercher ─────────────────────────────────────────
        findViewById(R.id.btnChercher).setOnClickListener(v -> {
            String idStr = etId.getText().toString().trim();
            if (idStr.isEmpty()) {
                Toast.makeText(this, "Entrez un ID", Toast.LENGTH_SHORT).show();
                return;
            }
            Etudiant e = service.findById(Integer.parseInt(idStr));
            if (e == null) {
                tvResultat.setText("Aucun étudiant trouvé pour id=" + idStr);
            } else {
                tvResultat.setText(e.getNom() + " " + e.getPrenom());
            }
        });

        // ── Supprimer ────────────────────────────────────────
        findViewById(R.id.btnSupprimer).setOnClickListener(v -> {
            String idStr = etId.getText().toString().trim();
            if (idStr.isEmpty()) {
                Toast.makeText(this, "Entrez un ID", Toast.LENGTH_SHORT).show();
                return;
            }
            int rows = service.supprimer(Integer.parseInt(idStr));
            String msg = rows > 0
                    ? "Étudiant id=" + idStr + " supprimé"
                    : "Aucun étudiant avec id=" + idStr;
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            tvResultat.setText(msg);
            etId.setText("");
        });
    }
}