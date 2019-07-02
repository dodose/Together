package com.example.together.activities.map;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.together.R;

public class HotelMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_hotel);

    }

    public void onClick(View v){
        Intent intent = null;
        switch (v.getId()){
            case R.id.button1:
                intent = new Intent(this, Map1.class);
                break;
        }
        startActivity(intent);
    }

}
