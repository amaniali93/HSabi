package com.amani.hsabi.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Billinfo implements Serializable {
    String bId;
    String bPrice;
    String bDate;
    ArrayList<Product> products;

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

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
