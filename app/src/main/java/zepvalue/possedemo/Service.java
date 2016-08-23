package zepvalue.possedemo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zepvalue on 8/16/2016.
 */
public class Service implements Serializable{


    private static final long serialVersionUID = 4657686577332887646L;
    private String platform;

    private Service service;



    private ArrayList<Programmer> programmers = new ArrayList<Programmer>();

    public Service(String platform, ArrayList<Programmer> programmers)
    {
        this.platform = platform;
        this.programmers = programmers;
    }

    public String getPlatform()
    {
        return platform;
    }

    public ArrayList<Programmer> getProgrammers()
    {
        return programmers;
    }

    public void setProgrammers(ArrayList<Programmer> programmers) {
        this.programmers = programmers;
    }

    private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
        //perform the default serialization for all non-transient, non-static fields
        aOutputStream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
        //always perform the default de-serialization first
        aInputStream.defaultReadObject();
        //make defensive copy of the mutable Date field
        service = new Service(this.platform, this.programmers);
    }
}
