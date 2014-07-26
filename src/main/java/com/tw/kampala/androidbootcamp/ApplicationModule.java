
package com.tw.kampala.androidbootcamp;

import android.content.Context;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.tw.kampala.androidbootcamp.database.DatabaseHelper;
import com.tw.kampala.androidbootcamp.models.Item;
import com.tw.kampala.androidbootcamp.service.api.ItemAPI;
import retrofit.RestAdapter;

import java.sql.SQLException;

public class ApplicationModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides @Singleton
    public RestAdapter getRestAdapter() {
        return new RestAdapter.Builder()
            .setEndpoint("http://10.0.2.2:4567") // Hard coded to localhost, can change later to a dialog 192.168.56.1 for genymotion
            .build();
    }

    @Provides @Singleton
    public ItemAPI getItemAPI(RestAdapter restAdapter) {
        return restAdapter.create(ItemAPI.class);
    }

    @Provides
    public DatabaseHelper getDatabaseHelper(Context context) {
        return OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    @Provides
    public Dao<Item, String> getItemDAO(DatabaseHelper databaseHelper) {
        try {
            return databaseHelper.getDao(Item.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
