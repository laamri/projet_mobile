package com.example.projetws;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Redirige directement vers AddEtudiant
        startActivity(new Intent(this, AddEtudiant.class));
        finish(); // Ferme MainActivity pour ne pas y revenir avec le bouton Back
    }
}