package com.example.shauryamittal.breezy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import cmpe277.gaganjain.weatherapp.R;

public class WeatherResult extends AppCompatActivity {

    TextView cityName;
    TextView temp;
    TextView highTemp;
    TextView lowTemp;
    Intent intent = getIntent();
    CurrentWeather currentWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_result);

        cityName = (TextView) findViewById(R.id.result_city);
        temp = (TextView) findViewById(R.id.result_temp);
        highTemp = (TextView) findViewById(R.id.result_high);
        lowTemp = (TextView) findViewById(R.id.result_low);

        intent = getIntent();

        cityName.setText(intent.getStringExtra("cityName"));

        getCurrentWeatherData(intent.getDoubleExtra("latitude", 0.0), intent.getDoubleExtra("longitude", 0.0));

        getHourlyWeather(intent.getDoubleExtra("latitude", 0.0), intent.getDoubleExtra("longitude", 0.0));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.dropdown, menu);
        if(cityInList(intent.getStringExtra("id"))){
            menu.findItem(R.id.addCity).setEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.addCity){

            Location newLocation = new Location(intent.getStringExtra("cityName"),
                    intent.getDoubleExtra("latitude", 0.0),
                    intent.getDoubleExtra("longitude", 0.0));

            newLocation.setId(intent.getStringExtra("id"));

            newLocation.setCurrentWeather(currentWeather);

            SavedLocations.locations.add(newLocation);

            Toast.makeText(getApplicationContext(), intent.getStringExtra("cityName") + " saved", Toast.LENGTH_SHORT).show();

            item.setEnabled(false);

            Log.v("City Saved", intent.getStringExtra("cityName"));
        }

        if(id == R.id.home){

            onBackPressed();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void getCurrentWeatherData(double lat, double lon){

        String apiKey = "9c358b9bde40e6e012c0c19c37f752d3";

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&units=imperial&APPID=" + apiKey;



        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        currentWeather = new CurrentWeather(response);

                        temp.setText(Math.round(currentWeather.getCurrentTemp()) + "");
                        highTemp.setText(currentWeather.getHighTemp() + "");
                        lowTemp.setText(currentWeather.getLowTemp() + "");

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                        Log.v("ERROR:", error.toString());

                    }
                });
        queue.add(jsObjRequest);
    }

    void getHourlyWeather(final double lat, final double lon){

        String apiKey = "9c358b9bde40e6e012c0c19c37f752d3";

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&units=imperial&APPID=" + apiKey;



        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            final JSONArray list= (JSONArray) response.get("list");
                            JSONObject data =  (JSONObject) list.get(0);
                            final String utcTime = data.get("dt_txt").toString();
                            final String timeStamp = data.get("dt").toString();
                            RequestQueue googleTimezoneQueue = Volley.newRequestQueue(getApplicationContext());

                            Log.v("UTC timeStamp ", timeStamp);
                            Log.v("UTC time ", utcTime);

                            String key = "AIzaSyBb31ykX-88UrhoTTyyJhZjcestZyKGINQ";
                            String url = "https://maps.googleapis.com/maps/api/timezone/json?location="+ lat +","+ lon +"&timestamp="+ timeStamp +"&key="+key;

                            JsonObjectRequest jsObjRequest = new JsonObjectRequest

                                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                        @Override
                                        public void onResponse(JSONObject response) {

                                            int[] hourTempIds = {
                                                    R.id.hrtemp1,
                                                    R.id.hrtemp2,
                                                    R.id.hrtemp3,
                                                    R.id.hrtemp4,
                                                    R.id.hrtemp5,
                                                    R.id.hrtemp6,
                                                    R.id.hrtemp7,
                                                    R.id.hrtemp8
                                            };

                                            int[] hourConditionIds = {
                                                    R.id.condition1,
                                                    R.id.condition2,
                                                    R.id.condition3,
                                                    R.id.condition4,
                                                    R.id.condition5,
                                                    R.id.condition6,
                                                    R.id.condition7,
                                                    R.id.condition8
                                            };

                                            for (int i=0; i<8; i++){
                                                try {
                                                    String temp = ((JSONObject) ((JSONObject) list.get(i)).get("main")).get("temp").toString();
                                                    JSONArray weather = (JSONArray)((JSONObject) list.get(i)).get("weather");
                                                    String condition = (((JSONObject)weather.get(0)).get("description")).toString();

                                                    ((TextView) findViewById(hourTempIds[i])).setText(temp);
                                                    ((TextView) findViewById(hourConditionIds[i])).setText(condition);

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }


                                            ArrayList<String> hours = DateHour.getHours(utcTime, response);


                                                ((TextView) findViewById(R.id.hr1)).setText(hours.get(0));
                                                ((TextView) findViewById(R.id.hr2)).setText(hours.get(1));
                                                ((TextView) findViewById(R.id.hr3)).setText(hours.get(2));
                                                ((TextView) findViewById(R.id.hr4)).setText(hours.get(3));
                                                ((TextView) findViewById(R.id.hr5)).setText(hours.get(4));
                                                ((TextView) findViewById(R.id.hr6)).setText(hours.get(5));
                                                ((TextView) findViewById(R.id.hr7)).setText(hours.get(6));
                                                ((TextView) findViewById(R.id.hr8)).setText(hours.get(7));


                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // TODO Auto-generated method stub

                                            Log.v("ERROR:", error.toString());

                                        }
                                    });

                            googleTimezoneQueue.add(jsObjRequest);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                        Log.v("ERROR:", error.toString());

                    }
                });
        queue.add(jsObjRequest);
    }

    public boolean cityInList(String id){
        for (int i=0; i<SavedLocations.locations.size(); i++){
            if(SavedLocations.locations.get(i).getId().equals(id)) return true;
        }
        return false;
    }

}
