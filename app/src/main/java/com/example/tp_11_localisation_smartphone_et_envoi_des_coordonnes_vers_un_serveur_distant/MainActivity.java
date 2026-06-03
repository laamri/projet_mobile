package com.example.tp_11_localisation_smartphone_et_envoi_des_coordonnes_vers_un_serveur_distant;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private double latitude;
    private double longitude;
    private double altitude;
    private float accuracy;
    private RequestQueue requestQueue;
    private TextView tvInfo;
    private ProgressBar progressBar;
    private Button btnStart;

    // REMPLACEZ PAR L'IP DE VOTRE SERVEUR (ex: 192.168.1.15)
    private static final String SERVER_IP = "10.0.2.2"; // IP par défaut pour l'émulateur vers localhost
    private String insertUrl = "http://" + SERVER_IP + "/localisation/createPosition.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = findViewById(R.id.tvInfo);
        progressBar = findViewById(R.id.progressBar);
        btnStart = findViewById(R.id.btnStart);
        requestQueue = Volley.newRequestQueue(this);

        btnStart.setOnClickListener(v -> {
            if (checkPermissions()) {
                startLocationUpdates();
            } else {
                requestPermissions();
            }
        });
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE
                }, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            boolean allGranted = true;
            if (grantResults.length > 0) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        allGranted = false;
                        break;
                    }
                }
            } else {
                allGranted = false;
            }

            if (allGranted) {
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Permissions refusées. L'application ne peut pas fonctionner.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startLocationUpdates() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        btnStart.setEnabled(false);
        btnStart.setText("Suivi activé...");
        tvInfo.setText("Recherche de position...");
        progressBar.setVisibility(View.VISIBLE);

        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    60000, // 1 minute
                    150,   // 150 mètres
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            altitude = location.getAltitude();
                            accuracy = location.getAccuracy();

                            updateUI();
                            addPosition(latitude, longitude);
                        }

                        @Override
                        public void onProviderEnabled(@NonNull String provider) {
                            Toast.makeText(MainActivity.this, "GPS activé", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onProviderDisabled(@NonNull String provider) {
                            Toast.makeText(MainActivity.this, "GPS désactivé", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void updateUI() {
        String msg = String.format(Locale.getDefault(),
                "Latitude : %.6f\nLongitude : %.6f\nAltitude : %.2f m\nPrécision : %.1f m",
                latitude, longitude, altitude, accuracy);
        tvInfo.setText(msg);
        progressBar.setVisibility(View.GONE);
    }

    private void addPosition(final double lat, final double lon) {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                insertUrl,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Serveur: " + response, Toast.LENGTH_SHORT).show();
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Erreur réseau: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

                params.put("latitude", String.valueOf(lat));
                params.put("longitude", String.valueOf(lon));
                params.put("date_position", sdf.format(new Date()));
                params.put("imei", getDeviceIdentifier());

                return params;
            }
        };

        requestQueue.add(request);
    }

    private String getDeviceIdentifier() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            try {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    return tm.getDeviceId();
                } else {
                    return Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                }
            } catch (Exception e) {
                return Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }
        return "Permission Denied";
    }
}
