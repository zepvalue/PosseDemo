package zepvalue.possedemo;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import java.util.ArrayList;

public class TitlesActivity extends Activity {

    public ArrayList<Programmer> getProgrammers() {
        return programmers;
    }

    private ArrayList<Programmer> programmers = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intentFromServices = getIntent();
        programmers = (ArrayList<Programmer>) intentFromServices.getExtras().getSerializable("extraProgrammers");

        Bundle bundleFromServices = new Bundle();
        bundleFromServices.putSerializable("extraProgrammers", programmers);
        TitlesFragment titleFragments = new TitlesFragment();
        titleFragments.setArguments(bundleFromServices);



        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.

            // create fragment
            TitlesFragment titles = new TitlesFragment();

            // get and set the position input by user (i.e., "index")
            // which is the construction arguments for this fragment
            titles.setArguments(getIntent().getExtras());

            //
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, titles).commit();
        }
    }
}
