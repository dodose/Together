package com.example.together.activities.petching;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.example.together.R;
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

        selct_pets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetchingActivity.this, PetchingSelectPetActivity.class);
                startActivity(intent);
            }
        });

    }

}
