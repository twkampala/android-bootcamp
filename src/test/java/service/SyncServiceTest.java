package service;

import android.app.Application;
import android.content.Intent;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.util.Modules;
import com.j256.ormlite.dao.Dao;
import com.tw.kampala.androidbootcamp.models.Item;
import com.tw.kampala.androidbootcamp.models.ItemIds;
import com.tw.kampala.androidbootcamp.service.SyncService;
import com.tw.kampala.androidbootcamp.service.api.ItemAPI;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import roboguice.RoboGuice;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class SyncServiceTest {

    private Dao<Item, String> mockDao = (Dao<Item, String>) mock(Dao.class);
    private ItemAPI mockAPI = mock(ItemAPI.class);

    static class TestSyncService extends SyncService {
        @Override
        public void onHandleIntent(Intent intent) {
            super.onHandleIntent(intent);
        }
    }

    @Before
    public void setUp() {
        Application application = Robolectric.application;
        RoboGuice.setBaseApplicationInjector(application,
                RoboGuice.DEFAULT_STAGE,
                Modules.override(RoboGuice.newDefaultRoboModule(application)).with(new MyTestModule()));
    }

    @Test
    public void testSyncOneItem() throws SQLException {
        Item item = Item.builder().id("test").name("Test").build();
        ItemIds itemIds = new ItemIds(Arrays.asList(new String[] { item.getId() }));
        when(mockAPI.getItems()).thenReturn(itemIds);
        when(mockAPI.getItem(item.getId())).thenReturn(item);

        TestSyncService syncService = Robolectric.buildService(TestSyncService.class).create().get();
        syncService.onHandleIntent(new Intent());

        verify(mockDao).createOrUpdate(item);
    }

    @Test
    public void testMultipleItems() throws SQLException {
        Item item1 = Item.builder().id("test1").name("Test1").build();
        Item item2 = Item.builder().id("test2").name("Test2").build();
        ItemIds itemIds = new ItemIds(Arrays.asList(new String[] { item1.getId(), item2.getId() }));
        when(mockAPI.getItems()).thenReturn(itemIds);
        when(mockAPI.getItem(item1.getId())).thenReturn(item1);
        when(mockAPI.getItem(item2.getId())).thenReturn(item2);

        TestSyncService syncService = Robolectric.buildService(TestSyncService.class).create().get();
        syncService.onHandleIntent(new Intent());

        verify(mockDao).createOrUpdate(item1);
        verify(mockDao).createOrUpdate(item2);
    }

    public class MyTestModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(ItemAPI.class).toInstance(mockAPI);
        }

        @Provides
        public Dao<Item, String> getItemDAO() {
            return mockDao;
        }
    }

}
