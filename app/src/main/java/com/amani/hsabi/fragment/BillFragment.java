package com.amani.hsabi.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.amani.hsabi.R;
import com.amani.hsabi.models.Billinfo;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class BillFragment extends Fragment {
    TextView tvtotal;

    TextView date;
    private Context mContext;
    private Billinfo mBillInfo;
    private DatabaseReference mRef;


    public BillFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mRef = database.getReference("Bills");


        tvtotal = parentView.findViewById(R.id.totalbill_tv);
        date = parentView.findViewById(R.id.tv_date);
        ImageView imgBarcode = parentView.findViewById(R.id.barcode_img);
        tvtotal.setText(mBillInfo.getbPrice() + "");
        startGenerateQRCode(imgBarcode);
        String date_n = new SimpleDateFormat("MM dd, yyyy", Locale.getDefault()).format(new Date());
        date.setText(date_n);
        return parentView;
    }


    public void startGenerateQRCode(final ImageView imgBarcode) {

        imgBarcode.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //this is the info which will be display after scanning the QR code
                    String infoToDisplay = "A\nB\nD\nC\nD\nF\nd\nd A\nB\nD\nC\nD\nF\nd\nd";

                    generateQRCode(imgBarcode, infoToDisplay);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });


    }


    private void generateQRCode(ImageView imgBarcode, String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.QR_CODE,
                    imgBarcode.getWidth(), imgBarcode.getHeight(), null
            );
        } catch (IllegalArgumentException Illegalargumentexception) {
            return;
        }
        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];
        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRCodeBlackColor) : getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, bitMatrixWidth, 0, 0, bitMatrixWidth, bitMatrixHeight);


        Glide.with(mContext).load(bitmap).into(imgBarcode);
    }


    public void setBill(Billinfo billinfo) {
        mBillInfo = billinfo;
    }
}
