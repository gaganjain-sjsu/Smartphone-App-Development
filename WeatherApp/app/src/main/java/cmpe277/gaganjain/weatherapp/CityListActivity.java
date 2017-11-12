package cmpe277.gaganjain.weatherapp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CityListActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new CityListFragment();
    }
}
