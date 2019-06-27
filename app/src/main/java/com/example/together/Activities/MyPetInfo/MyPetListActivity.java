package com.example.together.Activities.MyPetInfo;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import android.widget.Toast;


import com.example.together.Adapter.PetAdapter;
import com.example.together.Fragment.ProfileFragment;
import com.example.together.Model.Pet;
import com.example.together.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MyPetListActivity extends AppCompatActivity {

    private static String TAG = "MyPetListActivity";


    FirebaseUser firebaseUser;
    DatabaseReference reference;
    Button petadd;
    List<Pet> lsPet;

    ImageView backTo; // layout : activity_my_pet_list.xml


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pet_list);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        petadd = findViewById(R.id.petadd);
        backTo = findViewById(R.id.backTo);  //activity_my_pet_list뒤로 가기 버튼

        lsPet = new ArrayList<>();


        reference = FirebaseDatabase.getInstance().getReference("Pets").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren())
                {
                    String key = childSnapshot.getKey();
                    Log.d(TAG,key+"키키키");
                    Pet pet = childSnapshot.getValue(Pet.class);
                    String image = childSnapshot.child("petimageurl").getValue() + "";

                    pet.getPetname();
                    pet.getPetimageurl();
                    pet.getBirthday();

                    Log.d(TAG, pet+"펫이당");
                    Log.wtf(TAG, "Stack support "+image);
                    Log.wtf(TAG, "did you get petweight? : "+pet.getPetweight());
                    Log.wtf(TAG, "did you get Birthday?: "+pet.getBirthday());
                    Log.wtf(TAG, "did you get name?"+pet.getPetname());
                    Log.wtf(TAG, "did u get image url?"+pet.getPetimageurl());
                    lsPet.add(pet);
                }

                RecyclerView recyclerview_dogs = (RecyclerView) findViewById(R.id.recyclerview_dogs);
                PetAdapter petAdapter = new PetAdapter(MyPetListActivity.this,lsPet);
                recyclerview_dogs.setLayoutManager(new GridLayoutManager(MyPetListActivity.this,3));
                recyclerview_dogs.setAdapter(petAdapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.wtf(TAG, "onCancelled: 펫이미지 가져오는데 문제 발생! ");
            }
        });




        // add Pet
        petadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPetListActivity.this, MyPetRegActivity.class);
                startActivity(intent);

            }
        });

    }


}

