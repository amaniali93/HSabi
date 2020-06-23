package com.amani.hsabi.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amani.hsabi.R;
import com.amani.hsabi.activites.DB_SQLlite;
import com.amani.hsabi.models.Product;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private Context mContext;
    int count = 1;
    private ArrayList<Product> mCart;

    public CartAdapter() {
        mCart = new ArrayList<>();
    }

    public void update(int position, Product call) {
        mCart.add(position, call);
        notifyItemChanged(position);
    }

    public void update(ArrayList<Product> calls) {
        mCart = calls;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cart, parent, false);

        return new MyViewHolder(listItemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Product product = mCart.get(position);
        Glide.with(mContext).load(product.getpImg()).into(holder.ivproductImage);
        holder.tvproductname.setText(product.getpName());
        holder.tvproductsize.setText(product.getpSize());
        holder.tvquntity.setText(count + "");
        final double a = Double.parseDouble(product.getpPrice());

        double price = count * a;
        holder.edPrice.setText((int) price + " OMR");


    }


    @Override
    public int getItemCount() {
        return mCart.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivcancelImage;
        ImageView ivproductImage;
        ImageView ivremoveImage;
        ImageView ivaddImage;
        TextView tvproductname;
        TextView tvproductsize;
        EditText tvquntity;
        TextView edPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivcancelImage = itemView.findViewById(R.id.iv_cansel);
            ivproductImage = itemView.findViewById(R.id.item_photo);
            ivremoveImage = itemView.findViewById(R.id.remove_img);
            ivaddImage = itemView.findViewById(R.id.add_img);
            tvproductname = itemView.findViewById(R.id.tv_Productname);
            tvproductsize = itemView.findViewById(R.id.tv_size);
            tvquntity = itemView.findViewById(R.id.tv_qunt);
            edPrice = itemView.findViewById(R.id.tv_price);




            ivaddImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;
                    tvquntity.setText(String.valueOf(count));

                }
            });
            ivremoveImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count--;
                    tvquntity.setText(String.valueOf(count));
                }
            });
            ivcancelImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DB_SQLlite db = new DB_SQLlite(mContext);

                    db.delete(getItemCount());
                    mCart.remove(getAdapterPosition());
                    notifyDataSetChanged();

                    Toast.makeText(mContext, "Product deleted!", Toast.LENGTH_SHORT).show();


                }
            });


        }

    }
}

