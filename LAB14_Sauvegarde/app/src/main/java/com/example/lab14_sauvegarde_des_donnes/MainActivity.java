package com.example.lab14_sauvegarde_des_donnes;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.widget.SwitchCompat;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// Importation explicite de la classe R du namespace configuré
import com.example.securestoragejava.R;

import com.example.securestoragejava.cache.CacheStore;
import com.example.securestoragejava.external.ExternalAppFilesStore;
import com.example.securestoragejava.files.InternalTextStore;
import com.example.securestoragejava.files.StudentsJsonStore;
import com.example.securestoragejava.model.Student;
import com.example.securestoragejava.prefs.AppPrefs;
import com.example.securestoragejava.prefs.SecurePrefs;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SecureStorageLab";
    private final List<String> langs = Arrays.asList("fr", "en", "ar");

    private EditText etName;
    private EditText etToken;
    private Spinner spLang;
    private SwitchCompat swDark;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // On utilise le layout activity_main.xml
        setContentView(R.layout.activity_main);

        // Initialisation des vues (IDs correspondants à activity_main.xml)
        etName = findViewById(R.id.etName);
        etToken = findViewById(R.id.etToken);
        spLang = findViewById(R.id.spLang);
        swDark = findViewById(R.id.swDark);
        tvResult = findViewById(R.id.tvResult);

        setupLangSpinner();

        // Configuration des boutons
        findViewById(R.id.btnSavePrefs).setOnClickListener(v -> savePrefs());
        findViewById(R.id.btnLoadPrefs).setOnClickListener(v -> loadPrefsToUi());
        findViewById(R.id.btnSaveJson).setOnClickListener(v -> saveJsonFile());
        findViewById(R.id.btnLoadJson).setOnClickListener(v -> loadJsonFile());
        findViewById(R.id.btnExternal).setOnClickListener(v -> testExternalStorage());
        findViewById(R.id.btnClear).setOnClickListener(v -> clearAll());

        // Chargement automatique au démarrage
        loadPrefsToUi();
    }

    private void setupLangSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, langs);
        spLang.setAdapter(adapter);
    }

    private void savePrefs() {
        String name = etName.getText().toString().trim();
        String lang = langs.get(Math.max(0, spLang.getSelectedItemPosition()));
        String theme = swDark.isChecked() ? "dark" : "light";

        AppPrefs.save(this, name, lang, theme, false);

        String token = etToken.getText().toString();
        if (!token.isEmpty()) {
            try {
                SecurePrefs.saveToken(this, token);
            } catch (Exception e) {
                tvResult.setText("Erreur sécurité : " + e.getMessage());
                return;
            }
        }
        
        Toast.makeText(this, "Données enregistrées pour " + (name.isEmpty() ? "Anonyme" : name), Toast.LENGTH_SHORT).show();
        
        // MA TOUCHE : Rafraîchir l'affichage immédiatement après la sauvegarde
        loadPrefsToUi();
    }

    private void loadPrefsToUi() {
        AppPrefs.Triple triple = AppPrefs.load(this);
        etName.setText(triple.name);
        swDark.setChecked("dark".equals(triple.theme));
        int idx = langs.indexOf(triple.lang);
        spLang.setSelection(idx >= 0 ? idx : 0);

        int tokenLen = 0;
        try {
            String token = SecurePrefs.loadToken(this);
            tokenLen = (token != null) ? token.length() : 0;
        } catch (Exception ignored) {}

        // Affichage amélioré du résultat
        String displayName = triple.name.isEmpty() ? "(vide)" : triple.name;
        tvResult.setText(String.format(Locale.getDefault(), 
                "RÉSULTATS DU CHARGEMENT :\n\n👤 Nom : %s\n🌐 Langue : %s\n🌙 Thème : %s\n🔑 Token : %d caractères", 
                displayName, triple.lang, triple.theme, tokenLen));
    }

    private void saveJsonFile() {
        try {
            List<Student> list = Arrays.asList(
                new Student(1, "Amina", 20),
                new Student(2, "Omar", 21)
            );
            StudentsJsonStore.save(this, list);
            tvResult.setText("✅ Fichier JSON sauvegardé.");
        } catch (Exception e) {
            tvResult.setText("❌ Erreur JSON : " + e.getMessage());
        }
    }

    private void loadJsonFile() {
        List<Student> students = StudentsJsonStore.load(this);
        StringBuilder sb = new StringBuilder("📋 Liste des étudiants :\n");
        if (students.isEmpty()) {
            sb.append("(vide)");
        } else {
            for (Student s : students) {
                sb.append("- ").append(s.name).append(" (").append(s.age).append(" ans)\n");
            }
        }
        tvResult.setText(sb.toString());
    }

    private void testExternalStorage() {
        try {
            String path = ExternalAppFilesStore.write(this, "export.txt", "Ceci est un test externe.");
            tvResult.setText("📂 Fichier créé dans :\n" + path);
        } catch (Exception e) {
            tvResult.setText("❌ Erreur externe : " + e.getMessage());
        }
    }

    private void clearAll() {
        AppPrefs.clear(this);
        try { SecurePrefs.clear(this); } catch (Exception ignored) {}
        StudentsJsonStore.delete(this);
        CacheStore.purge(this);
        
        etName.setText("");
        etToken.setText("");
        swDark.setChecked(false);
        tvResult.setText("🧹 Toutes les données ont été réinitialisées.");
    }
}
