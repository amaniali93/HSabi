package com.amani.hsabi.activites;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.amani.hsabi.Interfaces.MediatorInterface;
import com.amani.hsabi.R;
import com.amani.hsabi.fragment.BillFragment;
import com.amani.hsabi.models.Billinfo;
import com.amani.hsabi.models.MyContats;

import static com.amani.hsabi.models.MyContats.FRAGMENT_BILL_FRAGMENT;
import static com.amani.hsabi.models.MyContats.KEY_FRAGMENT_TO_LOAD;

public class SecondaryActivity extends AppCompatActivity implements MediatorInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scondary);

        if (getIntent() != null) {

            if (FRAGMENT_BILL_FRAGMENT.equals(getIntent().getStringExtra(KEY_FRAGMENT_TO_LOAD))) {
                Billinfo billinfo = (Billinfo) getIntent().getSerializableExtra(MyContats.DATA_TO_PASS);
                BillFragment fragment = new BillFragment();
                fragment.setBill(billinfo);
                changeFragmentTo(fragment, BillFragment.class.getSimpleName());
            }
        }
    }

    @Override
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

    /**
     * @return BackStackEntryCount
     */
    private int getStackCount() {
        return getSupportFragmentManager().getBackStackEntryCount();
    }

    @Override
    public void onBackPressed() {
        if (getStackCount() <= 1) {
            finish();
        } else {
            super.onBackPressed();
        }

    }
}