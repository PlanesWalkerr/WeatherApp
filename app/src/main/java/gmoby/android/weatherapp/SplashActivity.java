package gmoby.android.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import gmoby.android.weatherapp.Model.Database.DBHelper;
import gmoby.android.weatherapp.Model.MessageEvent;
import gmoby.android.weatherapp.Utils.ConfigManager;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = "splashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadCities();

    }

    private void loadCities() {
        DBHelper dbHelper = new DBHelper(this);
        if (dbHelper.isEmpty()) {
            startService(new Intent(this, CityListService.class));
        } else {
            Log.d(TAG, "not empty");
            closeSplashScreen();
        }

    }

    private void closeSplashScreen() {

        Intent intent;
        ConfigManager configManager = new ConfigManager(this);
        if (configManager.isEmpty()) {
            intent = new Intent(this, SearchableActivity.class);
            Log.d(TAG, "going to search");
        } else {
            intent = new Intent(this, MainActivity.class);
            Log.d(TAG, "going to main");
        }

        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(MessageEvent event) {
        switch (event.message) {
            case CityListService.RESULT_OK:
                Log.d(TAG, "city list downloaded successfully");
                closeSplashScreen();
                break;
            case CityListService.RESULT_ERROR:
                Log.d(TAG, "city list downloading failed");
                break;
        }
    }
}