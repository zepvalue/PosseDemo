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
public class Data {

    private String data;
    private String programmersData;

    String platformsName;
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


    private static Data instance = null;

    protected Data() {
        // Exists only to defeat instantiation.
    }

    public static Data getInstance() {
        if(instance == null) {
            instance = new Data();
        }
        return instance;
    }


    String dummy;

    ArrayList<String> names     = new ArrayList<String>(10);
    ArrayList<String> favColors = new ArrayList<String>(10);
    ArrayList<String> ages      = new ArrayList<String>(10);
    ArrayList<String> weights   = new ArrayList<String>(10);
    ArrayList<String> phones    = new ArrayList<String>(10);
    ArrayList<String> isArtists = new ArrayList<String>(10);
    ArrayList<String> addresses = new ArrayList<String>(10);
    ArrayList<String> platforms = new ArrayList<String>(10);


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
        data = "";
        programmersData = "";
        platformsName = "";
        dummy = "";
        Programmer person;
        Location place;

        try {
            json = new JSONObject(jsonString);
            JSONObject response = json.getJSONObject("response");
            JSONArray locations = response.getJSONArray("locations");

            for (int i = 0; i < locations.length(); i++) {

               JSONObject location = locations.getJSONObject(i);

                locality   = location.optString("locality");
                region     = location.optString("region");
                postalCode = location.optString("postal_code");
                country    = location.optString("country");

                data = data  + locality + ", " + region + ", " + postalCode + ", " + country + "\n\n";
                String address =  locality + ", " + region + ", " + postalCode + ", " + country ;

                addresses.add(address);
                /*************************************************************************************/
                JSONArray services = location.getJSONArray("services");

                for(int j = 0; j < services.length();j++){
                    JSONObject platform = services.getJSONObject(j);

                    String platformName = platform.optString("platform");

                    platformsName = platformsName + platformName + "\n\n";
                    platforms.add(platformName);
                    /*************************************************************************************/

                    JSONArray programmers = platform.getJSONArray("programmers");
                    for(int k = 0; k < programmers.length(); k++)
                    {
                        JSONObject programmersObj = programmers.getJSONObject(k);

                        name     = programmersObj.optString("name");
                        favColor = programmersObj.optString("favorite_color");
                        age      = programmersObj.optString("age");
                        weight   = programmersObj.optString("weight");
                        phone    = programmersObj.optString("phone");
                        isArtist = programmersObj.optString("is_artist");

                        names.add(name);
                        favColors.add(favColor);
                        ages.add(age);
                        weights.add(weight);
                        phones.add(phone);
                        isArtists.add(isArtist);

                       // programmersData = programmersData + "\n" + place.getFormattedLocation() + "\n" + person.getFormattedProgrammer() + "\n";
                        programmersData = programmersData + "\n" + address + "\n" + name + "\n" + favColor + "\n" + age + "\n" ;
                    }
                }
                person   = new Programmer(name,favColor,age,weight,phone,isArtist);
                place    = new Location(locality,region,postalCode,country);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getProgrammersData() {
        return programmersData;
    }

}
