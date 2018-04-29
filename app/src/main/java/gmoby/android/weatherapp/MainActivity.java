package gmoby.android.weatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import gmoby.android.weatherapp.Model.CurrentWeatherApiResponse;
import gmoby.android.weatherapp.Model.Database.DBHelper;
import gmoby.android.weatherapp.Model.Weather.Weather;
import gmoby.android.weatherapp.Model.WeatherApiClient;
import gmoby.android.weatherapp.Utils.ConfigManager;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "weatherTag";
    public final static String CITY_KEY = "city";
    @BindView(R.id.city_textview)
    TextView cityTextView;
    @BindView(R.id.weather_info_textview)
    TextView weatherInfoTextView;
    @BindView(R.id.weather_icon)
    ImageView weatherIconView;
    DBHelper dbHelper;
    WeatherApiClient weatherApiClient;

    ConfigManager configManager;
    long cityId;
    String cityName;

    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;
    @BindView(R.id.progressBarHolder)
    FrameLayout progressBarHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        weatherApiClient = new WeatherApiClient("https://api.openweathermap.org/data/");

        dbHelper = new DBHelper(getApplicationContext());
        ButterKnife.bind(this);

        configManager = new ConfigManager(this);
        cityId = configManager.getCityId();
        cityName = configManager.getCityName();
        cityTextView.setText(cityName);
        getCurrentWeather(cityId);


    }


    private void getCurrentWeather(long id) {
        weatherApiClient.getCurrentWeather(id)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<CurrentWeatherApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CurrentWeatherApiResponse response) {
                        Log.d(TAG, "next");
                        fillData(response);


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.toString());

                    }

                    @Override
                    public void onComplete() {
                        outAnimation = new AlphaAnimation(1f, 0f);
                        outAnimation.setDuration(200);
                        progressBarHolder.setAnimation(outAnimation);
                        progressBarHolder.setVisibility(View.GONE);

                    }
                });
    }

    private void fillData(final CurrentWeatherApiResponse response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Weather weather = response.getWeather().get(0);
                Picasso.get()
                        .load("http://openweathermap.org/img/w/" + weather.getIcon() + ".png")
                        .into(weatherIconView);
                weatherInfoTextView.setText(weather.getMain() + "(" + weather.getDescription() + ")");
            }
        });


    }
}
