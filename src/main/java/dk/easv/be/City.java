package dk.easv.be;

public class City {

    private int zipcode;
    private String cityName;

    public City(int zipcode, String cityName){
        this.zipcode = zipcode;
        this.cityName = cityName;
    }

    public int getZipcode(){
        return zipcode;
    }

    public String getCityName(){
        return cityName;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
