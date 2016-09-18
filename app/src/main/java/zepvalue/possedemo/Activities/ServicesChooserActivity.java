package zepvalue.possedemo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import zepvalue.possedemo.Models.Programmer;
import zepvalue.possedemo.Models.Service;
import zepvalue.possedemo.R;
import zepvalue.possedemo.adapter.CustomAdapter;
import zepvalue.possedemo.adapter.RecyclerItemClickListener;


public class ServicesChooserActivity extends AppCompatActivity {


    private ListView listViewNames;
    private String platform;
    private LatLng markerPosition;
    private RecyclerView recyclerView;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    ArrayList<Service> services = null;
    ArrayList<Programmer> programmers = null;
    public static Service service = null;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_chooser);


        Intent intentFromMain = getIntent();
        services  = (ArrayList<Service>) intentFromMain.getSerializableExtra("extraServices");

        markerPosition = intentFromMain.getParcelableExtra("extraMarkerPosition");

        recyclerView = (RecyclerView) findViewById(R.id.services_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        customAdapter = new CustomAdapter(this, services);
        recyclerView.setAdapter(customAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        programmers = services.get(position).getProgrammers();
                        Intent itemListIntent = new Intent(view.getContext(), ItemListActivity.class);
                        itemListIntent.putExtra("extraProgrammers", programmers);
                        service = services.get(position);
                        itemListIntent.putExtra("extraPlatform", service.getPlatform());
                        itemListIntent.putExtra("extraMarkerPosition", markerPosition);
                        view.getContext().startActivity(itemListIntent);
                    }

                    @Override public void onLongItemClick(View view, int position)
                    {
                        // do whatever
                    }
                })
        );
    }
}