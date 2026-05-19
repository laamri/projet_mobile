package com.example.lab23_jni_et_protection_anti_debug_native;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import com.example.lab23_jni_et_protection_anti_debug_native.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Chargement de la bibliothèque native
    static {
        System.loadLibrary("lab23_jni_et_protection_anti_debug_native");
    }

    private ActivityMainBinding binding;

    // Déclarations des méthodes natives
    public native boolean isDebugDetected();
    public native String helloFromJNI();
    public native int factorial(int n);
    public native String stringFromJNI(); // Pour compatibilité

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Utilisation de ViewBinding comme dans le projet initial
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Appel du contrôle de sécurité natif
        boolean suspicious = isDebugDetected();

        if (suspicious) {
            binding.tvStatus.setText("État sécurité : ENVIRONNEMENT SUSPECT DÉTECTÉ");
            binding.tvStatus.setTextColor(Color.RED);

            binding.tvHello.setText("Fonction native sensible désactivée par sécurité.");
            binding.tvFact.setText("Calcul bloqué.");
        } else {
            binding.tvStatus.setText("État sécurité : OK");
            binding.tvStatus.setTextColor(Color.parseColor("#2E7D32")); // Vert foncé

            // Utilisation des fonctions natives si l'environnement est sain
            binding.tvHello.setText(helloFromJNI());

            int n = 10;
            int result = factorial(n);
            if (result >= 0) {
                binding.tvFact.setText("Factorielle de " + n + " (via JNI) = " + result);
            } else {
                binding.tvFact.setText("Erreur dans le calcul natif.");
            }
        }
    }
}
