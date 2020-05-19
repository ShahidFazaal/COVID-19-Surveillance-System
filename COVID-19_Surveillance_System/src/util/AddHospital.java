package util;

public class AddHospital {
    private String id;
    private String hName;
    private String city;
    private String district;
    private int capacity;
    private String director;
    private int dContactNo;
    private int hContactNo1;
    private int hContactNo2;
    private int hFax;
    private String hEmail;

    public AddHospital(String id, String hName, String city, String district, int capacity, String director, int dContactNo, int hContactNo1, int hContactNo2, int hFax, String hEmail) {
        this.id = id;
        this.hName = hName;
        this.city = city;
        this.district = district;
        this.capacity = capacity;
        this.director = director;
        this.dContactNo = dContactNo;
        this.hContactNo1 = hContactNo1;
        this.hContactNo2 = hContactNo2;
        this.hFax = hFax;
        this.hEmail = hEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String gethName() {
        return hName;
    }

    public void sethName(String hName) {
        this.hName = hName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getdContactNo() {
        return dContactNo;
    }

    public void setdContactNo(int dContactNo) {
        this.dContactNo = dContactNo;
    }

    public int gethContactNo1() {
        return hContactNo1;
    }

    public void sethContactNo1(int hContactNo1) {
        this.hContactNo1 = hContactNo1;
    }

    public int gethContactNo2() {
        return hContactNo2;
    }

    public void sethContactNo2(int hContactNo2) {
        this.hContactNo2 = hContactNo2;
    }

    public int gethFax() {
        return hFax;
    }

    public void sethFax(int hFax) {
        this.hFax = hFax;
    }

    public String gethEmail() {
        return hEmail;
    }

    public void sethEmail(String hEmail) {
        this.hEmail = hEmail;
    }

    @Override
    public String toString() {
        return hName;
    }
}
