package com.example.together.Activities.MyPetInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;


import com.example.together.Adapter.PetAdapter;
import com.example.together.Model.Pet;
import com.example.together.R;

import java.util.ArrayList;
import java.util.List;


public class MyPetListActivity extends AppCompatActivity {

    Button petadd;
    List<Pet> lsPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pet_list);

        petadd = findViewById(R.id.petadd);

        lsPet = new ArrayList<>();
        lsPet.add(new Pet("Test", "TestName1","TestTest",R.drawable.thevigitarian,1,2));
        lsPet.add(new Pet("Test", "TestName1","TestTest",R.drawable.thewildrobot,1,2));
        lsPet.add(new Pet("Test", "TestName1","TestTest",R.drawable.mariasemples,1,2));
        lsPet.add(new Pet("Test", "TestName1","TestTest",R.drawable.hediedwith,1,2));
        lsPet.add(new Pet("Test", "TestName1","TestTest",R.drawable.thevigitarian,1,2));
        lsPet.add(new Pet("Test", "TestName1","TestTest",R.drawable.thewildrobot,1,2));
        lsPet.add(new Pet("Test", "TestName1","TestTest",R.drawable.mariasemples,1,2));


        RecyclerView mypet = (RecyclerView) findViewById(R.id.recyclerview_my_dog);
        PetAdapter petAdapter = new PetAdapter(this,lsPet);
        mypet.setLayoutManager(new GridLayoutManager(this,3));
        mypet.setAdapter(petAdapter);

        petadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPetListActivity.this, MyPetRegActivity.class);
                startActivity(intent);

            }
        });
    }


}

