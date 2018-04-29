package gmoby.android.weatherapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigManager {

    SharedPreferences sharedPreferences;
    Context context;
    SharedPreferences.Editor editor;
    static final String PREFER_NAME = "Settings";
    static final String CITY_NAME = "cityName";
    static final String CITY_ID = "cityId";

    public ConfigManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getCityName() {
        return sharedPreferences.getString(CITY_NAME, null);
    }

    public Long getCityId() {
        return sharedPreferences.getLong(CITY_ID, -1);
    }

    public void setCityName(String name) {
        editor.putString(CITY_NAME, name);
        editor.commit();
    }

    public void setCityId(Long id) {
        editor.putLong(CITY_ID, id);
        editor.commit();
    }

    public boolean isEmpty() {
        return ((getCityId() == -1) && (getCityName() == null));
    }
}
