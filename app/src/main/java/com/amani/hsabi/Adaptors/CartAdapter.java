package com.amani.hsabi.Adaptors;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.amani.hsabi.R;
import com.amani.hsabi.activites.DB_SQLlite;
import com.amani.hsabi.models.Product;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    public static List<Product> selecteditems;
    int count;
    // private final CartFragment fragment;
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
        final int a = (product.getpPrice());

        int price = (count * a);

        holder.edPrice.setText((int) price + " OMR");

        SharedPreferences editorPrefernce = mContext.getSharedPreferences("total", MODE_PRIVATE);
        SharedPreferences.Editor editor = editorPrefernce.edit();

        SharedPreferences.Editor editor1 = editor.putInt("qunt", count);// or add toString() after if needed
        editor.apply();

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
                    try {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                        alertDialogBuilder.setMessage("Are you sure,You wanted to Remove?\n");
                        alertDialogBuilder.setCancelable(false);
                        AlertDialog.Builder yes = alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                DB_SQLlite db = new DB_SQLlite(mContext);

                                db.delete(getItemCount());
                                mCart.remove(getAdapterPosition());
                                notifyDataSetChanged();
                                notifyItemRemoved(getAdapterPosition());
                                Toast.makeText(mContext, "Product deleted!", Toast.LENGTH_SHORT).show();

                            }
                        });
                        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } catch (Exception e) {

                    }

                }




            });


        }

    }
}

