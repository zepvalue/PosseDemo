package zepvalue.possedemo;

/**
 * Created by zepvalue on 8/14/2016.
 */
public class Location {

    private String locality;
    private String region;
    private String postalCode;
    private String country;

    private String address;

    public Location(String locality, String region, String postalCode,String country)
    {
        this.locality = locality;
        this.region = region;
        this.country = country;
        this.postalCode = postalCode;
    }

    public Location(String address)
    {
        this.address= address;
    }

    public Location() {
    }


    public String getFormattedLocation()
    {
        return locality + ", " + region+ ", "  + postalCode+ ", "  + country;
    }

    public String getAddress() {
        return address;
    }
}
