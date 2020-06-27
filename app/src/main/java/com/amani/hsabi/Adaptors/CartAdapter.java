package com.amani.hsabi.Adaptors;

import android.content.Context;
import android.content.DialogInterface;
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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    public static List<Product> selecteditems;

    // private final CartFragment fragment;
    private Context mContext;
    private ArrayList<Product> mCart;
    private CartListener mListener;

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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final Product product = mCart.get(position);
        Glide.with(mContext).load(product.getpImg()).into(holder.ivproductImage);
        holder.tvproductname.setText(product.getpName());
        holder.tvproductsize.setText(product.getpSize());
        holder.tvquntity.setText(product.getQunt() + "");
        final int a = (product.getpPrice());

        int price = (product.getQunt() * a);

        holder.edPrice.setText((int) price + "   " + mContext.getString(R.string.or_1));

        holder.ivaddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int r = product.getQunt() + 1;
                product.setQunt(r);
                holder.tvquntity.setText(String.valueOf(r));
                int price = (product.getQunt() * a);
                holder.edPrice.setText((int) price + "   " + mContext.getString(R.string.or_1));
                if (mListener != null) {
                    mListener.onDataChange(mCart);
                }
            }
        });
        holder.ivremoveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getQunt() > 1) {
                    int r = product.getQunt() - 1;
                    product.setQunt(r);
                    holder.tvquntity.setText(String.valueOf(r));
                    int price = (product.getQunt() * a);
                    holder.edPrice.setText((int) price + "   " + mContext.getString(R.string.or_1));
                    if (mListener != null) {
                        mListener.onDataChange(mCart);
                    }
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return mCart.size();
    }


    public void setupCartListener(CartListener cartListener) {
        mListener = cartListener;
    }

    public ArrayList<Product> getProducts() {
        return mCart;
    }

    public interface CartListener {
        void onDataChange(ArrayList<Product> newProductsArrayList);
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


            ivcancelImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                        alertDialogBuilder.setMessage(R.string.remove_1);
                        alertDialogBuilder.setCancelable(false);
                        AlertDialog.Builder yes = alertDialogBuilder.setPositiveButton(R.string.y1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                DB_SQLlite db = new DB_SQLlite(mContext);

                                db.delete(getItemCount());
                                mCart.remove(getAdapterPosition());
                                notifyDataSetChanged();
                                notifyItemRemoved(getAdapterPosition());
                                Toast.makeText(mContext, R.string.Product_deleted, Toast.LENGTH_SHORT).show();

                            }
                        });
                        alertDialogBuilder.setNegativeButton(R.string.no_2, new DialogInterface.OnClickListener() {
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

