package com.example.together.Activities.MyPetInfo;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;


import com.example.together.Adapter.PetAdapter;
import com.example.together.Fragment.ProfileFragment;
import com.example.together.Model.Pet;
import com.example.together.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class MyPetListActivity extends AppCompatActivity {

    private static String TAG = "MyPetListActivity";


    FirebaseUser firebaseUser;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference databaseReference;
    Button petadd;
    List<Pet> lsPet;
    ImageView backTo; // layout : activity_my_pet_list.xml


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pet_list);

        Pet pet = new Pet();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Pets").child(firebaseUser.getUid());


        petadd = findViewById(R.id.petadd);
        backTo = findViewById(R.id.backTo);  //activity_my_pet_list뒤로 가기 버튼

        lsPet = new ArrayList<>();



        RecyclerView recyclerview_dogs = (RecyclerView) findViewById(R.id.recyclerview_dogs);
        PetAdapter petAdapter = new PetAdapter(this,lsPet);
        recyclerview_dogs.setLayoutManager(new GridLayoutManager(this,3));
        recyclerview_dogs.setAdapter(petAdapter);

        petadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPetListActivity.this, MyPetRegActivity.class);
                startActivity(intent);

            }
        });

    }


}

