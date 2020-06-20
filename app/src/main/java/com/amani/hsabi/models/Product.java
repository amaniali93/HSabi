package com.amani.hsabi.models;

import androidx.fragment.app.FragmentManager;

import java.io.Serializable;

public class Product implements Serializable {

    private String pId;
    private String pBarcodeNumber;
    private String pPrice;
    private String pName;
    private String pSize;
    private String pImg;

    public void setProduct(Product value) {
    }

    public void show(FragmentManager supportFragmentManager, String simpleName) {
    }
    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpBarcodeNumber() {
        return pBarcodeNumber;
    }

    public void setpBarcodeNumber(String pBarcodeNumber) {
        this.pBarcodeNumber = pBarcodeNumber;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpSize() {
        return pSize;
    }

    public void setpSize(String pSize) {
        this.pSize = pSize;
    }

    public String getpImg() {
        return pImg;
    }

    public void setpImg(String pImg) {
        this.pImg = pImg;
    }
}
