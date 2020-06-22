package com.amani.hsabi.activites;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amani.hsabi.models.MyContats;
import com.amani.hsabi.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.text.BreakIterator;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BarCodeScan extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static BreakIterator resulttextview;
    int MY_PERMISSIONS_REQUEST_CAMERA = 0;
    //DB_SQLlite db = new DB_SQLlite(this);


    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    @Override
    public void handleResult(Result result) {
        //BarCodeScan.resulttextview.setText(result.getText());
        Log.d("readResult", "handelResult(): " + result.getText());
        readProductInfoFromFirebase(result.getText());
        Toast.makeText(this, result.getText(), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        }
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }


    private void readProductInfoFromFirebase(final String resultText) {
// Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(MyContats.FB_KEY_PRODUCTS);
        Log.d("readResult", "onDataChange()");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Product value = d.getValue(Product.class);
                    Log.d("readResult", "onDataChange(): " + value.getpBarcodeNumber());

                    Log.d("BarCode", "Value is: " + value);
                    if (resultText.equals(value.getpBarcodeNumber())) {
                        // here write the code you want
                        showAlertDialog(value);
                        break;

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("readResult", "Failed to read value.", error.toException());
            }
        });

    }

    private void showAlertDialog(final Product value) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(BarCodeScan.this);
        dialog.setCancelable(false);
        dialog.setTitle("About Product");
        dialog.setMessage("BarCode Number:   " + value.getpBarcodeNumber() + "\n" +
                "Product Name:     " + value.getpName() + "\n" +
                "Product Price:    " + value.getpPrice() + "\n" +
                "Product Size:     " + value.getpSize() + "\n" +
                "Are you sure you want to add  to your Bill?");

        dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Action for "yes"
               /* SharedPreferences editorPrefernce =  getApplicationContext().getSharedPreferences("Product",MODE_PRIVATE);
                SharedPreferences.Editor editor = editorPrefernce.edit();
                editor.putString("pId",value.getpId());
                editor.putString("PbarcodeNo",value.getpBarcodeNumber());
                editor.putString("pName" , value.getpName());
                editor.putString("pImg" , value.getpImg());
                editor.putString("pPrice" , value.getpPrice());
                editor.putString("pSize" , value.getpSize());
                editor.apply();*/
                DB_SQLlite db = new DB_SQLlite(BarCodeScan.this);
                int result = db.addProduct(value);
                if (result == -1) {
                    Toast.makeText(BarCodeScan.this, "Error happened while storing item!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else if (result == 0) {
                    Toast.makeText(BarCodeScan.this, "This Product is already in cart!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(BarCodeScan.this, "added successfully!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BarCodeScan.this, FunctionActivity.class);
                    intent.putExtra(MyContats.KEY_SCANNED_PRODUCT, value);
                    startActivity(intent);
                }


            }
        })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Action for "no".
                        dialog.dismiss();
                    }
                });

        final AlertDialog alert = dialog.create();
        alert.show();
    }
}