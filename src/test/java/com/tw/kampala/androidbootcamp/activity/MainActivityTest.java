package com.tw.kampala.androidbootcamp.activity;

import android.app.Application;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.util.Modules;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.tw.kampala.androidbootcamp.models.Item;
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

    private Dao<Item, String> mockDao = (Dao<Item, String>) mock(Dao.class);

    @Before
    public void setup() {
        Application application = Robolectric.application;
        RoboGuice.setBaseApplicationInjector(application,
                RoboGuice.DEFAULT_STAGE,
                Modules.override(RoboGuice.newDefaultRoboModule(application)).with(new MyTestModule()));
    }

    @Test
    public void createTriggersCompute() throws InterruptedException, SQLException {
        CloseableWrappedIterable mockWrappedIterable = mock(CloseableWrappedIterable.class);

        when(mockDao.getWrappedIterable()).thenReturn(mockWrappedIterable);
        when(mockWrappedIterable.iterator()).thenReturn(new ArrayList<Item>().iterator());

        Robolectric.buildActivity(MainActivity.class).create().start();

        verify(mockDao).getWrappedIterable();
        verify(mockWrappedIterable).iterator();
    }

    public class MyTestModule extends AbstractModule {
        @Override
        protected void configure() {
        }

        @Provides
        public Dao<Item, String> getItemDAO() {
            return mockDao;
        }
    }
}
