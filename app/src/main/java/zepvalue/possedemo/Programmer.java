package zepvalue.possedemo;

/**
 * Created by zepvalue on 8/14/2016.
 */
public class Programmer {

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

    public void setName(String name) {
        this.name = name;
    }

}
