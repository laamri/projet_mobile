package com.example.lab16_matriser_les_services_dans_une_apk;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChronometreService extends Service {

    private final IBinder binder = new LocalBinder();
    private int secondes = 0;
    private boolean isRunning = false;
    private boolean isPaused = false;
    private ScheduledExecutorService executor;
    private static final int NOTIFICATION_ID = 1001;
    private NotificationManager notificationManager;
    private static final String CHANNEL_ID = "chrono_channel";

    public class LocalBinder extends Binder {
        public ChronometreService getService() {
            return ChronometreService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        creerNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = (intent != null) ? intent.getAction() : null;

        if ("STOP".equals(action)) {
            stopSelf();
            return START_NOT_STICKY;
        } else if ("PAUSE".equals(action)) {
            isPaused = true;
            updateNotification();
        } else if ("RESUME".equals(action)) {
            isPaused = false;
            updateNotification();
        } else if (!isRunning) {
            isRunning = true;
            isPaused = false;
            startForeground(NOTIFICATION_ID, creerNotification());
            demarrerCompteur();
        }

        return START_STICKY;
    }

    private void demarrerCompteur() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(() -> {
            if (isRunning && !isPaused) {
                secondes++;
                updateNotification();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void creerNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Chronomètre Service",
                    NotificationManager.IMPORTANCE_LOW
            );
            notificationManager.createNotificationChannel(channel);
        }
    }

    private Notification creerNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Action Arrêter
        Intent stopIntent = new Intent(this, ChronometreService.class);
        stopIntent.setAction("STOP");
        PendingIntent stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_IMMUTABLE);

        // Action Pause / Reprendre
        String pauseResumeAction = isPaused ? "RESUME" : "PAUSE";
        String pauseResumeText = isPaused ? "Reprendre" : "Pause";
        Intent pauseResumeIntent = new Intent(this, ChronometreService.class);
        pauseResumeIntent.setAction(pauseResumeAction);
        PendingIntent pauseResumePendingIntent = PendingIntent.getService(this, 1, pauseResumeIntent, PendingIntent.FLAG_IMMUTABLE);

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(isPaused ? "Chronomètre en pause" : "Chronomètre en cours")
                .setContentText("Temps écoulé : " + formatTemps(secondes))
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setContentIntent(contentIntent)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .addAction(android.R.drawable.ic_media_pause, pauseResumeText, pauseResumePendingIntent)
                .addAction(android.R.drawable.ic_menu_close_clear_cancel, "Arrêter", stopPendingIntent)
                .build();
    }

    private void updateNotification() {
        if (isRunning) {
            notificationManager.notify(NOTIFICATION_ID, creerNotification());
        }
    }

    public String formatTemps(int sec) {
        int hours = sec / 3600;
        int minutes = (sec % 3600) / 60;
        int seconds = sec % 60;
        if (hours > 0) {
            return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
    }

    public int getSecondes() {
        return secondes;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean isPaused() {
        return isPaused;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        if (executor != null) {
            executor.shutdownNow();
        }
        stopForeground(true);
        super.onDestroy();
    }
}
