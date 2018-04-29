package gmoby.android.weatherapp.Model;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by misha on 4/12/18.
 */

public interface WeatherApiService {

    @Headers("Accept: application/json")
    @GET("{version}/weather")
    Observable<CurrentWeatherApiResponse> getCurrentWeather(@Path("version") String version,
                                                            @Query("id") long id,
                                                            @Query("appid") String appid);

    @Headers("Accept: application/json")
    @GET("{version}/find")
    Observable<List<City>> getCityList(@Path("version") String version,
                                       @Query("q") String q,
                                       @Query("type") String type,
                                       @Query("cnt") int cnt,
                                       @Query("appid") String appid);
}
