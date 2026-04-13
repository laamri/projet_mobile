package com.example.lab7_galeriedestars;



import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView ivStar = findViewById(R.id.ivSplashStar);

        // Charger et lancer l'animation (res/anim/star_anim.xml)
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.star_anim);
        ivStar.startAnimation(anim);

        // Apres 3s -> MainActivity
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, 3000);
    }
}