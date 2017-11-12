package cmpe277.gaganjain.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;


public class NewCityActivity extends AppCompatActivity implements PlaceSelectionListener {

    private static final String TAG = "NewCityActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_city);
        Log.d(TAG, "inside onCreate() method");

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Register a listener to receive callbacks when a place has been selected or an error has
        // occurred.
        AutocompleteFilter cityOnlyFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        autocompleteFragment.setOnPlaceSelectedListener(this);
        autocompleteFragment.setFilter(cityOnlyFilter);
    }



    @Override
    public void onPlaceSelected(Place place) {
        String city_name = place.getName().toString();
        String latLong = String.valueOf(place.getLatLng().latitude) + ","  + String.valueOf(place.getLatLng().longitude);
        //Set<String> latLongSet = new HashSet<>();
        //latLongSet.add();
        //latLongSet.add();
        Log.d(TAG, "inside onPlaceSelected() method");
        Log.d(TAG, "Place: " + city_name + "LatLng :" + latLong);

        //Context context = NewCityActivity.this;
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);
        //SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(city_name, latLong);
        //editor.putStringSet(city_name, latLongSet);
        editor.commit();
        Log.d(TAG, "Value stored in SharedPref: " + sharedPref.getAll());

        Intent intent = new Intent(this, CityListActivity.class);
        startActivity(intent);

    }

    @Override
    public void onError(Status status) {
        Log.e("NewCityActivity", "onError: Status = " + status.toString());

        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

}
