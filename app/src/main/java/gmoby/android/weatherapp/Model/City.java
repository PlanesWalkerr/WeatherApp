package gmoby.android.weatherapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("country_code")
    @Expose
    private String countryCode;

    @SerializedName("lng")
    @Expose
    private double lng;
    @SerializedName("lat")
    @Expose
    private double lat;

    public City() {
    }

    public City(long id, String name, double lat, double lng, String countryCode) {
        this.id = id;
        this.name = name;
        this.countryCode = countryCode;
        this.lng = lng;
        this.lat = lat;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", lng=" + lng +
                ", lat=" + lat +
                '}';
    }
}
