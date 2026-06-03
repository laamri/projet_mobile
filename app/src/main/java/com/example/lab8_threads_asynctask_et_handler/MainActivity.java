package com.example.lab8_threads_asynctask_et_handler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.lang.ref.WeakReference;

/**
 * Lab 8 : Threads, AsyncTask et Handler.
 * Ce TP démontre comment garder une interface fluide en déportant les calculs
 * et chargements longs sur des threads secondaires.
 */
public class MainActivity extends AppCompatActivity {

    private TextView txtStatus;
    private ProgressBar progressBar;
    private ImageView img;
    private MaterialButton btnLoadThread;
    private MaterialButton btnCalcAsync;
    private MaterialButton btnToast;

    // Handler pour communiquer avec le UI Thread depuis un thread de fond
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Configuration des insets pour le design "Edge-to-Edge"
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Initialisation des composants
        txtStatus = findViewById(R.id.txtStatus);
        progressBar = findViewById(R.id.progressBar);
        img = findViewById(R.id.img);
        btnLoadThread = findViewById(R.id.btnLoadThread);
        btnCalcAsync = findViewById(R.id.btnCalcAsync);
        btnToast = findViewById(R.id.btnToast);

        mainHandler = new Handler(Looper.getMainLooper());

        // 2. Bouton Toast : Preuve que l'UI n'est pas bloquée
        btnToast.setOnClickListener(v ->
                Toast.makeText(this, R.string.toast_reactive, Toast.LENGTH_SHORT).show()
        );

        // 3. Approche Thread + Handler
        btnLoadThread.setOnClickListener(v -> loadImageWithThread());

        // 4. Approche AsyncTask (Pédagogique)
        btnCalcAsync.setOnClickListener(v -> new HeavyCalcTask(this).execute());
    }

    /**
     * Gère l'activation des boutons pour éviter les lancements multiples
     */
    private void setButtonsEnabled(boolean enabled) {
        btnLoadThread.setEnabled(enabled);
        btnCalcAsync.setEnabled(enabled);
    }

    /**
     * Charge une image simulée dans un Thread secondaire.
     * Ma touche : Ajout d'une animation de fondu lors de l'affichage.
     */
    private void loadImageWithThread() {
        setButtonsEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        txtStatus.setText(R.string.status_loading_thread);

        new Thread(() -> {
            try {
                // Simule une latence réseau ou disque
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Décodage de la ressource (opération coûteuse)
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

            // Retour sur le UI Thread pour modifier les vues
            mainHandler.post(() -> {
                img.setImageBitmap(bitmap);
                
                // Animation de fondu (Touch)
                img.setAlpha(0f);
                img.animate().alpha(1f).setDuration(800);
                
                progressBar.setVisibility(View.INVISIBLE);
                txtStatus.setText(R.string.status_image_loaded);
                setButtonsEnabled(true);
            });
        }).start();
    }

    /**
     * AsyncTask pour les calculs intensifs.
     * Utilisation d'une classe statique + WeakReference pour éviter les fuites de mémoire (Best Practice).
     */
    private static class HeavyCalcTask extends AsyncTask<Void, Integer, Long> {
        private final WeakReference<MainActivity> activityRef;

        HeavyCalcTask(MainActivity activity) {
            this.activityRef = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            MainActivity activity = activityRef.get();
            if (activity == null || activity.isFinishing()) return;

            activity.setButtonsEnabled(false);
            activity.progressBar.setVisibility(View.VISIBLE);
            activity.progressBar.setIndeterminate(false);
            activity.progressBar.setProgress(0);
            activity.txtStatus.setText(R.string.status_calc_running);
        }

        @Override
        protected Long doInBackground(Void... voids) {
            long result = 0;
            for (int i = 1; i <= 100; i++) {
                // Simulation d'un calcul CPU intensif
                for (int k = 0; k < 600000; k++) {
                    result += (i * k) % 19;
                }
                
                try { Thread.sleep(25); } catch (InterruptedException ignored) {}

                // Mise à jour de la barre de progression
                publishProgress(i);
            }
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            MainActivity activity = activityRef.get();
            if (activity == null || activity.isFinishing()) return;

            int progress = values[0];
            activity.progressBar.setProgress(progress);
            activity.txtStatus.setText(activity.getString(R.string.status_calc_progress, progress));
        }

        @Override
        protected void onPostExecute(Long result) {
            MainActivity activity = activityRef.get();
            if (activity == null || activity.isFinishing()) return;

            activity.progressBar.setVisibility(View.INVISIBLE);
            activity.txtStatus.setText(activity.getString(R.string.status_calc_finished, result));
            activity.setButtonsEnabled(true);
            
            Toast.makeText(activity, R.string.toast_finished, Toast.LENGTH_SHORT).show();
        }
    }
}
