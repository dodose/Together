package com.example.blogapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.blogapp.Activities.HomeActivity;
import com.example.blogapp.Activities.PostActivity;
import com.example.blogapp.R;


public class HomeFragment extends Fragment {

    ImageButton nav_top_post;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        nav_top_post = view.findViewById(R.id.nav_top_post);

        nav_top_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Inflate the layout for this fragment
        return view;

    }








}
