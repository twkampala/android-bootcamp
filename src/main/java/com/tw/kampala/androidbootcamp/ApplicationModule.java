package com.tw.kampala.androidbootcamp;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.tw.kampala.androidbootcamp.service.api.ItemAPI;
import retrofit.RestAdapter;

public class ApplicationModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides @Singleton
    public RestAdapter getRestAdapter() {
        return new RestAdapter.Builder()
            .setEndpoint("http://10.0.2.2:4567")
            .build();
    }

    @Provides @Singleton
    public ItemAPI getItemAPI(RestAdapter restAdapter) {
        return restAdapter.create(ItemAPI.class);
    }

}
