package gmoby.android.weatherapp.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by misha on 4/12/18.
 */

public class WeatherApiClient {

    private String version = "2.5";
    private String APPID = "3c5c2e0e442064f94968e75cef43689e";

    private WeatherApiService weatherApiService;

    public WeatherApiClient(String baseUrl) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = builder.build();
        weatherApiService = retrofit.create(WeatherApiService.class);

    }

    public Observable<CurrentWeatherApiResponse> getCurrentWeather(int id){
        return weatherApiService.getCurrentWeather(version, id, APPID);

    }
}
