package com.tw.kampala.androidbootcamp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.tw.kampala.androidbootcamp.R;
import com.tw.kampala.androidbootcamp.adapter.ItemAdapter;
import com.tw.kampala.androidbootcamp.models.Item;
import com.tw.kampala.androidbootcamp.service.SyncService;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends RoboActivity {

    @InjectView(R.id.sync_button)
    Button syncButton;

    @InjectView(R.id.items_list_view)
    ListView itemsListView;

    @Inject
    private Dao<Item, String> itemDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SyncService.class);
                startService(intent);
            }
        });

        ArrayList<Item> items = new ArrayList<Item>();
        for (int i = 0; i < 10; i++) {
            items.add(Item.builder().name("User " + i).city("City").build());
        }

        final ArrayAdapter<Item> itemAdapter = new ItemAdapter(this, R.layout.item_row_layout, items);
        itemsListView.setAdapter(itemAdapter);
        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Item item = itemAdapter.getItem(position);
                Toast.makeText(MainActivity.this, String.format("Item selected %s", item.getName()), Toast.LENGTH_SHORT).show();
            }
        });

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                itemAdapter.clear();
                try {
                    itemAdapter.addAll(itemDAO.queryForAll());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new IntentFilter(SyncService.SYNC_COMPLETE));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.tw.kampala.androidbootcamp.R.menu.main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OpenHelperManager.releaseHelper(); // Can we just use ContextScope on a DatabaseHelper?
    }

}
