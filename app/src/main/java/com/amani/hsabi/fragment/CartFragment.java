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
import com.amani.hsabi.Interfaces.MediaInterface;
import com.amani.hsabi.R;
import com.amani.hsabi.activites.DB_SQLlite;
//import com.amani.hsabi.activites.DB_SQLlite;


public class CartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    TextView totalprice;
    RecyclerView recyclerView;
    Button totalbill;
    private Context mContext;
    CartAdapter mAdapter;

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
        recyclerView.setAdapter(mAdapter);

        totalbill = parentView.findViewById(R.id.btn_placeorder);
        calculateTotal();
        return parentView;

    }

    public void calculateTotal() {
        // SharedPreferences prefs = mContext.getSharedPreferences("total",MODE_PRIVATE);
        // int loadedString = prefs.getInt("qunt", Integer.parseInt("null"));

        int i = 0;
        int total = 0;

       /* while(i<mAdapter.selecteditems.size()){
          //  total=total + ( Integer.valueOf(mAdapter.selecteditems.get(i).getpPrice()) * Integer.valueOf(mAdapter.selecteditems.get(i).loadedString));
            i++;
        }*/
        totalprice.setText("OMR" + total);
    }
}
