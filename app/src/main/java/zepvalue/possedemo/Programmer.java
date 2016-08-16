package zepvalue.possedemo;

/**
 * Created by zepvalue on 8/14/2016.
 */
public class Programmer extends Location {

    private String name;
    private String favColor;
    private String age;
    private String weight;
    private String phone;
    private String isArtist;
//    public Programmer(String name, String favColor, int age, double weight, String phone, boolean isArtist)
//    {
//
//        this.name = name;
//        this.favColor = favColor;
//        this.age = age;
//        this.weight  = weight;
//        this.phone = phone;
//        this.isArtist = isArtist;
//    }


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

    public void setFavColor(String favColor) {
        this.favColor = favColor;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setIsArtist(String isArtist) {
        this.isArtist = isArtist;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getFormattedProgrammer()
    {
        return "\n\n" + name + "\n" + favColor + "\n" + age + weight + "\n" + phone + "\n" + isArtist + "\n" +getFormattedLocation() ;
    }

}
