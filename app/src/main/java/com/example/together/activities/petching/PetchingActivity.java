package com.example.together.activities.petching;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.example.together.R;
import com.example.together.activities.HomeActivity;
import com.example.together.activities.petHospital.PetHospitalPetConditionActivity;
import com.example.together.adapter.PetchingTabsAccessorAdapter;
import com.google.android.material.tabs.TabLayout;

public class PetchingActivity extends AppCompatActivity
{

    private Toolbar mToolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PetchingTabsAccessorAdapter petchingTabsAccessorAdapter;
    private Button selct_pets;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petching);

       viewPager = findViewById(R.id.main_tabs_pager);
       petchingTabsAccessorAdapter = new PetchingTabsAccessorAdapter(getSupportFragmentManager());
       viewPager.setAdapter(petchingTabsAccessorAdapter);

        tabLayout = (TabLayout) findViewById(R.id.main_tabs);
        tabLayout.setupWithViewPager(viewPager);

        selct_pets = findViewById(R.id.selct_pets);

        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_back);
        getSupportActionBar().setTitle("");


        selct_pets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetchingActivity.this, PetchingSelectPetActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true ;
    }


    //액션바 클릭 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(PetchingActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
