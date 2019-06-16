package com.example.together.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.together.Fragment.TabFragment1;
import com.example.together.Fragment.TabFragment2;
import com.example.together.Fragment.TabFragment3;

public class PageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;
    private String code;


    public PageAdapter(FragmentManager fm, int numOfTabs,String code) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.code = code;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment fragment1 = new TabFragment1();
                Bundle bundle0 = new Bundle(1); // 파라미터는 전달할 데이터 개수
                bundle0.putString("code", code); // key , value
                fragment1.setArguments(bundle0);
                return fragment1;
            case 1:
                Fragment fragment2 = new TabFragment2(); // Fragment 생성
                Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
                bundle.putString("code", code); // key , value
                fragment2.setArguments(bundle);
                return fragment2;
            case 2:
                Fragment fragment3 = new TabFragment3(); // Fragment 생성
                Bundle bundle2 = new Bundle(1); // 파라미터는 전달할 데이터 개수
                bundle2.putString("code", code); // key , value
                fragment3.setArguments(bundle2);
                return fragment3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}