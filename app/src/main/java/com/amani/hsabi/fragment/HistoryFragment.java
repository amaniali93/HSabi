package com.amani.hsabi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amani.hsabi.Adaptors.HistoryAdapter;
import com.amani.hsabi.R;
import com.amani.hsabi.activites.DB_SQLlite;


public class HistoryFragment extends Fragment {

    RecyclerView recyclerView;
    private Context mContext;
    private HistoryAdapter mAdapter;
    public HistoryFragment() {
        // Required empty public constructor
    }

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
        final View parentView = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = parentView.findViewById(R.id.recycler_cart);
        setupRecyclerView(recyclerView);
        mAdapter = new HistoryAdapter();
        DB_SQLlite db = new DB_SQLlite(mContext);
        // mAdapter.update(db.getlastBill());

        recyclerView.setAdapter(mAdapter);


        return inflater.inflate(R.layout.fragment_history, container, false);
    }
}
