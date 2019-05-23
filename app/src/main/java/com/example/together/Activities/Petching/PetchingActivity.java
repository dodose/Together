package com.example.together.Activities.Petching;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.together.R;

import java.util.ArrayList;
import java.util.List;

public class PetchingActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    private final List<Fragment> mFragmnetList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petching);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // 프래그먼트 추가하기
//        adapter.AddFragment(new FindFriend_Fragment(), "친구찾기");
        adapter.AddFragment(new Lounge_Fragment(), "라운지");
        adapter.AddFragment(new Message_Fragment(), "메시지");

        // adapter 세팅
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
