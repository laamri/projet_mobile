package com.example.lab16_matriser_les_services_dans_une_apk;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tvTemps, tvStatus;
    private Button btnStart, btnStop, btnPause;
    private ChronometreService chronometreService;
    private boolean isBound = false;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private static final int PERMISSION_REQUEST_CODE = 101;

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ChronometreService.LocalBinder binder = (ChronometreService.LocalBinder) service;
            chronometreService = binder.getService();
            isBound = true;
            updateUIState();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            chronometreService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        View root = findViewById(R.id.main);
        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        tvTemps = findViewById(R.id.tvTemps);
        tvStatus = findViewById(R.id.tvStatus);
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);

        btnStart.setOnClickListener(v -> checkPermissionAndStart());
        btnPause.setOnClickListener(v -> togglePause());
        btnStop.setOnClickListener(v -> stopChronometre());

        // Update UI every second
        handler.post(updateRunnable);
    }

    private final Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            if (isBound && chronometreService != null && chronometreService.isRunning()) {
                tvTemps.setText(chronometreService.formatTemps(chronometreService.getSecondes()));
                updateUIState();
            }
            handler.postDelayed(this, 1000);
        }
    };

    private void checkPermissionAndStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
            } else {
                startChronometreService();
            }
        } else {
            startChronometreService();
        }
    }

    private void startChronometreService() {
        Intent intent = new Intent(this, ChronometreService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private void togglePause() {
        if (isBound && chronometreService != null) {
            Intent intent = new Intent(this, ChronometreService.class);
            if (chronometreService.isPaused()) {
                intent.setAction("RESUME");
            } else {
                intent.setAction("PAUSE");
            }
            startService(intent);
            updateUIState();
        }
    }

    private void stopChronometre() {
        Intent intent = new Intent(this, ChronometreService.class);
        intent.setAction("STOP");
        startService(intent);

        if (isBound) {
            unbindService(connection);
            isBound = false;
        }
        chronometreService = null;
        tvTemps.setText("00:00");
        tvStatus.setText("Arrêté");
        updateUIState();
    }

    private void updateUIState() {
        if (chronometreService != null && chronometreService.isRunning()) {
            btnStart.setVisibility(View.GONE);
            btnPause.setVisibility(View.VISIBLE);
            btnStop.setVisibility(View.VISIBLE);

            if (chronometreService.isPaused()) {
                btnPause.setText("REPRENDRE");
                tvStatus.setText("En pause");
            } else {
                btnPause.setText("PAUSE");
                tvStatus.setText("En cours...");
            }
        } else {
            btnStart.setVisibility(View.VISIBLE);
            btnPause.setVisibility(View.GONE);
            btnStop.setVisibility(View.GONE);
            tvStatus.setText("Prêt");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startChronometreService();
            } else {
                Toast.makeText(this, "Permission refusée pour les notifications", Toast.LENGTH_SHORT).show();
                startChronometreService();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, ChronometreService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(connection);
            isBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(updateRunnable);
        super.onDestroy();
    }
}
