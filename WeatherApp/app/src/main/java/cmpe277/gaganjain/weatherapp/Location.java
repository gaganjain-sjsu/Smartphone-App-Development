package cmpe277.gaganjain.weatherapp;

/**
 * Created by shauryamittal on 10/31/17.
 */

public class Location {
    private String mCityName;
    private double mLatitude;
    private double mLongitude;

    public Location(String city, double lat, double lon){
        this.mCityName = city;
        this.mLatitude = lat;
        this.mLongitude = lon;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double mlatitude) {
        this.mLatitude = mlatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }
}
