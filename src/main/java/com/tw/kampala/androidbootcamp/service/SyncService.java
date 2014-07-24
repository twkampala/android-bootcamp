package com.tw.kampala.androidbootcamp.service;

import android.app.NotificationManager;
import android.content.Intent;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.tw.kampala.androidbootcamp.service.api.ItemAPI;
import roboguice.service.RoboIntentService;

public class SyncService extends RoboIntentService {

    @Inject
    NotificationManager notificationManager;

    @Inject
    Provider<ItemAPI> itemAPIProvider;

    public SyncService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        
    }
}
