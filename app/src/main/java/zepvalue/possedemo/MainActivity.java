package zepvalue.possedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*************VIEWS************/
    private TextView nameValTV;
    private TextView favColorValTV;
    private TextView ageValTV;
    private TextView weightValTV;
    private TextView phoneValTV;
    private TextView isArtistValTV;
    private TextView locationValTV;
    /******************************/

    /**********EXPANDABLE LIST******************/
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    /*******************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*******************VIEWS INITIALIZATION***********************/
        nameValTV = (TextView) findViewById(R.id.name_value_text_view);
        favColorValTV = (TextView) findViewById(R.id.favColor_value_text_view);
        ageValTV = (TextView) findViewById(R.id.age_value_text_view);
        weightValTV = (TextView) findViewById(R.id.weight_value_text_view);
        phoneValTV = (TextView) findViewById(R.id.phone_value_text_view);
        isArtistValTV = (TextView) findViewById(R.id.isArtist_value_text_view);
        locationValTV = (TextView) findViewById(R.id.location_value_text_view);


       //class Data SINGLETON
        Data store = Data.getInstance();

        //This might not be needed to be here
        store.addData(store.loadJSONFromAsset(this));

        Programmer programmer = new Programmer(store.names.get(0),store.favColors.get(0),
                                               store.ages.get(0), store.weights.get(0),
                                               store.phones.get(0),store.isArtists.get(0));


        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.explistView);

        // preparing list data
        prepareListData(store);

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    private void prepareListData(Data store) {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add(store.names.get(0));
        listDataHeader.add(store.names.get(1));
        listDataHeader.add(store.names.get(2));
        listDataHeader.add(store.names.get(3));
        listDataHeader.add(store.names.get(4));
        listDataHeader.add(store.names.get(5));
        listDataHeader.add(store.names.get(6));



        // Adding child data
        List<String> federico = new ArrayList<String>();
        federico.add("Platform: "        + store.platforms.get(0));
        federico.add("Favourite Color: " + store.favColors.get(0));
        federico.add("Age: "             + store.ages.get(0));
        federico.add("Weight: "          + store.weights.get(0));
        federico.add("Phone: "           + store.phones.get(0));
        federico.add("IsArtist: "        + store.isArtists.get(0));
        federico.add("Location: "        + store.addresses.get(0));

        List<String> user2 = new ArrayList<String>();
        List<String> user3 = new ArrayList<String>();

        listDataChild.put(listDataHeader.get(0), federico); // Header, Child data
        listDataChild.put(listDataHeader.get(1), user2);
        listDataChild.put(listDataHeader.get(2), user3);
    }
}