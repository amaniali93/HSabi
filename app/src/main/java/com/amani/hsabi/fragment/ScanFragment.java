package com.amani.hsabi.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.amani.hsabi.activites.BarCodeScan;
import com.amani.hsabi.R;
import com.amani.hsabi.activites.FunctionActivity;
import com.amani.hsabi.models.MyContats;
import com.amani.hsabi.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ScanFragment extends Fragment {
    public static TextView resulttextview;
    Button scanbtn, btnenterNumber;
    TextView barcodeNomber;

    public ScanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_scan, container, false);
        barcodeNomber = parentView.findViewById(R.id.tv_no_barcode);
        btnenterNumber = parentView.findViewById(R.id.btn_no_barcode);
        scanbtn = parentView.findViewById(R.id.btn_Scan);


        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentScan = new Intent(getActivity(), BarCodeScan.class);
                startActivity(intentScan);
            }
        });


        btnenterNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String barcodeNo = barcodeNomber.getText().toString();
                if(barcodeNo.isEmpty()){
                    barcodeNomber.setError("Number is not found");
                    barcodeNomber.requestFocus();
                }else{
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference(MyContats.FB_KEY_PRODUCTS);
                    Log.d("readResult", "onDataChange()");
                    myRef.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                final Product value = d.getValue(Product.class);
                                Log.d("readResult", "onDataChange(): " + value.getpBarcodeNumber());

                                Log.d("BarCode", "Value is: " + value);
                                if (barcodeNo.equals(value.getpBarcodeNumber())) {
                                    // here write the code you want
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
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

                                            Intent intent = new Intent(getContext(), FunctionActivity.class);

                                            intent.putExtra(MyContats.KEY_SCANNED_PRODUCT, value);
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
                                }else {

                                    Toast.makeText(getContext(), "Sorry, Product is not available!!", Toast.LENGTH_SHORT).show();                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.w("readResult", "Failed to read value.");
                        }
                    });


                }
            }
        });
        return parentView;
    }
}
