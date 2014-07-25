package com.tw.kampala.androidbootcamp.activity;

import android.app.Application;
import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.tw.kampala.androidbootcamp.database.DatabaseHelper;
import com.tw.kampala.androidbootcamp.models.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import roboguice.RoboGuice;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private DatabaseHelper mockDatabaseHelper = mock(DatabaseHelper.class);

    @Before
    public void setup() {
        Application application = Robolectric.application;
        RoboGuice.setBaseApplicationInjector(application,
                RoboGuice.DEFAULT_STAGE,
                Modules.override(RoboGuice.newDefaultRoboModule(application)).with(new MyTestModule()));
    }

    @After
    public void teardown() {
        Application application = Robolectric.application;
        RoboGuice.setBaseApplicationInjector(application,
                RoboGuice.DEFAULT_STAGE,
                RoboGuice.newDefaultRoboModule(application));
    }

    @Test
    public void createTriggersCompute() throws InterruptedException, SQLException {
        Dao mockDao = mock(Dao.class);
        CloseableWrappedIterable mockWrappedIterable = mock(CloseableWrappedIterable.class);
        //Ugly
        when(mockDatabaseHelper.getDao(Item.class)).thenReturn(mockDao);
        when(mockDao.getWrappedIterable()).thenReturn(mockWrappedIterable);
        when(mockWrappedIterable.iterator()).thenReturn(new ArrayList<Item>().iterator());

        Robolectric.buildActivity(MainActivity.class).create().start();

        verify(mockDatabaseHelper).getDao(Item.class);
        verify(mockDao).getWrappedIterable();
        verify(mockWrappedIterable).iterator();
    }


    public class MyTestModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(DatabaseHelper.class).toInstance(mockDatabaseHelper);
        }
    }
}
