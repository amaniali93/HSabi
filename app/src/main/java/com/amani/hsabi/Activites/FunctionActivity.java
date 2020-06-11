package com.amani.hsabi.Activites;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.amani.hsabi.Adaptors.MyViewPagerAdapter;
import com.amani.hsabi.R;
import com.google.android.material.tabs.TabLayout;

public class FunctionActivity extends AppCompatActivity {

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
}
