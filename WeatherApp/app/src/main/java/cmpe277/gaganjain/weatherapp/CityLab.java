package cmpe277.gaganjain.weatherapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaganjain on 11/10/17.
 */

public class CityLab {

    private static CityLab sCityLab;

    private List<City> mCities;

    public static CityLab get(Context context) {
        if (sCityLab == null) {
            sCityLab = new CityLab(context);
        }

        return sCityLab;
    }

    private CityLab(Context context) {
        mCities = new ArrayList<>();
    }

    public void addCity(City c) {
        for(City city: mCities){
            if(city.getCityName().equals(c.getCityName())){
                return;
            }
        }
        mCities.add(c);
    }

    public List<City> getCities() {
        return mCities;
    }

    public City getCity(String cityName) {
        for (City city : mCities) {
            if (city.getCityName().equals(cityName)) {
                return city;
            }
        }

        return null;
    }
}
