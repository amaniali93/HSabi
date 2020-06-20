package com.amani.hsabi.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amani.hsabi.Interfaces.MediaInterface;
import com.amani.hsabi.R;


public class CartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    TextView totalprice , eachprice ,qunt,productName ,size ;
    ImageView productImg  , cancelImg ,addImg, minusImg;
    Button totalbill;

    public CartFragment() {
        // Required empty public constructor
    }
    private MediaInterface mListener;

    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View parentView = inflater.inflate(R.layout.fragment_cart, container, false);
        totalprice = parentView.findViewById(R.id.tv_total);
        eachprice = parentView.findViewById(R.id.tv_rate);
        qunt = parentView.findViewById(R.id.tv_qunt);
        productName = parentView.findViewById(R.id.tv_Productname);
        size = parentView.findViewById(R.id.tv_size);
        totalbill = parentView.findViewById(R.id.btn_placeorder);
        productImg = parentView.findViewById(R.id.item_photo);
        cancelImg = parentView.findViewById(R.id.iv_cansel);
        addImg = parentView.findViewById(R.id.add_img);
        minusImg = parentView.findViewById(R.id.remove_img);
        return parentView;
    }
}
