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
import com.amani.hsabi.models.Product;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CartAdapter  extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private Context mContext;

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

        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cart, parent, false);

        return new MyViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {

        Product product = mCart.get(position);


        Glide.with(mContext).load( product.getpImg()).into(holder.ivproductImage);
        holder.tvproductname.setText(product.getpName());
        holder.tvproductsize.setText(product.getpSize());
        holder.tvquntity.setText(getItemCount());

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
        TextView tvquntity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           ivcancelImage = itemView.findViewById(R.id.iv_cansel);
           ivproductImage = itemView.findViewById(R.id.item_photo);
           ivremoveImage = itemView.findViewById(R.id.remove_img);
           ivaddImage = itemView.findViewById(R.id.add_img);
           tvproductname = itemView.findViewById(R.id.tv_Productname);
           tvproductsize = itemView.findViewById(R.id.tv_size);
           tvquntity = itemView.findViewById(R.id.tv_qunt);

        }
       // public void showData(){
           // ArrayList<String> listdata=db.getProducts();
         //   ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.list_item_cart,listdata);
      //  }
    }
}

