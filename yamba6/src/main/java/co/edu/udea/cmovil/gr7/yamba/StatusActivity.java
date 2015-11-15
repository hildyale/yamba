package co.edu.udea.cmovil.gr7.yamba;

import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;



public class StatusActivity extends SubActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yamba);
        // Check if this activity was created before
        if (savedInstanceState == null) {
            // Create a fragment
            StatusFragment fragment = new StatusFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(android.R.id.content, fragment,  fragment.getClass().getSimpleName());
            fragmentTransaction.commit();
        }
    }


}





