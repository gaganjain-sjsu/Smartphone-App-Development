package cmpe277.gaganjain.weatherapp;

/**
 * Created by gaganjain on 11/10/17.
 */

public class City {

    private String mCityName;
    private double mLatitude;
    private double mLongitude;
    private String mLocalTime;
    private String mCurrentTemperature;

    public City(String cityName, double latitude, double longitude, String localTime, String currentTemperature) {
        mCityName = cityName;
        mLatitude = latitude;
        mLongitude = longitude;
        mLocalTime = localTime;
        mCurrentTemperature = currentTemperature;
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

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public String getLocalTime() {
        return mLocalTime;
    }

    public void setLocalTime(String localTime) {
        mLocalTime = localTime;
    }

    public String getCurrentTemperature() {
        return mCurrentTemperature;
    }

    public void setCurrentTemperature(String currentTemperature) {
        mCurrentTemperature = currentTemperature;
    }

    @Override
    public String toString() {
        return "City{" +
                "mCityName='" + mCityName + '\'' +
                ", mLatitude=" + mLatitude +
                ", mLongitude=" + mLongitude +
                ", mLocalTime='" + mLocalTime + '\'' +
                ", mCurrentTemperature='" + mCurrentTemperature + '\'' +
                '}';
    }
}
