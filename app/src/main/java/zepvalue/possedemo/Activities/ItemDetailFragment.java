package zepvalue.possedemo.Activities;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
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

import zepvalue.possedemo.Models.Programmer;
import zepvalue.possedemo.R;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment implements OnMapReadyCallback{

    private GoogleMap googleMap;
    private MapView mapView;
    private TextView nameValueTV;
    private TextView ageTV;
    private TextView weightTV;
    private ImageView favColorIV;
    private ImageView avatarIV;
    private ImageView logoIV;
    private Programmer programmer = null;
    private LatLng markerPosition;
    private String platform;
    private ImageView phoneIV;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (orientation).
     */
    public ItemDetailFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        programmer = (Programmer) bundle.getSerializable("extraProgrammer");
        platform = bundle.getString("extraPlatform");
        markerPosition = bundle.getParcelable("extraMarkerPosition");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        avatarIV = (ImageView) rootView.findViewById(R.id.avatar_image_view);
        getAvatar();

        logoIV = (ImageView) rootView.findViewById(R.id.logo_image_view);
        getPlatform();

        nameValueTV = (TextView) rootView.findViewById(R.id.name_value_text_view);
        ageTV = (TextView) rootView.findViewById(R.id.age_value_text_view);
        weightTV = (TextView) rootView.findViewById(R.id.weight_value_text_view);
        favColorIV = (ImageView) rootView.findViewById(R.id.favColor_image_view);
        getFavColor();
        nameValueTV.setText(programmer.getName());
        ageTV.setText(programmer.getAge());
        weightTV.setText(programmer.getWeight());
        phoneIV = (ImageView) rootView.findViewById(R.id.phone_image_view);
        phoneIV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", programmer.getPhone(), null)); //THE POSITION WILL BE RECEIVED FROM THE TITLES ACTIVITY
                startActivity(intent);
            }
        });

        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);


        return rootView;
    }

    private void getAvatar()
    {
        if(programmer.getIsArtist().equals("true"))
        {
            avatarIV.setImageResource(R.drawable.artist);
        }
        else
            avatarIV.setImageResource(R.drawable.avatar);
    }

    private void getFavColor()
    {
        switch(programmer.getFavColor())
        {
            case "red":
                favColorIV.setColorFilter(Color.parseColor("#D32F2F"));
                break;
            case "blue":
                favColorIV.setColorFilter(Color.parseColor("#0D47A1"));
                break;
            case "black":
                favColorIV.setColorFilter(Color.BLACK);
                break;
            case "green":
                favColorIV.setColorFilter(Color.parseColor("#00C853"));
                break;
            case "purple":
                favColorIV.setColorFilter(Color.parseColor("#9C27B0"));
                break;
            case "aqua":
                favColorIV.setColorFilter(Color.parseColor("#00E5FF"));
                break;
            case "brown":
                favColorIV.setColorFilter(Color.parseColor("#795548"));
                break;
           default:
               favColorIV.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);


        }
    }

    private void getPlatform()
    {
        switch(platform)
        {
            case "iOS": {
                logoIV.setColorFilter(Color.GRAY);
                logoIV.setImageResource(R.drawable.apple_logo_black);

            }
            break;

            case "Android": {
                logoIV.setImageResource(R.drawable.android_logo);
                logoIV.setColorFilter(Color.parseColor("#00C853"));
            }
            break;

            case "Ruby": {
                logoIV.setColorFilter(Color.parseColor("#D32F2F"));
                logoIV.setImageResource(R.drawable.ruby_logo_black);
            }
            break;
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
        map.moveCamera(CameraUpdateFactory.newLatLng(markerPosition));
        map.animateCamera(CameraUpdateFactory.zoomTo(zoom), 1000, null);
    }
}
