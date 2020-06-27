package com.amani.hsabi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amani.hsabi.Adaptors.CartAdapter;
import com.amani.hsabi.Interfaces.MediatorInterface;
import com.amani.hsabi.R;
import com.amani.hsabi.activites.DB_SQLlite;
import com.amani.hsabi.models.Billinfo;
import com.amani.hsabi.models.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
//import com.amani.hsabi.activites.DB_SQLlite;


public class CartFragment extends Fragment implements CartAdapter.CartListener {
    // TODO: Rename parameter arguments, choose names that match
    TextView totalprice;
    RecyclerView recyclerView;
    Button totalbill;
    private Context mContext;
    CartAdapter mAdapter;
    private int mTotalPrice;
    private OnBillRequestListener mListener;


    public CartFragment() {
        // Required empty public constructor
    }

    private MediatorInterface MediatorInterface;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mListener = (OnBillRequestListener) context;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        DividerItemDecoration divider = new DividerItemDecoration(mContext, layoutManager.getOrientation());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(divider);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View parentView = inflater.inflate(R.layout.fragment_cart, container, false);
        totalprice = parentView.findViewById(R.id.tv_total);
        recyclerView = parentView.findViewById(R.id.recycler_cart);
        setupRecyclerView(recyclerView);
        mAdapter = new CartAdapter();
        DB_SQLlite db = new DB_SQLlite(mContext);
        mAdapter.update(db.getProducts());
        mAdapter.setupCartListener(this);
        recyclerView.setAdapter(mAdapter);
        onDataChange(db.getProducts());
        totalbill = parentView.findViewById(R.id.btn_placeorder);

        totalbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   if (mListener != null) {


                String date_n = new SimpleDateFormat("MM dd, yyyy", Locale.getDefault()).format(new Date());
                DB_SQLlite db = new DB_SQLlite(mContext);
                Billinfo billObj = new Billinfo(totalprice.getText().toString(), date_n);
                db.addBill(billObj);

                if (mListener != null) {
                    mListener.onBillRequest(mAdapter.getProducts(), mTotalPrice);
                }


            }

            //  FragmentManager fragmentManager=getFragmentManager();
            //}
        });
        return parentView;

    }

    public void calculateTotal(int totalPrice) {
        totalprice.setText(totalPrice + "   " + getString(R.string.OMR_1));
        mTotalPrice = totalPrice;

    }

    @Override
    public void onDataChange(ArrayList<Product> newProductsArrayList) {
        int totalPrice = 0;
        for (Product p : newProductsArrayList) {
            totalPrice += p.getpPrice() * p.getQunt();
        }

        calculateTotal(totalPrice);
    }


    public interface OnBillRequestListener {
        void onBillRequest(ArrayList<Product> products, int totalPrice);
    }


}
