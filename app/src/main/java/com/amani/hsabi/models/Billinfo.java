package com.amani.hsabi.models;

public class Billinfo {
    String bId;
    String bPrice;
    String bDate;

    public Billinfo() {

    }

    public Billinfo(String bPrice, String bDate) {
        this.bPrice = bPrice;
        this.bDate = bDate;
    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public String getbPrice() {
        return bPrice;
    }

    public void setbPrice(String bPrice) {
        this.bPrice = bPrice;
    }

    public String getbDate() {
        return bDate;
    }

    public void setbDate(String bDate) {
        this.bDate = bDate;
    }
}
