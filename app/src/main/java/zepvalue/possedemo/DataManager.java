package zepvalue.possedemo;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by zepvalue on 8/14/2016.
 */
public class DataManager {



    private static DataManager instance = null;

    ArrayList<Location> locations = null;

    private DataManager() {
        // Exists only to defeat instantiation.
    }

    public static DataManager getInstance(Context context) {
        if(instance == null) {
            instance = new DataManager();
        }
        String jsonString = instance.loadJSONFromAsset(context);
        instance.addData(jsonString);
        return instance;
    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("android_model_challenge.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void addData(String jsonString) {
        JSONObject json = null;
        int counterProgrammer =0;
        try {
            json = new JSONObject(jsonString);
            JSONObject response = json.getJSONObject("response");

            JSONArray locations = response.getJSONArray("locations");
            ArrayList<Location> listLocation = new ArrayList<>();
                for (int i = 0; i < locations.length(); i++) {

                    JSONObject location = locations.getJSONObject(i);

                    String locality = location.optString("locality");
                    String region = location.optString("region");
                    String postalCode = location.optString("postal_code");
                    String country = location.optString("country");

                    Location newLocation = new Location(locality, region, postalCode, country, null);
                    listLocation.add(i, newLocation);

                    JSONArray services = location.getJSONArray("services");
                    ArrayList<Service> listServices = new ArrayList<>();

                    for (int j = 0; j < services.length(); j++) {
                        JSONObject service = services.getJSONObject(j);

                        String platformName = service.optString("platform");

                        Service newService = new Service(platformName, null);
                        listServices.add(j, newService);

                        JSONArray programmers = service.getJSONArray("programmers");
                        ArrayList<Programmer> listProgrammer = new ArrayList<>();

                        for (int k = 0; k < programmers.length(); k++)
                        {
                            JSONObject programmersObj = programmers.getJSONObject(k);

                            String name = programmersObj.optString("name");
                            String favColor = programmersObj.optString("favorite_color");
                            String age = programmersObj.optString("age");
                            String weight = programmersObj.optString("weight");
                            String phone = programmersObj.optString("phone");
                            String isArtist = programmersObj.optString("is_artist");

                            Programmer newProgrammer = new Programmer(name, favColor, age, weight, phone, isArtist);
                            listProgrammer.add(k, newProgrammer);
                        }
                        newService.setProgrammers(listProgrammer);
                    }
                    newLocation.setServices(listServices);
                }
            this.locations = listLocation;

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }
}
