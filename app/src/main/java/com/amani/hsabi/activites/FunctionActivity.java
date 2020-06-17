package com.amani.hsabi.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.amani.hsabi.Adaptors.MyViewPagerAdapter;
import com.amani.hsabi.R;
import com.amani.hsabi.fragment.HistoryFragment;
import com.amani.hsabi.fragment.LoginFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class FunctionActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);


        TabLayout tabs = findViewById(R.id.tabs);
        ViewPager viewPager = findViewById(R.id.view_pager);

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        tabs.setupWithViewPager(viewPager);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public void changeFragmentTo(Fragment fragmentToDisplay, String tag) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        ft.replace(R.id.fl_host, fragmentToDisplay, tag);
        if (fm.findFragmentByTag(tag) == null) {
            ft.addToBackStack(tag);
        }
        ft.commit();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.nav_history) {
            changeFragmentTo(new HistoryFragment(), HistoryFragment.class.getSimpleName());
        }

        if(id==R.id.nav_about_app) {
            AlertDialog alertDialog = new AlertDialog.Builder(FunctionActivity.this).create();
            alertDialog.setTitle("About HSabi");
            alertDialog.setMessage("HSabi App is developed by Amani This App allow for customer to scan and collect the things and print digital Bill");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        if(id==R.id.nav_contact) {
            AlertDialog alertDialog = new AlertDialog.Builder(FunctionActivity.this).create();
            alertDialog.setTitle("Contact us");
            alertDialog.setMessage("you can contact us by Phone +9689568338 Or by Email: alhassaniamani@gmail.com");
            alertDialog.show();
        }
        if(id==R.id.nav_logout) {
            LogOut();
        }

        return super.onOptionsItemSelected(item);
    }

    public void LogOut(){
        FirebaseAuth.getInstance().signOut();
        changeFragmentTo(new LoginFragment(), LoginFragment.class.getSimpleName());
    }

}
