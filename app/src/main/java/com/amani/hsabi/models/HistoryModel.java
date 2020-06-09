package com.amani.hsabi.models;

public class HistoryModel {
    public int getBarcodeImage() {
        return barcodeImage;
    }

    public void setBarcodeImage(int barcodeImage) {
        this.barcodeImage = barcodeImage;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getBillPrice() {
        return billPrice;
    }

    public void setBillPrice(String billPrice) {
        this.billPrice = billPrice;
    }

    private int barcodeImage;
    private String billNumber;
    private String billDate;
    private String billPrice;
}
