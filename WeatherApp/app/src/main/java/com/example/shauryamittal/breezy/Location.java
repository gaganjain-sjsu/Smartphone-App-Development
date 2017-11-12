package com.example.shauryamittal.breezy;

/**
 * Created by shauryamittal on 10/31/17.
 */

public class Location {

    private String mId;
    private String mCityName;
    private double mLatitude;
    private double mLongitude;
    private CurrentWeather mCurrentWeather;



    public Location(String city, double lat, double lon){
        this.mCityName = city;
        this.mLatitude = lat;
        this.mLongitude = lon;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
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

    public CurrentWeather getCurrentWeather() {
        return mCurrentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        mCurrentWeather = currentWeather;
    }
}
