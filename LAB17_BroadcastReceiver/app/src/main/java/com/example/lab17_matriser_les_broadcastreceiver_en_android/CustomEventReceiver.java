package com.example.lab17_matriser_les_broadcastreceiver_en_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomEventReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.example.receiverdemo.CUSTOM_EVENT".equals(intent.getAction())) {
            String message = intent.getStringExtra("message");
            Toast.makeText(context, "Custom reçu : " + message, Toast.LENGTH_LONG).show();
        }
    }
}
