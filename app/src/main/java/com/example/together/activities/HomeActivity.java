package com.example.together.activities;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.together.fragment.SearchFragment;
import com.example.together.fragment.HomeFragment;
import com.example.together.fragment.NotificationFragment;
import com.example.together.fragment.ProfileFragment;
import com.example.together.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements LifecycleObserver {


    BottomNavigationView bottomNavigationView;
    Fragment seletedFragment = null;
    DatabaseReference reference;
    FirebaseUser firebaseUser;


    private Boolean backKeyPressed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

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


    // LifeCycle을 채크하여 앱 꺼짐 여부를 채크후 온/오프라인 표시

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void onAppBackgrounded() {

        Log.d("MyApp", "App in background");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());


        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", "offline");

        reference.updateChildren(hashMap);


    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void onAppForegrounded() {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        Log.d("MyApp", "App in foreground");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", "online");

        reference.updateChildren(hashMap);


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
