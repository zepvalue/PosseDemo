package zepvalue.possedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private TextView nameValTV;
    private TextView favColorValTV;
    private TextView ageValTV;
    private TextView weightValTV;
    private TextView phoneValTV;
    private TextView isArtistValTV;
    private TextView locationValTV;

    private ArrayList<Programmer> programmer ;
    private ArrayList<Location> location   ;
    private ArrayList<Platform> platform   ;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nameValTV = (TextView) findViewById(R.id.name_value_text_view);
        favColorValTV = (TextView) findViewById(R.id.favColor_value_text_view);
        ageValTV = (TextView) findViewById(R.id.age_value_text_view);
        weightValTV = (TextView) findViewById(R.id.weight_value_text_view);
        phoneValTV = (TextView) findViewById(R.id.phone_value_text_view);
        isArtistValTV = (TextView) findViewById(R.id.isArtist_value_text_view);
        locationValTV = (TextView) findViewById(R.id.location_value_text_view);

        DataManager store = DataManager.getInstance();

        store.addData(store.loadJSONFromAsset(this));
        programmer = store.getListProgrammer();
        location   = store.getListLocation();
        platform   = store.getListPlatform();

        expListView = (ExpandableListView) findViewById(R.id.explistView);

        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        expListView.setAdapter(listAdapter);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        for (int i = 0; i < programmer.size(); i++)
            listDataHeader.add(programmer.get(i).getName());

        String [] users = new String[programmer.size()];

        String out;
        String city;
        String pla;

        int l, j , k;

        for(l = 0; l<location.size();l++)
        {
            city = "Location: " + location.get(l).getAddress()+ "\n";

            for( k = 0 ; k< platform.size();k++)
            {
                pla = ("Platform: " + platform.get(k).getName()+ "\n");

                for (j = k; j < platform.size(); j++)
                {
                    users[j] = ("Favourite Color: " + programmer.get(j).getFavColor() + "\n"
                            + "Age: "             + programmer.get(j).getAge()      + "\n"
                            + "Weight: "          + programmer.get(j).getWeight()   + "\n"
                            + "Phone: "           + programmer.get(j).getPhone()    + "\n"
                            + "IsArtist: "        + programmer.get(j).getIsArtist() + "\n");

                    out = city + pla + users[j] ;

                   listDataChild.put(listDataHeader.get(j), new ArrayList<String>(Arrays.asList(out.split("\n"))));
                    //NOW I NEED TO LIMIT THE LOOP FOR EACH PART
                }
            }
        }
    }
}