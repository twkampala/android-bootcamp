package com.tw.kampala.androidbootcamp.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.tw.kampala.androidbootcamp.R;
import com.tw.kampala.androidbootcamp.service.api.ItemAPI;
import roboguice.service.RoboIntentService;

public class SyncService extends RoboIntentService {

    public static final Integer NOTIFICATION_ID = 1000;

    @Inject
    NotificationManager notificationManager;

    @Inject
    Provider<ItemAPI> itemAPIProvider;

    public SyncService() {
        super("SyncService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Notification notification = new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle("Sync")
            .setContentText("Syncing items...")
            .build();
        notificationManager.notify(NOTIFICATION_ID, notification);

        stopSelf();
    }
}
