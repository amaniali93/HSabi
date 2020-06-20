package com.amani.hsabi.activites;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amani.hsabi.fragment.CartFragment;
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

        readProductInfoFromFirebase(result.getText());

        onBackPressed();
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


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Product value = dataSnapshot.getValue(Product.class);
                    Log.d("BarCode", "Value is: " + value);
                    if (resultText.equals(value.getpBarcodeNumber().toString())) {
                        // here write the code you want

                        AlertDialog.Builder dialog = new AlertDialog.Builder(BarCodeScan.this);
                        dialog.setCancelable(false);
                        dialog.setTitle("About Products");
                        dialog.setMessage(value+"Are you sure you want to add  to your Bill?" );
                        dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                //Action for "yes".

                                Intent intent=new Intent(BarCodeScan.this,FunctionActivity.class);

                                startActivity(intent);
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
                        break;

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("BarCode", "Failed to read value.", error.toException());
            }
        });

    }
}