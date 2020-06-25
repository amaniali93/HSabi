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
import com.amani.hsabi.models.HistoryModel;

import java.util.ArrayList;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<HistoryModel> mHistory;
    private HistoryAdapter mListener;

    public HistoryAdapter() {
        mHistory = new ArrayList<>();
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

        HistoryModel History = mHistory.get(position);
        holder.billDate.setText(History.getBillDate());
        holder.billNumber.setText(History.getBillNumber());
        holder.billPrice.setText(History.getBillPrice());

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
