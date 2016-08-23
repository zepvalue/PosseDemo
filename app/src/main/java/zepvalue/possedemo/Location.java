package zepvalue.possedemo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zepvalue on 8/14/2016.
 */
public class Location implements Serializable {

    private static final long serialVersionUID = 6008605814747197499L;
    private String locality;
    private String region;
    private String postalCode;
    private String country;
    private ArrayList<Service> services;


    public Location(String locality, String region, String postalCode,String country, ArrayList<Service> services)
    {
        this.locality = locality;
        this.region = region;
        this.country = country;
        this.postalCode = postalCode;
        this.services = services;
    }

    public String getAddress()
    {
        return locality + ", " +region + ", " + postalCode + ", " + country;
    }

    private Location location;

    public ArrayList<Service> getServices()
    {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }

    private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
        //perform the default serialization for all non-transient, non-static fields
        aOutputStream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream aInputStream
    ) throws ClassNotFoundException, IOException {
        //always perform the default de-serialization first
        aInputStream.defaultReadObject();
        //make defensive copy of the mutable Date field
        location = new Location(this.locality, this.region, this.postalCode,this.country, this.services);
    }
}
