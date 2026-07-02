package com.example.lab_18_viewmodel_et_livedata_en_android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

/**
 * MainActivity : Gère l'affichage et les interactions utilisateur.
 * Elle ne contient aucune variable d'état (pas de int count).
 * Toute la donnée provient du ViewModel.
 */
public class MainActivity extends AppCompatActivity {

    private CounterViewModel viewModel;
    private TextView tvCount;
    private Button btnIncrement, btnDecrement, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des vues avec findViewById (conformément aux consignes)
        tvCount = findViewById(R.id.tvCount);
        btnIncrement = findViewById(R.id.btnIncrement);
        btnDecrement = findViewById(R.id.btnDecrement);
        btnReset = findViewById(R.id.btnReset);

        /*
         * 1. Récupération (ou création) du ViewModel.
         * On utilise ViewModelProvider pour que l'instance survive à la rotation de l'écran.
         * Si l'Activity est recréée, ViewModelProvider retournera l'instance existante.
         */
        viewModel = new ViewModelProvider(this).get(CounterViewModel.class);

        /*
         * 2. Observation du LiveData (Lifecycle-aware).
         * L'Activity "écoute" les changements de la valeur du compteur.
         * Le paramètre 'this' permet à LiveData de savoir quand l'Activity est détruite
         * pour arrêter d'envoyer des mises à jour (évite les crashs et fuites mémoire).
         */
        viewModel.getCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer newCount) {
                // Mise à jour automatique de l'interface utilisateur
                tvCount.setText(String.valueOf(newCount));
            }
        });

        /*
         * 3. Gestion des événements.
         * L'Activity se contente de transmettre les actions de l'utilisateur au ViewModel.
         * Elle ne sait pas COMMENT le compteur est incrémenté, elle sait juste qu'elle doit
         * demander au ViewModel de le faire.
         */
        btnIncrement.setOnClickListener(v -> viewModel.increment());
        btnDecrement.setOnClickListener(v -> viewModel.decrement());
        btnReset.setOnClickListener(v -> viewModel.reset());
    }
}
