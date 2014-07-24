package com.tw.kampala.androidbootcamp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import com.tw.kampala.androidbootcamp.R;
import roboguice.activity.RoboActivity;

public class MainActivity extends RoboActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.tw.kampala.androidbootcamp.R.menu.main, menu);
        return true;
    }

}
