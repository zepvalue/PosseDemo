package zepvalue.possedemo.Activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.gms.maps.model.LatLng;

import zepvalue.possedemo.Models.Programmer;
import zepvalue.possedemo.R;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity {

    private Programmer programmer = null;
    private LatLng markerPosition;
    private String platform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        Intent intentFromTitles = getIntent();
        programmer = (Programmer) intentFromTitles.getSerializableExtra("extraProgrammer");
        platform =  intentFromTitles.getStringExtra("extraPlatform");
        markerPosition = intentFromTitles.getParcelableExtra("extraMarkerPosition");

        // Show the Up button in the action bar.
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

//         savedInstanceState is non-null when there is fragment state
//         saved from previous configurations of this activity
//         (e.g. when rotating the screen from portrait to landscape).
//         In this case, the fragment will automatically be re-added
//         to its container so we don't need to manually add it.
//         For more information, see the Fragments API guide at:

         http://developer.android.com/guide/components/fragments.html

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            Bundle arguments = new Bundle();

            arguments.putSerializable("extraProgrammer", programmer);
            arguments.putString("extraPlatform", platform);
            arguments.putParcelable("extraMarkerPosition", markerPosition);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);

            getFragmentManager().beginTransaction().add(R.id.item_detail_container, fragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
