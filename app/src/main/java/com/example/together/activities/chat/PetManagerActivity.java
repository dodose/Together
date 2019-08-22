package com.example.together.activities.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.together.R;
import com.example.together.activities.petching.PetchingSelectPetActivity;
import com.example.together.adapter.PetHospitalizationSelectAdapter;
import com.example.together.adapter.PetManagerAdapter;
import com.example.together.model.Pet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PetManagerActivity extends AppCompatActivity {


    private static final String TAG = "PetManagerActivity";

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    List<Pet> lsPet;
    PetHospitalizationSelectAdapter petHospitalizationSelectAdapter;
    PetManagerAdapter petManagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_manager);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        lsPet = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Pets").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren())
                {

                    String key = childSnapshot.getKey();
                    Pet pet = childSnapshot.getValue(Pet.class);
                    Log.d(TAG, "캉테"+key);

                    lsPet.add(pet);

                    Log.d(TAG, "야야야성별"+pet.getGender());
                    Log.d(TAG, "야야야생일"+pet.getBirthday());
                }


                LinearLayoutManager layoutManager = new LinearLayoutManager(PetManagerActivity.this, LinearLayoutManager.HORIZONTAL, false);
                RecyclerView recyclerview_dogs = findViewById(R.id.recyclerview_dogs);
                recyclerview_dogs.setLayoutManager(layoutManager);
                recyclerview_dogs.setNestedScrollingEnabled(false);

                petManagerAdapter = new PetManagerAdapter(PetManagerActivity.this, lsPet);
                recyclerview_dogs.setAdapter(petManagerAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }
}
