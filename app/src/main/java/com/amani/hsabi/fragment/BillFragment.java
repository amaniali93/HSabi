package com.amani.hsabi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.amani.hsabi.R;


public class BillFragment extends Fragment {
    TextView tvtotal;
    ImageView imgBarcode;
    TextView date;
    private Context mContext;
    private int mPriceTotal;
    public BillFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View parentView = inflater.inflate(R.layout.fragment_bill, container, false);
        tvtotal = parentView.findViewById(R.id.totalbill_tv);
        date = parentView.findViewById(R.id.tv_date);
        imgBarcode = parentView.findViewById(R.id.barcode_img);
        tvtotal.setText(mPriceTotal);

        return parentView;
    }

    public void setPricetotal(int pricetotal) {
        mPriceTotal = pricetotal;
    }
}
