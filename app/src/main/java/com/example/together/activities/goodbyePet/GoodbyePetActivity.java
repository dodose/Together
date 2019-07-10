package com.example.together.activities.goodbyePet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.together.activities.map.MapsActivity;
import com.example.together.R;

public class GoodbyePetActivity extends AppCompatActivity {

    ImageButton goodbyeMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodbye_pet);

        goodbyeMap = findViewById(R.id.goodbyeMap);




        goodbyeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GoodbyePetActivity.this, MapsActivity.class));
            }
        });


    }





}
