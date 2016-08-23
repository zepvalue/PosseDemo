package zepvalue.possedemo;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
/**
 * Created by zepvalue on 8/18/2016.
 */
public class DetailsActivity extends Activity
{
    private Programmer programmer = null;
    private Service    service = null ;
    private LatLng     markerPosition = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Intent intentFromTitlesFragment = getIntent();
//        programmer = (Programmer) intentFromTitlesFragment.getExtras().getSerializable("extraProgrammer");


        Intent intentFromCustomAdapter = getIntent();
        service = (Service) intentFromCustomAdapter.getSerializableExtra("extraService");

        Intent intentFromMain = getIntent();
        markerPosition =  intentFromMain.getExtras().getParcelable("extraMarkerPosition");

        Bundle bundleFromServicesMainTitles = new Bundle();
        bundleFromServicesMainTitles.putSerializable("extraProgrammer", programmer);
        bundleFromServicesMainTitles.putSerializable("extraService" , service);
        bundleFromServicesMainTitles.putParcelable("extramarkerPosition", markerPosition);
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundleFromServicesMainTitles);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.

            // create fragment
            DetailsFragment details = new DetailsFragment();

            // get and set the position input by user (i.e., "index")
            // which is the construction arguments for this fragment
            details.setArguments(getIntent().getExtras());

            //
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, details).commit();

        }
    }

    public Service getService() {
        return service;
    }

    public Programmer getProgrammer() {
        return programmer;
    }

    public LatLng getMarkerPosition() {
        return markerPosition;
    }
}

