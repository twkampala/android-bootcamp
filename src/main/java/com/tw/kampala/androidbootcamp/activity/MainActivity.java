package com.tw.kampala.androidbootcamp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.tw.kampala.androidbootcamp.R;
import com.tw.kampala.androidbootcamp.adapter.ItemAdapter;
import com.tw.kampala.androidbootcamp.models.Item;
import com.tw.kampala.androidbootcamp.service.SyncService;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends RoboActivity {

    public static final String ITEM = "item";
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


        final ArrayAdapter<Item> itemAdapter = new ItemAdapter(this, R.layout.item_row_layout, loadItems());
        itemsListView.setAdapter(itemAdapter);
        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Item item = itemAdapter.getItem(position);
                Intent getDetailIntent = new Intent(MainActivity.this, DetailActivity.class);
                getDetailIntent.putExtra(ITEM, item);
                startActivity(getDetailIntent);
            }
        });

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                itemAdapter.clear();
                itemAdapter.addAll(loadItems());
            }
        }, new IntentFilter(SyncService.SYNC_COMPLETE));
    }

    private List<Item> loadItems() {
        List<Item> items;
        try {
            items = itemDAO.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
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
