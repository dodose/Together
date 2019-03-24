package com.example.blogapp.Activities;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.blogapp.Fragment.SearchFragment;
import com.example.blogapp.Fragment.HomeFragment;
import com.example.blogapp.Fragment.NotificationFragment;
import com.example.blogapp.Fragment.ProfileFragment;
import com.example.blogapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment seletedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

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
