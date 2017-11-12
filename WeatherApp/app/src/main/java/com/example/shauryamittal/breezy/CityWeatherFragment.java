package com.example.shauryamittal.breezy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import cmpe277.gaganjain.weatherapp.R;


/**
 * Created by shauryamittal on 11/2/17.
 */

public class CityWeatherFragment extends Fragment {

    public static final String INDEX = "index";

    private int mPageNumber;

    private int index;

    CurrentWeather currentWeather;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */

    public static CityWeatherFragment create(int index) {
        CityWeatherFragment fragment = new CityWeatherFragment();
        Bundle args = new Bundle();
        args.putInt(INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    public CityWeatherFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getArguments().getInt(INDEX);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.activity_weather_result, container, false);

        // Set the title view to show the page number.
        Location location = SavedLocations.locations.get(index);


        ((TextView) rootView.findViewById(R.id.result_city)).setText(location.getCityName());
        ((TextView) rootView.findViewById(R.id.result_temp)).setText(Math.round(location.getCurrentWeather().getCurrentTemp()) + "");
        ((TextView) rootView.findViewById(R.id.result_high)).setText(location.getCurrentWeather().getHighTemp() + "");
        ((TextView) rootView.findViewById(R.id.result_low)).setText(location.getCurrentWeather().getLowTemp() + "");

        WeatherResult result = new WeatherResult();
        //result.getHourlyWeather(SavedLocations.locations.get(index).getLatitude(), SavedLocations.locations.get(index).getLongitude());


        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }


}
