package gmoby.android.weatherapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import gmoby.android.weatherapp.Model.City;
import gmoby.android.weatherapp.Model.Database.DBHelper;
import gmoby.android.weatherapp.Model.MessageEvent;


public class CityListService extends Service {

    private final String TAG = "cityListService";

    public static final String RESULT_OK = "0";
    public static final String RESULT_ERROR = "1";
    private String message = "";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        loadCities();
        return super.onStartCommand(intent, flags, startId);
    }

    private void loadCities() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                try {
                    // Create a URL for the desired page
                    URL url = new URL("http://openweathermap.org/help/city_list.txt");
                    String[] columns;
                    List<City> list = new ArrayList<City>();

                    // Read all the text returned by the server
                    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                    String str;
                    str = in.readLine();
                    while ((str = in.readLine()) != null) {
                        // str is one line of text; readLine() strips the newline character(s)
                        columns = str.split("\t");
                        list.add(new City(Integer.parseInt(columns[0]),
                                columns[1],
                                Double.parseDouble(columns[2]),
                                Double.parseDouble(columns[3]),
                                columns[4]));
                    }
                    in.close();
                    Log.d(TAG, list.size() + " items");
                    dbHelper.writeCities(list);

                    message = RESULT_OK;
                    stopService();

                } catch (MalformedURLException e)

                {
                    message = RESULT_ERROR;
                } catch (IOException e)

                {
                    message = RESULT_ERROR;
                }
            }
        }).start();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    void stopService() {
        this.stopSelf();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().post(new MessageEvent(message));
        super.onDestroy();
    }
}
