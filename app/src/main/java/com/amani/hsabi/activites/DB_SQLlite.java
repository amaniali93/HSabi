package com.amani.hsabi.activites;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.amani.hsabi.models.Product;

import java.security.Policy;
import java.util.ArrayList;

public class DB_SQLlite extends SQLiteOpenHelper {
    public static final String DB_name="data.db";
    public DB_SQLlite(@Nullable Context context) {
        super(context, DB_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table scan_Product (pId STRING PRIMARY KEY AUTOINCREMENT, pBarcodeNumber STRING ,pPrice STRING , pName STRING ,pSize STRING, pImg STRING)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS scan_Product");
        onCreate(db);

    }

    public boolean addProduct(Product value) {

SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("pId",value.getpId());
        cv.put("pBarcodeNumber", value.getpBarcodeNumber());
        cv.put("pPrice", value.getpPrice());
        cv.put("pName", value.getpName());
        cv.put("pSize", value.getpSize());
        cv.put(" pImg", value.getpImg());
        Long result = db.insert("scan_Product",null,cv);
        if(result==-1)
            return false;
        else
            return true;
    }


   public ArrayList<Product> getProducts() {
       ArrayList arrayList = new ArrayList();
       SQLiteDatabase db = this.getReadableDatabase();
       Cursor res = db.rawQuery("select* from scan_table", null);
       res.moveToFirst();
       while (res.isAfterLast()==false) {
              String t1 =res.getString(1);
           String t2 =res.getString(2);
           String t3 =res.getString(3);
           String t4 =res.getString(4);
           String t5 =res.getString(5);
           arrayList.add(t1+"-"+t2+"-"+t3+"-"+t4+"-"+t5);
           res.moveToNext();
       }return arrayList;
   }
    public void deleteAllProduct(Product value) {

    }
}
