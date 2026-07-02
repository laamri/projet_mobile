package com.example.lab17_matriser_les_broadcastreceiver_en_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Toast.makeText(context, "Téléphone démarré - Receiver statique activé !", Toast.LENGTH_LONG).show();
        }
    }
}
