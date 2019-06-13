package com.example.together.Activities.GoodbyePet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.together.R;

public class GoodbyeSelectActivity extends AppCompatActivity {

    ImageButton goto_rainbow_search, goto_goodbypet_gallery, goodbyePet_how_to_use;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodbye_select);


        goto_rainbow_search = findViewById(R.id.goto_rainbow_search);
        goto_goodbypet_gallery = findViewById(R.id.goto_goodbypet_gallery);
        goodbyePet_how_to_use = findViewById(R.id.goodbyePet_how_to_use);

        goto_rainbow_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodbyeSelectActivity.this, GoodbyePetTimeDateSelectActivity.class );
                startActivity(intent);
            }
        });


        goto_goodbypet_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodbyeSelectActivity.this, GoodbyeMemorialActivity.class );
                startActivity(intent);
            }
        });


        goodbyePet_how_to_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodbyeSelectActivity.this, GoodbyeMemorialActivity.class );
                startActivity(intent);
            }
        });


    }



}
