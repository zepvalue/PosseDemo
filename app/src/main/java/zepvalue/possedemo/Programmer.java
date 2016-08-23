package zepvalue.possedemo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by zepvalue on 8/14/2016.
 */
public class Programmer implements Serializable {

    private static final long serialVersionUID = 7043342470139187758L;
    private String name;
    private String favColor;
    private String age;
    private String weight;
    private String phone;
    private String isArtist;

    public Programmer(String name, String favColor, String age, String weight, String phone, String isArtist)
    {
        this.name = name;
        this.favColor = favColor;
        this.age = age;
        this.weight  = weight;
        this.phone = phone;
        this.isArtist = isArtist;


    }

    public String getName() {
        return name;
    }

    public String getFavColor() {
        return favColor;
    }

    public String getAge() {
        return age;
    }

    public String getWeight() {
        return weight;
    }

    public String getPhone() {
        return phone;
    }

    public String getIsArtist() {
        return isArtist;
    }

    private Programmer programmer;

    private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
        //perform the default serialization for all non-transient, non-static fields
        aOutputStream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream aInputStream
    ) throws ClassNotFoundException, IOException {
        //always perform the default de-serialization first
        aInputStream.defaultReadObject();
        //make defensive copy of the mutable Date field
        programmer = new Programmer(this.name,this.favColor,this.age,this.weight,this.phone,this.isArtist);
    }
}
