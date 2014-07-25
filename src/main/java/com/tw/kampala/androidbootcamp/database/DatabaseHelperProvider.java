package com.tw.kampala.androidbootcamp.database;

import android.content.Context;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.j256.ormlite.android.apptools.OpenHelperManager;

public class DatabaseHelperProvider implements Provider<DatabaseHelper> {

    private Context context;

    @Inject
    public DatabaseHelperProvider(Context context) {
        this.context = context;
    }

    @Override
    public DatabaseHelper get() {
        return OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }
}
