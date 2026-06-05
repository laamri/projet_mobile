package com.example.lab21_capteurs_embarqus_android;

import android.hardware.Sensor;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.lab21_capteurs_embarqus_android.fragments.ActivityRecognitionFragment;
import com.example.lab21_capteurs_embarqus_android.fragments.CompassFragment;
import com.example.lab21_capteurs_embarqus_android.fragments.MotionSensorFragment;
import com.example.lab21_capteurs_embarqus_android.fragments.SensorGraphFragment;
import com.example.lab21_capteurs_embarqus_android.fragments.SensorsListFragment;
import com.example.lab21_capteurs_embarqus_android.fragments.StepCounterFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Gestion du bouton retour avec OnBackPressedDispatcher (recommandé pour Android 13+)
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    // Désactive le callback pour laisser le système gérer le retour (fermer l'app)
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });

        if (savedInstanceState == null) {
            openFragment(new SensorsListFragment());
            navigationView.setCheckedItem(R.id.menu_sensors);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_sensors) {
            openFragment(new SensorsListFragment());
        } else if (id == R.id.menu_temperature) {
            openFragment(SensorGraphFragment.newInstance(
                    Sensor.TYPE_AMBIENT_TEMPERATURE,
                    "Température ambiante",
                    "FIRST_VALUE"));
        } else if (id == R.id.menu_humidity) {
            openFragment(SensorGraphFragment.newInstance(
                    Sensor.TYPE_RELATIVE_HUMIDITY,
                    "Humidité relative",
                    "FIRST_VALUE"));
        } else if (id == R.id.menu_proximity) {
            openFragment(SensorGraphFragment.newInstance(
                    Sensor.TYPE_PROXIMITY,
                    "Capteur de proximité",
                    "FIRST_VALUE"));
        } else if (id == R.id.menu_magnetic) {
            openFragment(SensorGraphFragment.newInstance(
                    Sensor.TYPE_MAGNETIC_FIELD,
                    "Champ magnétique",
                    "MAGNITUDE"));
        } else if (id == R.id.menu_accelerometer) {
            openFragment(MotionSensorFragment.newInstance(
                    Sensor.TYPE_ACCELEROMETER,
                    "Accéléromètre : x, y, z"));
        } else if (id == R.id.menu_gravity) {
            openFragment(MotionSensorFragment.newInstance(
                    Sensor.TYPE_GRAVITY,
                    "Gravité : x, y, z"));
        } else if (id == R.id.menu_gyroscope) {
            openFragment(MotionSensorFragment.newInstance(
                    Sensor.TYPE_GYROSCOPE,
                    "Gyroscope : rad/s"));
        } else if (id == R.id.menu_steps) {
            openFragment(new StepCounterFragment());
        } else if (id == R.id.menu_compass) {
            openFragment(new CompassFragment());
        } else if (id == R.id.menu_activity) {
            openFragment(new ActivityRecognitionFragment());
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}