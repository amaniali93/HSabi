package com.amani.hsabi.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.amani.hsabi.Adaptors.MyViewPagerAdapter;
import com.amani.hsabi.R;
import com.amani.hsabi.fragment.BillFragment;
import com.amani.hsabi.fragment.CartFragment;
import com.amani.hsabi.models.Billinfo;
import com.amani.hsabi.models.MyContats;
import com.amani.hsabi.models.Product;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FunctionActivity extends AppCompatActivity implements CartFragment.OnBillRequestListener {
    private MyViewPagerAdapter mAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);


        TabLayout tabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);

        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(mAdapter);
        tabs.setupWithViewPager(viewPager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_about_app) {
            AlertDialog alertDialog = new AlertDialog.Builder(FunctionActivity.this).create();
            alertDialog.setTitle(getString(R.string.Last_Bill));
            alertDialog.setMessage("from DB");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok_1),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
       /* Log.i("FunctionActivity", "MenuItem selected " + id);
        if (id == R.id.nav_history) {
            Log.i("FunctionActivity", "switch to HistoryFragment");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(new HistoryFragment(),HistoryFragment.class.getSimpleName());
            ft.commit();
            Log.i("FunctionActivity", "finish switch to HistoryFragment");
            //changeFragmentTo(new HistoryFragment(), HistoryFragment.class.getSimpleName());
        }
*/
        if (id == R.id.nav_about_app) {
            AlertDialog alertDialog = new AlertDialog.Builder(FunctionActivity.this).create();
            alertDialog.setTitle(getString(R.string.About_HSabi));
            alertDialog.setMessage(getString(R.string.msg_1));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok_1),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        if (id == R.id.nav_contact) {
            AlertDialog alertDialog = new AlertDialog.Builder(FunctionActivity.this).create();
            alertDialog.setTitle(getString(R.string.Contact_us));
            alertDialog.setMessage(getString(R.string.con_1));
            alertDialog.show();
        }
        if (id == R.id.nav_logout) {
            LogOut();
        }


        return super.onOptionsItemSelected(item);
    }

    public void LogOut() {
        FirebaseAuth.getInstance().signOut();

        Intent i = new Intent(FunctionActivity.this, MainActivity.class);
        startActivity(i);
        finish();

    }


    @Override
    public void onBillRequest(ArrayList<Product> products, int totalPrice) {

        Intent intent = new Intent(FunctionActivity.this, SecondaryActivity.class);
        intent.putExtra(MyContats.KEY_FRAGMENT_TO_LOAD, BillFragment.class.getSimpleName());
        Billinfo billinfo = new Billinfo();
        billinfo.setbDate(getCurrentData());
        billinfo.setbPrice(totalPrice + "");
        billinfo.setProducts(products);
        intent.putExtra(MyContats.DATA_TO_PASS, billinfo);
        startActivity(intent);
    }

    private String getCurrentData() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MMMM/yyyy");
        return df.format(c);
    }
}
