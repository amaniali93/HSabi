package com.amani.hsabi.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amani.hsabi.Adaptors.CartAdapter;
import com.amani.hsabi.Interfaces.MediaInterface;
import com.amani.hsabi.R;
import com.amani.hsabi.models.CartModel;
import com.amani.hsabi.models.MyContats;
import com.amani.hsabi.models.Product;

import java.util.ArrayList;


public class CartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    TextView totalprice  ;
     RecyclerView recyclerView;
    Button totalbill;
    private Context mContext;
    private CartAdapter mAdapter;
    public CartFragment() {
        // Required empty public constructor
    }
    private MediaInterface mListener;

    @Override
    public void onStart() {
        super.onStart();

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
    private void setupRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        DividerItemDecoration divider = new DividerItemDecoration(mContext, layoutManager.getOrientation());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(divider);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }
    //private void createProductInfo(ArrayList<Product> cart) {
//Product carts;

        //add first Item
        //cart = new ArrayList<Product>();

  //  }-->
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View parentView = inflater.inflate(R.layout.fragment_cart, container, false);
        totalprice = parentView.findViewById(R.id.tv_total);
        recyclerView= parentView.findViewById(R.id.recycler_cart);
        setupRecyclerView(recyclerView);
        mAdapter = new CartAdapter();
        ArrayList<CartModel> carts = new ArrayList<>();
       // createProductInfo(carts);
        mAdapter.update(carts);
        totalbill = parentView.findViewById(R.id.btn_placeorder);

        return parentView;
    }
}
