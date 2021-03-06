package com.amani.hsabi.activites;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.amani.hsabi.models.Billinfo;
import com.amani.hsabi.models.Product;

import java.util.ArrayList;

public class DB_SQLlite extends SQLiteOpenHelper {
    public static final String DB_name = "data.db";


    public DB_SQLlite(@Nullable Context context) {
        super(context, DB_name, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table scan_Product (pId TEXT PRIMARY KEY, pBarcodeNumber TEXT ,pPrice TEXT , pName TEXT ,pSize TEXT, pImg TEXT)");
        db.execSQL("create table billTable(bId TEXT PRIMARY KEY ,bPrice TEXT ,bDate)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS scan_Product");
        onCreate(db);

    }

    public int addProduct(Product value) {

        if (isNewItem(value.getpId())) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("pId", value.getpId());
            cv.put("pBarcodeNumber", value.getpBarcodeNumber());
            cv.put("pPrice", value.getpPrice());
            cv.put("pName", value.getpName());
            cv.put("pSize", value.getpSize());
            cv.put(" pImg", value.getpImg());
            long result = db.insert("scan_Product", null, cv);
            return (int) result;
        } else {
            return 0;
        }
    }

    public void addBill(Billinfo billObj) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv1 = new ContentValues();
        cv1.put("bPrice", billObj.getbPrice());
        cv1.put("bDate", billObj.getbDate());
        db.insert("billTable", null, cv1);
        db.close();
    }

    private boolean isNewItem(String pId) {
        String Query = "Select * from scan_Product  where pId = " + "'" + pId + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return true; // new Item
        }
        cursor.close();

        return false; // not New Item
    }


    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select* from scan_Product", null);
        if (res.moveToFirst()) {
            do {
                Product p = new Product();
                String t0 = res.getString(0);
                String t1 = res.getString(1);
                int t2 = res.getInt(2);
                String t3 = res.getString(3);
                String t4 = res.getString(4);
                String t5 = res.getString(5);

                p.setpId(t0);
                p.setpBarcodeNumber(t1);
                p.setpPrice(t2);
                p.setpName(t3);
                p.setpSize(t4);
                p.setpImg(t5);
                products.add(p);

            } while (res.moveToNext());
        }
        return products;
    }

    public ArrayList<Billinfo> getlastBill() {
        ArrayList<Billinfo> Billinfo = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select* from billTable", null);
        if (res.moveToFirst()) {
            do {
                Billinfo p = new Billinfo();
                String id = res.getString(0);
                String price = res.getString(1);
                String date = res.getString(2);

                p.setbId(id);
                p.setbPrice(price);
                p.setbDate(date);

                Billinfo.add(p);

            } while (res.moveToNext());
        }
        return Billinfo;
    }

    public void delete(String productId) {
        // delete * from table where id = 1
        SQLiteDatabase db = getWritableDatabase();
        db.delete("scan_Product", "pId" + "='" + productId + "'", null);
        db.close();
    }


}
