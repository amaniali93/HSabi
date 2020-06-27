package com.amani.hsabi.models;

public class HistoryModel extends Billinfo {

    public HistoryModel(String billNumber, String billDate, String billPrice) {
        this.billNumber = billNumber;
        this.billDate = billDate;
        this.billPrice = billPrice;
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

    private String billNumber;
    private String billDate;
    private String billPrice;
}
