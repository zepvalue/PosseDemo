package zepvalue.possedemo;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zepvalue on 8/18/2016.
 */
public class DetailsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private MapView mapView;
    private boolean mapsSupported = true;


    private TextView nameValueTV;
    private TextView ageTV;
    private TextView weightTV;
    private TextView phoneTV;
    private ImageView avatarIV;
    private ImageView logoIV;

    private int titlesIndex;
    private Programmer programmer = null;
    private LatLng markerPosition;
    private Location location;
    private Service service;

    private ArrayList<Service> services;


    // Create a new instance of DetailsFragment, initialized to show the
    // text at 'index'.@Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        Log.d("WHATTTTTTTTTTTTTTT","DAHIUDHOUAHDS");

        super.onCreate(savedInstanceState);
        programmer = (Programmer) getArguments().get("extraProgrammerFromFragment");

        Log.d("WHATTTTTTTTTTTTTTT","DAHIUDHOUAHDS");
    }


    public static DetailsFragment newInstance(int index) {
        DetailsFragment f = new DetailsFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    // The system calls this when it's time for the fragment to draw its
    // user interface for the first time. To draw a UI for your fragment,
    // you must return a View from this method that is the root of your
    // fragment's layout. You can return null if the fragment does not
    // provide a UI.

    // We create the UI with a scrollview and text and return a reference to
    // the scoller which is then drawn to the screen

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

//        programmer   =   ((DetailsActivity)this.getActivity()).getProgrammer();


        programmer  = (Programmer) getArguments().getSerializable("extraProgrammerFromFragment");
        service        = ((DetailsActivity)this.getActivity()).getService();
        markerPosition = ((DetailsActivity)this.getActivity()).getMarkerPosition();


        View v = inflater.inflate(R.layout.fragment_user, container, false);

        avatarIV = (ImageView) v.findViewById(R.id.avatar_image_view);
        getAvatar();

        logoIV = (ImageView) v.findViewById(R.id.logo_image_view);
        getPlatform();

        nameValueTV = (TextView) v.findViewById(R.id.name_value_text_view);
        ageTV = (TextView) v.findViewById(R.id.age_value_text_view);
        weightTV = (TextView) v.findViewById(R.id.weight_value_text_view);
        phoneTV = (TextView) v.findViewById(R.id.phone_value_text_view);

        nameValueTV.setText(programmer.getName());
        ageTV.setText(programmer.getAge());
        weightTV.setText(programmer.getWeight());
        phoneTV.setText(programmer.getPhone());

        phoneTV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", programmer.getPhone(), null)); //THE POSITION WILL BE RECEIVED FROM THE TITLES ACTIVITY
                startActivity(intent);
            }
        });

        mapView = (MapView) v.findViewById(R.id.map);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    private void getAvatar()
    {
        switch (programmer.getFavColor()) {
            case "red":
                avatarIV.setImageResource(R.drawable.artist_avatar_red);
                break;
            case "blue":
                avatarIV.setImageResource(R.drawable.businessman_avatar_blue);
                break;
            case "black":
                avatarIV.setImageResource(R.drawable.artist_avatar_black);
                break;
            case "aqua":
                avatarIV.setImageResource(R.drawable.businessman_avatar_aqua);
                break;
            case "green":
                avatarIV.setImageResource(R.drawable.businessman_avatar_green);
                break;
            case "purple":
                avatarIV.setImageResource(R.drawable.businessman_avatar_purple);
                break;
            case "brown":
                avatarIV.setImageResource(R.drawable.artist_avatar_brown);
                break;

        }
    }

    private void getPlatform()
    {

        switch(service.getPlatform())
        {
            case "iOS":
                logoIV.setImageResource(R.drawable.apple_logo);
                break;
            case "Android":
                logoIV.setImageResource(R.drawable.android_logo);
                break;
            case "Ruby":
                logoIV.setImageResource(R.drawable.ruby_logo);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        float zoom = 5;
        map.addMarker(new MarkerOptions().position(markerPosition).title(programmer.getName()));
        map.moveCamera(CameraUpdateFactory.newLatLng(getLocationFromAddress(getActivity(), location.getAddress())));
        map.animateCamera(CameraUpdateFactory.zoomTo(zoom), 1000, null);
    }

    private LatLng getLocationFromAddress(Context context, String strAddress) {

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

}
