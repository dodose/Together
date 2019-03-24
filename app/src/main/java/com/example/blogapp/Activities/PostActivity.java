package com.example.blogapp.Activities;

import android.app.Fragment;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.blogapp.R;

public class PostActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }
}
