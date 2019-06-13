package com.example.together.Activities.GoodbyePet;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.example.together.Adapter.SectionPageAdapter;
import com.example.together.Fragment.tab1Fragment;
import com.example.together.Fragment.tab2Fragment;
import com.example.together.Fragment.tab3Fragment;
import com.example.together.R;

public class GoodbyepetMenuSelectActivity extends AppCompatActivity {

    private static  final String TAG = "fowMainActivity";

    private SectionPageAdapter mSectionPageAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodbyepet_menu_select);
        Log.d(TAG,"onCreate:String.");

        mSectionPageAdapter = new SectionPageAdapter((getSupportFragmentManager()));

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private  void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new tab1Fragment(),"메뉴");
        adapter.addFragment(new tab2Fragment(),"클린리뷰");
        adapter.addFragment(new tab3Fragment(),"정보");
        viewPager.setAdapter(adapter);
    }

}

