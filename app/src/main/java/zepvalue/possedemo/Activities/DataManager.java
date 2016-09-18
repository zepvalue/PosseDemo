package zepvalue.possedemo.Activities;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import zepvalue.possedemo.Models.Location;
import zepvalue.possedemo.Models.Programmer;
import zepvalue.possedemo.Models.Service;

/**
 * Created by zepvalue on 8/14/2016.
 */
public class DataManager {

    private static DataManager instance = null;

    ArrayList<Location> locations = null;

    private DataManager() {
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
        try {
            JsonElement jelement = new JsonParser().parse(jsonString);


            JsonObject response = jelement.getAsJsonObject().getAsJsonObject("response");

            JsonArray locations = response.getAsJsonArray("locations");

            ArrayList<Location> listLocation = new ArrayList<>();

                for (int i = 0; i < locations.size(); i++) {

                    JsonObject location = locations.get(i).getAsJsonObject();

                    String locality = location.get("locality").getAsString();
                    String region = location.get("region").getAsString();
                    String postalCode = location.get("postal_code").getAsString();
                    String country = location.get("country").getAsString();

                    Location newLocation = new Location(locality, region, postalCode, country, null);
                    listLocation.add(i, newLocation);

                    JsonArray services = location.getAsJsonArray("services");
                    ArrayList<Service> listServices = new ArrayList<>();

                    for (int j = 0; j < services.size(); j++) {
                        JsonObject service = services.get(j).getAsJsonObject();

                        String platformName = service.get("platform").getAsString();

                        Service newService = new Service(platformName, null);
                        listServices.add(j, newService);

                        JsonArray programmers = service.getAsJsonArray("programmers");
                        ArrayList<Programmer> listProgrammer = new ArrayList<>();

                        for (int k = 0; k < programmers.size(); k++)
                        {
                            JsonObject programmersObj = programmers.get(k).getAsJsonObject();

                            String name = programmersObj.get("name").getAsString();
                            String favColor = programmersObj.get("favorite_color").getAsString();
                            String age = programmersObj.get("age").getAsString();
                            String weight = programmersObj.get("weight").getAsString();
                            String phone = programmersObj.get("phone").getAsString();
                            String isArtist = programmersObj.get("is_artist").getAsString();

                            Programmer newProgrammer = new Programmer(name, favColor, age, weight, phone, isArtist);
                            listProgrammer.add(k, newProgrammer);
                        }
                        newService.setProgrammers(listProgrammer);
                    }
                    newLocation.setServices(listServices);
                }
            this.locations = listLocation;

        } catch (JsonIOException e) {

            e.printStackTrace();
        }
    }
}
