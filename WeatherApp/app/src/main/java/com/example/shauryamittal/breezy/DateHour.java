package com.example.shauryamittal.breezy;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by shauryamittal on 11/10/17.
 */

public class DateHour {

    static ArrayList<String> getHours(String utcDateTime, JSONObject googleTimestampResponse){
        try {

            ArrayList<String> result = new ArrayList<String>();

            long rawOffset = Long.parseLong(googleTimestampResponse.get("rawOffset").toString());
            long dstOffset = Long.parseLong(googleTimestampResponse.get("dstOffset").toString());

            Log.v("rawOffset ", rawOffset + "");
            Log.v("dstOffset ", dstOffset + "");

            long minuteDifference = (rawOffset + dstOffset) / (60);

            Log.v("Minute Difference ", minuteDifference + "");


            String utcTime = utcDateTime.split(" ")[1];

            Log.v("UTC TIME ONLY ", utcTime + "");

            long utcMinutes = Long.parseLong(utcTime.split(":")[0]) * 60 + Long.parseLong(utcTime.split(":")[1]);

            Log.v("UTC TOTAL MINUTES ", utcMinutes + "");

            long totalMinutes = utcMinutes + minuteDifference;

            Log.v("TOTAL MINUTES ", totalMinutes + "");

            if(totalMinutes < 0){
                totalMinutes = (24 * 60) + totalMinutes;
            }

            Log.v("AdJUSTED TOTAL MINUTES ", totalMinutes + "");


            int actualHour = (int) totalMinutes / 60;
            int actualMinute = (int) totalMinutes % 60;

            for(int i=0; i<8; i++){
                if(actualMinute < 10){
                    result.add(actualHour + ":0" + actualMinute);
                }
                else {
                    result.add(actualHour + ":" + actualMinute);
                }
                actualHour = actualHour + 3;
                if (actualHour>23) actualHour = actualHour - 24;
            }

            return result;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
