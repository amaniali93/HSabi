package com.amani.hsabi.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amani.hsabi.R;
import com.amani.hsabi.activites.DB_SQLlite;
import com.amani.hsabi.models.Billinfo;
import com.amani.hsabi.models.HistoryModel;

import java.util.ArrayList;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<HistoryModel> mHistory;
    private HistoryAdapter mListener;
    private DB_SQLlite dbSqLlite;

    public HistoryAdapter() {
        mHistory = new ArrayList<>();
    }

    public HistoryAdapter(DB_SQLlite db) {
        this.dbSqLlite = db;
        mHistory = new ArrayList<>();
        ArrayList<Billinfo> bills = dbSqLlite.getlastBill();
        for (Billinfo bill : bills) {
            mHistory.add(new HistoryModel(bill.getbId(), bill.getbDate(), bill.getbPrice()));
        }
    }
    public void update(int position, HistoryModel HistoryModel) {
        mHistory.add(position, HistoryModel);
        notifyItemChanged(position);
    }

    public void update(ArrayList<HistoryModel> History) {
        mHistory = History;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_history, parent, false);

        return new MyViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Billinfo History = mHistory.get(position);
        holder.billDate.setText(History.getbDate());
        holder.billNumber.setText(History.getbId());
        holder.billPrice.setText(History.getbPrice());

    }

    @Override
    public int getItemCount() {
        return mHistory.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivbarcodeImage;
        TextView billNumber;
        TextView billDate;
        TextView billPrice;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivbarcodeImage = itemView.findViewById(R.id.iv_prebarcode);
            billNumber = itemView.findViewById(R.id.tv_bill_number);
            billDate = itemView.findViewById(R.id.tv_date);
            billPrice = itemView.findViewById(R.id.tv_price);
        }
    }
}
