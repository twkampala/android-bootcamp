
package com.tw.kampala.androidbootcamp;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.tw.kampala.androidbootcamp.database.DatabaseHelper;
import com.tw.kampala.androidbootcamp.database.DatabaseHelperProvider;
import com.tw.kampala.androidbootcamp.service.api.ItemAPI;
>>>>>>> Injecting DatabaseHelper with Roboguice through a Provider
import retrofit.RestAdapter;

public class ApplicationModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DatabaseHelper.class).toProvider(DatabaseHelperProvider.class);
    }

    @Provides @Singleton
    public RestAdapter getRestAdapter() {
        return new RestAdapter.Builder()
            .setEndpoint("http://10.0.3.2:4567") // Hard coded to localhost, can change later to a dialog
            .build();
    }

    @Provides @Singleton
    public ItemApi getItemAPI(RestAdapter restAdapter) {
        return restAdapter.create(ItemApi.class);
    }

}
