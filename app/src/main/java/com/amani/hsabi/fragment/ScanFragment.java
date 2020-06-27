package com.amani.hsabi.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.amani.hsabi.R;
import com.amani.hsabi.activites.BarCodeScan;
import com.amani.hsabi.activites.DB_SQLlite;
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
    private Context mContext;

    public ScanFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_scan, container, false);
        barcodeNomber = parentView.findViewById(R.id.tv_no_barcode);
        btnenterNumber = parentView.findViewById(R.id.btn_no_barcode);
        scanbtn = parentView.findViewById(R.id.btn_Scan);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
                if (barcodeNo.isEmpty()) {
                    barcodeNomber.setError(getString(R.string.no_3));
                    barcodeNomber.requestFocus();
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference(MyContats.FB_KEY_PRODUCTS);
                    Log.d("readResult", "onDataChange()");
                    myRef.addValueEventListener(new ValueEventListener() {
                        boolean notFound = true;

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                final Product value = d.getValue(Product.class);
                                Log.d("readResult", "onDataChange(): " + value.getpBarcodeNumber());

                                Log.d("BarCode", "Value is: " + value);
                                if (barcodeNo.equals(value.getpBarcodeNumber())) {
                                    notFound = false;
                                    // here write the code you want
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                                    dialog.setCancelable(false);
                                    dialog.setTitle(R.string.About);
                                    dialog.setMessage(getString(R.string.Num) + value.getpBarcodeNumber() + "\n" +
                                            getString(R.string.Product_Name) + value.getpName() + "\n" +
                                            getString(R.string.Product_Price) + value.getpPrice() + "\n" +
                                            getString(R.string.Product_Size) + value.getpSize() + "\n" +
                                            getString(R.string.q1));

                                    dialog.setPositiveButton(R.string.yes_1, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            //Action for "yes"
                                            DB_SQLlite db = new DB_SQLlite(getContext());
                                            int result = db.addProduct(value);
                                            if (result == -1) {
                                                Toast.makeText(getContext(), R.string.error1, Toast.LENGTH_SHORT).show();

                                            } else if (result == 0) {
                                                Toast.makeText(getContext(), R.string.alarm_1, Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(getContext(), R.string.added_successfully, Toast.LENGTH_SHORT).show();
                                                Intent intentdone = new Intent(getContext(), FunctionActivity.class);
                                                intentdone.putExtra(MyContats.KEY_SCANNED_PRODUCT, value);
                                                startActivity(intentdone);
                                            }
                                            Intent intent = new Intent(getContext(), FunctionActivity.class);

                                            intent.putExtra(MyContats.KEY_SCANNED_PRODUCT, value);
                                            startActivity(intent);

                                        }
                                    })
                                            .setNegativeButton(R.string.no_1, new DialogInterface.OnClickListener() {
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
                            if (notFound) {
                                Toast.makeText(getContext(), R.string.pa, Toast.LENGTH_SHORT).show();
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
