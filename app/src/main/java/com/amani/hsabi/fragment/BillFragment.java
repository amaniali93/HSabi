package com.amani.hsabi.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.amani.hsabi.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class BillFragment extends Fragment {
    TextView tvtotal;
    ImageView imgBarcode;
    TextView date;
    private Context mContext;
    private int mPriceTotal;
    private View view;

    public BillFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View parentView = inflater.inflate(R.layout.fragment_bill, container, false);
        tvtotal = parentView.findViewById(R.id.totalbill_tv);
        date = parentView.findViewById(R.id.tv_date);
        imgBarcode = parentView.findViewById(R.id.barcode_img);
        tvtotal.setText(mPriceTotal);
        barcodegenerat(view);
        String date_n = new SimpleDateFormat("MM dd, yyyy", Locale.getDefault()).format(new Date());
        date.setText(date_n);
        return parentView;
    }

    public void setPricetotal(int pricetotal) {
        mPriceTotal = pricetotal;
    }

    public void barcodegenerat(View view) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(tvtotal.getText().toString(), BarcodeFormat.CODE_128, imgBarcode.getWidth(), imgBarcode.getHeight());
            Bitmap bitmap = Bitmap.createBitmap(imgBarcode.getWidth(), imgBarcode.getHeight(), Bitmap.Config.RGB_565);
            for (int i = 0; i < imgBarcode.getWidth(); i++) {
                for (int j = 0; j < imgBarcode.getHeight(); j++) {
                    bitmap.setPixel(i, j, bitMatrix.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }
}
