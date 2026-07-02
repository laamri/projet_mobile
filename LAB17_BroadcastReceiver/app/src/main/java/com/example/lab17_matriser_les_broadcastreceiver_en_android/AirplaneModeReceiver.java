package com.example.lab17_matriser_les_broadcastreceiver_en_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AirplaneModeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Cette méthode est appelée À CHAQUE fois qu'un broadcast correspondant arrive
        if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
            
            // Récupère l'état du mode avion (true = activé, false = désactivé)
            boolean isAirplaneOn = intent.getBooleanExtra("state", false);
            
            String message = isAirplaneOn 
                ? "Mode Avion ACTIVÉ - Plus de connexion !" 
                : "Mode Avion DÉSACTIVÉ - Connexions rétablies";
            
            // Toast pour voir le résultat immédiatement
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }
}
