package com.amani.hsabi.Adaptors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.amani.hsabi.fragment.CartFragment;
import com.amani.hsabi.fragment.ScanFragment;

public class MyViewPagerAdapter extends FragmentPagerAdapter {



    private CharSequence[] tabTitles = {"Scan", "Cart"};

    public MyViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

    }


    //here we select the fragment and specific its index
    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                return new ScanFragment();
            case 1:
                return new CartFragment();
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    // here we can set/ rename tabs titles
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


}
