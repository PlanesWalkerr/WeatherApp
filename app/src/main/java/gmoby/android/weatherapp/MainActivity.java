package gmoby.android.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import gmoby.android.weatherapp.Model.CurrentWeatherApiResponse;
import gmoby.android.weatherapp.Model.WeatherApiClient;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "weatherTag";
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.getWeather);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeatherApiClient weatherApiClient = new WeatherApiClient("https://api.openweathermap.org/data/");
                weatherApiClient.getCurrentWeather(2172797)
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(new Observer<CurrentWeatherApiResponse>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(CurrentWeatherApiResponse response) {
                                Log.d(TAG, "next");

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "error");

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
    }
}
