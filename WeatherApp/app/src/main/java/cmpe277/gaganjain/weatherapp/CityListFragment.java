package cmpe277.gaganjain.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shauryamittal.breezy.WeatherDetailsActivity;
import com.example.shauryamittal.breezy.WeatherResult;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by gaganjain on 11/10/17.
 */

public class CityListFragment extends Fragment {

    private static final String TAG = "CityListFragment";

    private RecyclerView mCityRecyclerView;
    private CityAdapter mAdapter;
    private String mCurrentTemperature;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "inside onCreate()");
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_list, container, false);

        mCityRecyclerView = (RecyclerView) view
                .findViewById(R.id.city_recycler_view);
        mCityRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }*/
        Log.d(TAG, "inside onCreateView()");

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "inside onResume()");
        updateUI();
    }

    private void updateUI() {
        CityLab cityLab = CityLab.get(getActivity());

        Log.d(TAG, "inside updateUI()");
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);

        if(sharedPref != null){
            Log.d(TAG, "sharedPref != null");
            Log.d(TAG, "Value in sharedPref: " + sharedPref.getAll());
            Map<String, ?> allEntries = sharedPref.getAll();
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                Log.d(TAG, entry.getKey() + ": " + entry.getValue().toString());
                String city = entry.getKey();
                String latLong = (String)entry.getValue();
                String lat = latLong.split(",")[0];
                String lon = latLong.split(",")[1];
                //String current_temp = getWeatherData(lat, lon);
                //Log.d(TAG, "Current temp for " + city + current_temp);
                cityLab.addCity(new City(city, Double.parseDouble(lat), Double.parseDouble(lon), "--", "--"));
            }
        }
        List<City> cities = cityLab.getCities();
        if (mAdapter == null) {
            mAdapter = new CityAdapter(cities);
            mCityRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    public String getWeatherData(double lat, double lon){

        Log.d(TAG, "inside getWeatherData()" + "Latitude :" + lat + "Longitude :" + lon);
        String apiKey = "9c358b9bde40e6e012c0c19c37f752d3";

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url ="http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&units=imperial&APPID=" + apiKey;
        Log.d(TAG, "URL " + url);
//http://api.openweathermap.org/data/2.5/weather?lat=37.3382082&lon=-121.8863286&units=imperial&APPID=9c358b9bde40e6e012c0c19c37f752d3
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        CurrentWeather weather = new CurrentWeather(response);
                        mCurrentTemperature = String.valueOf(weather.getCurrentTemp());
                        Log.v("City ", weather.getCityName());
                        Log.v("Current Temp ", weather.getCurrentTemp() + "");
                        Log.v("Low Temp ", weather.getLowTemp() + "");
                        Log.v("High Temp ", weather.getHighTemp() + "");
                        Log.v("Weather Condition", weather.getWeatherCondition());
                        Log.v("Humidity", weather.getHumidity() + "");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                        Log.v("ERROR:", error.toString());

                    }
                });
        Log.d(TAG, "Current Temperature inside getWeather(): "+ mCurrentTemperature);
        queue.add(jsObjRequest);
        return mCurrentTemperature;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_city_list, menu);

        //MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        /*if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_city:
                Intent intent = new Intent(getActivity(), NewCityActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class CityHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private City mCity;

        private TextView mCurrentTimeView;
        private TextView mCityNameView;
        private TextView mCurrentTemperatureView;

        public CityHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_city, parent, false));
            itemView.setOnClickListener(this);

            mCurrentTimeView = (TextView) itemView.findViewById(R.id.current_time);
            mCityNameView = (TextView) itemView.findViewById(R.id.city_name);
            mCurrentTemperatureView = (TextView) itemView.findViewById(R.id.city_temperature);

        }

        public void bind(City city) {

            mCity = city;
            Log.d(TAG, "Before binding :" + city.toString());
            //city.setCurrentTemperature(getWeatherData(city.getLatitude(), city.getLongitude()));
            Log.d(TAG, "inside getWeatherData()" + "Latitude :" + city.getLatitude() + "Longitude :" + city.getLongitude());
            String apiKey = "9c358b9bde40e6e012c0c19c37f752d3";

            RequestQueue queue = Volley.newRequestQueue(getActivity());
            String url ="http://api.openweathermap.org/data/2.5/weather?lat=" + city.getLatitude() + "&lon=" + city.getLongitude() + "&units=imperial&APPID=" + apiKey;
            Log.d(TAG, "URL " + url);
//http://api.openweathermap.org/data/2.5/weather?lat=37.3382082&lon=-121.8863286&units=imperial&APPID=9c358b9bde40e6e012c0c19c37f752d3
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            CurrentWeather weather = new CurrentWeather(response);
                            mCurrentTemperatureView.setText(String.valueOf(weather.getCurrentTemp()));
                            //mCurrentTemperature = String.valueOf(weather.getCurrentTemp());
                            Log.v("City ", weather.getCityName());
                            Log.v("Current Temp ", weather.getCurrentTemp() + "");
                            Log.v("Low Temp ", weather.getLowTemp() + "");
                            Log.v("High Temp ", weather.getHighTemp() + "");
                            Log.v("Weather Condition", weather.getWeatherCondition());
                            Log.v("Humidity", weather.getHumidity() + "");
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub

                            Log.v("ERROR:", error.toString());

                        }
                    });
            queue.add(jsObjRequest);
            city.setCurrentTemperature(mCurrentTemperatureView.getText().toString());
            Log.d(TAG, "City after binding : " + city.toString());
            mCurrentTimeView.setText(String.valueOf(city.getLocalTime()));
            mCityNameView.setText(city.getCityName());
            //mCurrentTemperatureView.setText(String.valueOf(city.getCurrentTemperature()));
        }

        @Override
        public void onClick(View view) {
            //TODO Shaurya's activity (CityViewActivity)
            //Intent intent = newIntent(getActivity(), .getId());
            //startActivity(intent);
            Intent intent = new Intent(getActivity(), WeatherResult.class);
            intent.putExtra("cityName", mCity.getCityName());
            intent.putExtra("latitude", mCity.getLatitude());
            intent.putExtra("longitude", mCity.getLongitude());
            startActivity(intent);
        }
    }

    private class CityAdapter extends RecyclerView.Adapter<CityHolder> {

        private List<City> mCities;

        public CityAdapter(List<City> cities) {
            mCities = cities;
        }

        @Override
        public CityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CityHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CityHolder holder, int position) {
            City city = mCities.get(position);
            //city.setCurrentTemperature(getWeatherData(city.getLatitude(), city.getLongitude()));
            holder.bind(city);
        }

        @Override
        public int getItemCount() {
            return mCities.size();
        }
    }
}
