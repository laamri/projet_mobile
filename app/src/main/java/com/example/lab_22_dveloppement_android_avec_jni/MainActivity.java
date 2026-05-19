package com.example.lab_22_dveloppement_android_avec_jni;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.example.lab_22_dveloppement_android_avec_jni.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityJNI";

    static {
        System.loadLibrary("native-lib");
    }

    private ActivityMainBinding binding;

    public native String helloFromJNI();
    public native int factorial(int n);
    public native String reverseString(String s);
    public native int sumArray(int[] values);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Message de bienvenue natif
        binding.tvHello.setText(helloFromJNI());

        // 2. Initialisation des tests automatiques (Étape 10)
        runGuidedTests();

        // 3. Gestion Interactive (Preuve que ce n'est pas statique !)
        setupInteractivity();

        // Bouton pour relancer l'autotest
        binding.btnRunTests.setOnClickListener(v -> runGuidedTests());
    }

    private void setupInteractivity() {
        // Calcul Dynamique du Factoriel
        binding.btnCalcFact.setOnClickListener(v -> {
            String input = binding.etFactorial.getText().toString();
            if (!input.isEmpty()) {
                try {
                    int n = Integer.parseInt(input);
                    int result = factorial(n);
                    
                    if (result == -1) {
                        binding.tvFactResult.setText("Erreur : n doit être >= 0");
                    } else if (result == -2) {
                        binding.tvFactResult.setText("Erreur : Overflow (trop grand)");
                    } else {
                        binding.tvFactResult.setText(String.format(Locale.getDefault(), "Résultat : %d", result));
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Veuillez entrer un nombre valide", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Inversion Dynamique de Chaîne
        binding.btnDoReverse.setOnClickListener(v -> {
            String input = binding.etReverse.getText().toString();
            String result = reverseString(input);
            binding.tvReverseResult.setText(String.format("Résultat : %s", result));
        });
    }

    private void runGuidedTests() {
        StringBuilder report = new StringBuilder();
        report.append("--- RAPPORT DE TESTS (ÉTAPE 10) ---\n\n");

        // Test 1 : Valeur normale
        int res1 = factorial(10);
        appendTestResult(report, "T1 (Fact 10)", res1 == 3628800, String.valueOf(res1));

        // Test 2 : Valeur négative
        int res2 = factorial(-5);
        appendTestResult(report, "T2 (Fact -5)", res2 == -1, String.valueOf(res2));

        // Test 3 : Dépassement (Overflow)
        int res3 = factorial(20);
        appendTestResult(report, "T3 (Fact 20)", res3 == -2, String.valueOf(res3));

        // Test 4 : Chaîne vide
        String res4 = reverseString("");
        appendTestResult(report, "T4 (Rev '')", res4.equals(""), "'" + res4 + "'");

        // Test 5 : Tableau vide
        int res5 = sumArray(new int[]{});
        appendTestResult(report, "T5 (Sum [])", res5 == 0, String.valueOf(res5));

        binding.tvTestReport.setText(report.toString());
        Log.i(TAG, "Tests guidés (Étape 10) terminés avec succès.");
    }

    private void appendTestResult(StringBuilder sb, String testName, boolean success, String actual) {
        sb.append(testName)
          .append(": ")
          .append(success ? "✅ OK" : "❌ FAIL")
          .append(" (Val: ")
          .append(actual)
          .append(")\n");
    }
}
