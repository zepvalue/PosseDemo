package zepvalue.possedemo.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import zepvalue.possedemo.Models.Location;
import zepvalue.possedemo.Models.Service;
import zepvalue.possedemo.R;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener
{

    private GoogleMap map;
    private DataManager store = null;
    public static String extraServices = "extraServices";
    public static String extraMarkerPosition = "extraMarkerPosition";
    private Toolbar mToolbar;

    private ArrayList<Location> locations = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        store = DataManager.getInstance(this);
        locations = store.locations;
        setContentView(R.layout.activity_main);


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MapFragment fragmentMap = MapFragment.newInstance();
        fragmentTransaction.add(R.id.container, fragmentMap);
        fragmentTransaction.commit();
        fragmentMap.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        float zoom = 2;
        int i;

        for(i=0;i<this.locations.size();i++) {
            map.addMarker(new MarkerOptions().position(getLocationFromAddress(this, this.locations.get(i).getAddress())).title(this.locations.get(i).getAddress()));
        }
        map.moveCamera(CameraUpdateFactory.newLatLng(getLocationFromAddress(this, this.locations.get(0).getAddress())));
        map.animateCamera(CameraUpdateFactory.zoomTo(zoom), 1000, null);
        map.setOnMarkerClickListener(this);
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);

            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        String markerTitle = marker.getTitle();
        Location location = this.getLocationByAddress(markerTitle);
        ArrayList<Service> services = location.getServices();
        LatLng markerPosition = marker.getPosition();

        Intent serviceIntent = new Intent(this, ServicesChooserActivity.class);
        serviceIntent.putExtra(extraServices, services);
        serviceIntent.putExtra(extraMarkerPosition, markerPosition);
        startActivity(serviceIntent);
        return false;
    }

    public Location getLocationByAddress(String address)
    {
        for(int i = 0; i< locations.size();i++)
        {
            Location l = locations.get(i);
            if(l.getAddress().equalsIgnoreCase(address))
            {
                return l;
            }
        }
        return null;
    }
}