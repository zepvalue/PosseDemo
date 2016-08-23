package zepvalue.possedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class ServicesChooserActivity extends AppCompatActivity {


    private ArrayList<Service> services = null;
    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_chooser);

        DataManager store = DataManager.getInstance(this);

        Intent intentFromMain = getIntent();
        ArrayList<Service> services  = (ArrayList<Service>) intentFromMain.getSerializableExtra("extraServices");

        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(new CustomAdapter(this, services));
    }
}