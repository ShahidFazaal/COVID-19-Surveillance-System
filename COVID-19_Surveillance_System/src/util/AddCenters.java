package util;

public class AddCenters {
    private String qId;
    private String qName;
    private String qCity;
    private String qDistrict;
    private String qHead;
    private int qHeadContact;
    private int qContact1;
    private int qContact2;
    private int qCapacity;

    public AddCenters(String qId, String qName, String qCity, String qDistrict, String qHead, int qHeadContact, int qContact1, int qContact2, int qCapacity) {
        this.qId = qId;
        this.qName = qName;
        this.qCity = qCity;
        this.qDistrict = qDistrict;
        this.qHead = qHead;
        this.qHeadContact = qHeadContact;
        this.qContact1 = qContact1;
        this.qContact2 = qContact2;
        this.qCapacity = qCapacity;
    }

    public String getqId() {
        return qId;
    }

    public void setqId(String qId) {
        this.qId = qId;
    }

    public String getqName() {
        return qName;
    }

    public void setqName(String qName) {
        this.qName = qName;
    }

    public String getqCity() {
        return qCity;
    }

    public void setqCity(String qCity) {
        this.qCity = qCity;
    }

    public String getqDistrict() {
        return qDistrict;
    }

    public void setqDistrict(String qDistrict) {
        this.qDistrict = qDistrict;
    }

    public String getqHead() {
        return qHead;
    }

    public void setqHead(String qHead) {
        this.qHead = qHead;
    }

    public int getqHeadContact() {
        return qHeadContact;
    }

    public void setqHeadContact(int qHeadContact) {
        this.qHeadContact = qHeadContact;
    }

    public int getqContact1() {
        return qContact1;
    }

    public void setqContact1(int qContact1) {
        this.qContact1 = qContact1;
    }

    public int getqContact2() {
        return qContact2;
    }

    public void setqContact2(int qContact2) {
        this.qContact2 = qContact2;
    }

    public int getqCapacity() {
        return qCapacity;
    }

    public void setqCapacity(int qCapacity) {
        this.qCapacity = qCapacity;
    }

    @Override
    public String toString() {
        return qName;
    }
}
