package co.edu.udea.cmovil.gr7.yamba;

/**
 * Created by alejo on 29/09/15.
 */

import android.os.Bundle;

public class SettingsActivity extends SubActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState == null) {
        // Create a fragment
        SettingsFragment fragment = new SettingsFragment();
        getFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, fragment,
                        fragment.getClass().getSimpleName()).commit();
        }

     }
}



