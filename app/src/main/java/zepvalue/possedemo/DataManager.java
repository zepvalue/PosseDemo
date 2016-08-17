package zepvalue.possedemo;

import android.content.Context;
import android.util.Log;

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
    ArrayList<Programmer> listProgrammer = new ArrayList<>();
    ArrayList<Location> listLocation   = new ArrayList<>();
    ArrayList<Platform> listPlatform   = new ArrayList<>();

    String name;
    String age;
    String favColor;
    String weight;
    String phone;
    String isArtist;
    String locality;
    String region;
    String postalCode;
    String country;
    private static DataManager instance = null;


    protected DataManager() {
        // Exists only to defeat instantiation.
    }

    public static DataManager getInstance() {
        if(instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public String loadJSONFromAsset(Context c) {
        String json = null;
        try {
            InputStream is = c.getAssets().open("android_model_challenge.json");
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
        int counterProgrammer = 0;
        int counterPlatform   = 0;
        int counterLocation   = 0;
        try {
            json = new JSONObject(jsonString);
            JSONObject response = json.getJSONObject("response");
            JSONArray locations = response.getJSONArray("locations");

                for (int i = 0; i < locations.length(); i++) {

                    JSONObject location = locations.getJSONObject(i);

                    locality = location.optString("locality");
                    region = location.optString("region");
                    postalCode = location.optString("postal_code");
                    country = location.optString("country");

                    listLocation.add(i, new Location(locality, region, postalCode, country));
                    counterLocation++;
                    JSONArray services = location.getJSONArray("services");

                    for (int j = 0; j < services.length(); j++) {
                        JSONObject platform = services.getJSONObject(j);

                        String platformName = platform.optString("platform");

                        listPlatform.add(counterPlatform ,new Platform(platformName));
                        counterPlatform++;

                        JSONArray programmers = platform.getJSONArray("programmers");
                        for (int k = 0; k < programmers.length(); k++)
                        {
                            JSONObject programmersObj = programmers.getJSONObject(k);

                            name = programmersObj.optString("name");
                            favColor = programmersObj.optString("favorite_color");
                            age = programmersObj.optString("age");
                            weight = programmersObj.optString("weight");
                            phone = programmersObj.optString("phone");
                            isArtist = programmersObj.optString("is_artist");

                            listProgrammer.add(counterProgrammer , new Programmer(name, favColor, age, weight, phone, isArtist));
                            counterProgrammer++;
                        }
                    }
                }
        } catch (JSONException e) {

            e.printStackTrace();
        }
    }


    public ArrayList<Location> getListLocation() {
        return listLocation;
    }

    public ArrayList<Platform> getListPlatform() {
        return listPlatform;
    }

    public ArrayList<Programmer> getListProgrammer() {
        return listProgrammer;
    }

}
