package com.tw.kampala.androidbootcamp.service;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.google.inject.Inject;
import com.tw.kampala.androidbootcamp.R;
import com.tw.kampala.androidbootcamp.models.Item;
import com.tw.kampala.androidbootcamp.models.ItemIds;
import com.tw.kampala.androidbootcamp.service.api.ItemApi;
import roboguice.service.RoboIntentService;

public class SyncService extends RoboIntentService {

    public static final Integer NOTIFICATION_ID = 1000;

    @Inject
    NotificationManager notificationManager;

    @Inject
    ItemApi itemApi;

    public SyncService() {
        super("SyncService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Sync")
                .setContentText("Syncing items...")
                .setProgress(0, 0, true);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

        try {
            ItemIds itemIds = itemApi.getItems();
            int counter = 0;

            for (String id : itemIds.getIds()) {
                Item item = itemApi.getItem(id);
                builder.setProgress(itemIds.getIds().size(), ++counter, false);
                notificationManager.notify(NOTIFICATION_ID, builder.build());
            }

            builder.setContentText("Syncing complete.");
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        } catch (Exception e) {
            builder.setContentText("Sync failed");
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }

        stopSelf();
    }

}
