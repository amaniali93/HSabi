package com.amani.hsabi.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.amani.hsabi.activites.BarCodeScan;
import com.amani.hsabi.R;


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
                String barcodeNo = barcodeNomber.getText().toString();
                if(barcodeNo.isEmpty()){
                    barcodeNomber.setError("Number is not found");
                    barcodeNomber.requestFocus();
                }else{

                }
            }
        });
        return parentView;
    }
}
