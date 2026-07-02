package com.example.securestoragejava.ui;

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

// Importation explicite de la classe R du bon package
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
        setContentView(R.layout.activity_main);

        // Initialisation des vues
        etName = findViewById(R.id.etName);
        etToken = findViewById(R.id.etToken);
        spLang = findViewById(R.id.spLang);
        swDark = findViewById(R.id.swDark);
        tvResult = findViewById(R.id.tvResult);

        setupLangSpinner();

        // Boutons
        Button btnSavePrefs = findViewById(R.id.btnSavePrefs);
        Button btnLoadPrefs = findViewById(R.id.btnLoadPrefs);
        Button btnSaveJson = findViewById(R.id.btnSaveJson);
        Button btnLoadJson = findViewById(R.id.btnLoadJson);
        Button btnExternal = findViewById(R.id.btnExternal);
        Button btnClear = findViewById(R.id.btnClear);

        // Listeners
        btnSavePrefs.setOnClickListener(v -> savePrefs());
        btnLoadPrefs.setOnClickListener(v -> loadPrefsToUi());
        btnSaveJson.setOnClickListener(v -> saveJsonFile());
        btnLoadJson.setOnClickListener(v -> loadJsonFile());
        btnExternal.setOnClickListener(v -> testExternalStorage());
        btnClear.setOnClickListener(v -> clearAll());

        // Chargement initial
        loadPrefsToUi();
    }

    private void setupLangSpinner() {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, langs);
        spLang.setAdapter(adapter);
    }

    private void savePrefs() {
        String name = etName.getText().toString().trim();
        String lang = langs.get(Math.max(0, spLang.getSelectedItemPosition()));
        String theme = swDark.isChecked() ? "dark" : "light";

        boolean ok = AppPrefs.save(this, name, lang, theme, false);

        String token = etToken.getText().toString();
        if (!token.isEmpty()) {
            try {
                SecurePrefs.saveToken(this, token);
            } catch (Exception e) {
                Log.e(TAG, "Erreur chiffrement token", e);
                tvResult.setText(String.format("Erreur sécurité : %s", e.getMessage()));
                return;
            }
        }

        try {
            CacheStore.write(this, "last_action.txt", "Dernière sauvegarde : " + System.currentTimeMillis());
        } catch (Exception ignored) {}

        Log.d(TAG, "Préférences sauvegardées : name=" + name + ", lang=" + lang + ", theme=" + theme);
        tvResult.setText("Préférences et token (chiffré) sauvegardés.\nCache mis à jour.");
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
            tokenLen = token != null ? token.length() : 0;
        } catch (Exception e) {
            Log.e(TAG, "Erreur lecture token chiffré", e);
        }

        tvResult.setText(String.format(Locale.getDefault(), "Données chargées :\nNom: %s\nLangue: %s\nThème: %s\nToken (longueur): %d",
                triple.name, triple.lang, triple.theme, tokenLen));
    }

    private void saveJsonFile() {
        List<Student> students = Arrays.asList(
                new Student(1, "Alice", 22),
                new Student(2, "Bob", 23),
                new Student(3, "Charlie", 21)
        );

        try {
            StudentsJsonStore.save(this, students);
            InternalTextStore.writeUtf8(this, "metadata.txt", "Fichier JSON généré le " + new java.util.Date());
            tvResult.setText("Fichier JSON (interne) sauvegardé avec 3 étudiants.");
        } catch (Exception e) {
            tvResult.setText(String.format("Erreur stockage interne : %s", e.getMessage()));
        }
    }

    private void loadJsonFile() {
        List<Student> students = StudentsJsonStore.load(this);
        String metadata = "";
        try {
            metadata = InternalTextStore.readUtf8(this, "metadata.txt");
        } catch (Exception ignored) {}

        StringBuilder sb = new StringBuilder("Données JSON :\n");
        sb.append(metadata).append("\n\n");
        for (Student s : students) {
            sb.append("- ").append(s.name).append(" (").append(s.age).append(" ans)\n");
        }
        tvResult.setText(sb.toString());
    }

    private void testExternalStorage() {
        try {
            String path = ExternalAppFilesStore.write(this, "export_test.txt", "Contenu exporté vers stockage externe spécifique.");
            if (path != null) {
                String content = ExternalAppFilesStore.read(this, "export_test.txt");
                tvResult.setText(String.format("Stockage externe OK.\nFichier : %s\nContenu lu : %s", path, content));
            } else {
                tvResult.setText("Erreur : Stockage externe non disponible.");
            }
        } catch (Exception e) {
            tvResult.setText(String.format("Erreur stockage externe : %s", e.getMessage()));
        }
    }

    private void clearAll() {
        AppPrefs.clear(this);
        try { SecurePrefs.clear(this); } catch (Exception ignored) {}
        StudentsJsonStore.delete(this);
        InternalTextStore.delete(this, "metadata.txt");
        ExternalAppFilesStore.delete(this, "export_test.txt");
        int count = CacheStore.purge(this);

        etName.setText("");
        etToken.setText("");
        swDark.setChecked(false);
        spLang.setSelection(0);

        tvResult.setText(String.format(Locale.getDefault(), "Toutes les données ont été effacées.\nFichiers cache supprimés : %d", count));
        Toast.makeText(this, "Nettoyage effectué", Toast.LENGTH_SHORT).show();
    }
}