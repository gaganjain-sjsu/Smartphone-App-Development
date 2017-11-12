package com.example.shauryamittal.breezy;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by shauryamittal on 11/9/17.
 */

public class LocalTime {

    int hour;
    long mTimeStamp;
    long mLocalTimeStamp;

    public LocalTime(long timeStamp){

        this.mTimeStamp = timeStamp;

    }


    void setLocalTime(Context context, double lat, double lon){

        RequestQueue queue = Volley.newRequestQueue(context);
        String key = "AIzaSyBb31ykX-88UrhoTTyyJhZjcestZyKGINQ";
        String url ="https://maps.googleapis.com/maps/api/timezone/json?location="+ lat +","+ lon +"&timestamp="+ mTimeStamp +"&key="+key;



        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            long rawOffset = Long.parseLong(response.get("rawOffset").toString());
                            long dstOffset = Long.parseLong(response.get("dstOffset").toString());
                            mLocalTimeStamp = mTimeStamp + rawOffset*1000 + dstOffset*1000;

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

    public long getLocalTimeStamp() {
        return mLocalTimeStamp;
    }



    public int getHour() {
        return hour;
    }

    public void setHour(long localTimeStamp) {
        Date date = new Date(localTimeStamp);   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        Log.v("Hour 24H Format ", calendar.get(Calendar.HOUR_OF_DAY) + ""); // gets hour in 24h format
        Log.v("Hour 12H Format ", calendar.get(Calendar.HOUR) + "");        // gets hour in 12h format
        calendar.get(Calendar.MONTH);
    }




}
