package com.example.shauryamittal.breezy;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shauryamittal on 10/31/17.
 */

public class CurrentWeather {
    double mCurrentTemp;
    double mLowTemp;
    double mHighTemp;
    double mHumidity;
    String mCityName;
    String mWeatherCondition;

    public CurrentWeather(JSONObject response){
        try {
            this.mCityName = response.get("name").toString();
            JSONObject main = (JSONObject) response.get("main");
            this.mCurrentTemp = Double.parseDouble(main.get("temp").toString());
            this.mHighTemp = Double.parseDouble(main.get("temp_max").toString());
            this.mLowTemp = Double.parseDouble(main.get("temp_min").toString());
            this.mHumidity = Double.parseDouble(main.get("humidity").toString());
            JSONArray metaData = (JSONArray) response.get("weather");
            JSONObject conditions = (JSONObject) metaData.get(0);
            this.mWeatherCondition = conditions.get("description").toString();
        }
        catch (JSONException e){
            Log.d("Error parsing JSON", e.toString());
        }
    }

    public double getCurrentTemp() {
        return mCurrentTemp;
    }

    public double getLowTemp() {
        return mLowTemp;
    }

    public double getHighTemp() {
        return mHighTemp;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public String getCityName() {
        return mCityName;
    }

    public String getWeatherCondition() {
        return mWeatherCondition;
    }
}
