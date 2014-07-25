package com.tw.kampala.androidbootcamp.service;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.tw.kampala.androidbootcamp.R;
import com.tw.kampala.androidbootcamp.models.Item;
import com.tw.kampala.androidbootcamp.models.ItemIds;
import com.tw.kampala.androidbootcamp.service.api.ItemAPI;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import roboguice.service.RoboIntentService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SyncService extends RoboIntentService {

    public static final Integer NOTIFICATION_ID = 1000;
    public static final String SYNC_COMPLETE = "SYNC_COMPLETE";

    @Inject
    private NotificationManager notificationManager;

    @Inject
    private ItemAPI itemAPI;

    @Inject
    private Dao<Item, String> itemDao;

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
            ItemIds itemIds = itemAPI.getItems();
            int counter = 0;

            for (String id : itemIds.getIds()) {
                Item item = itemAPI.getItem(id);

                Response itemImage = itemAPI.getItemImage(id);
                item.setImageBytes(readFully(itemImage.getBody().in()));

                itemDao.createOrUpdate(item);

                builder.setProgress(itemIds.getIds().size(), ++counter, false);
                notificationManager.notify(NOTIFICATION_ID, builder.build());
            }

            builder.setContentText("Syncing complete.");
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        } catch (Exception e) {
            builder.setContentText("Sync failed");
            notificationManager.notify(NOTIFICATION_ID, builder.build());
            Log.e("android-bootcamp", "sync failed", e);
        }

        sendBroadcast(new Intent(SYNC_COMPLETE));

        stopSelf();
    }

    public static byte[] readFully(InputStream input) throws IOException {
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((bytesRead = input.read(buffer)) != -1)
        {
            output.write(buffer, 0, bytesRead);
        }
        return output.toByteArray();
    }

}
