package com.example.together.activities;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.together.fragment.SearchFragment;
import com.example.together.fragment.HomeFragment;
import com.example.together.fragment.NotificationFragment;
import com.example.together.fragment.ProfileFragment;
import com.example.together.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity  {
    BottomNavigationView bottomNavigationView;
    Fragment seletedFragment = null;

    private Boolean backKeyPressed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


        Bundle intent = getIntent().getExtras();
        if (intent != null){
            String publisher = intent.getString("publisherid");

            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
            editor.putString("profileid", publisher);
            editor.apply();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ProfileFragment()).commit();

        } else{

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment()).commit();

        }

    }

    @Override
    public void onBackPressed() {
        if (backKeyPressed) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "뒤로가기를 한번 더 누르시면 앱을 종료합니다.",
                    Toast.LENGTH_SHORT).show();
            backKeyPressed = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backKeyPressed = false;
                }
            }, 3 * 1000);
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()){
                        case R.id.nav_bottomBar_home:
                            seletedFragment = new HomeFragment();
                            break;

                        case R.id.nav_bottomBar_search:
                            seletedFragment = new SearchFragment();
                            break;

                        case R.id.nav_bottomBar_notification:
                            seletedFragment = new NotificationFragment();
                            break;


                        case R.id.nav_bottomBar_profile:
                            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
                            seletedFragment = new ProfileFragment();
                            break;


                    }


                    if (seletedFragment != null){
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, seletedFragment).commit();
                    }

                    return true;
                }
            };





}
