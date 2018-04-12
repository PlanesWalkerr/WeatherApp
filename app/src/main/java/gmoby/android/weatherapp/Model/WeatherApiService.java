package gmoby.android.weatherapp.Model;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by misha on 4/12/18.
 */

public interface WeatherApiService {

    @Headers("Accept: application/json")
    @GET("{version}/weather")
    Observable<CurrentWeatherApiResponse> getCurrentWeather(@Path("version") String version,
                                  @Query("id") int id,);
}
