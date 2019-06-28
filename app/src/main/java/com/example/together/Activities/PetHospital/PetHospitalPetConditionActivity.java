package com.example.together.Activities.PetHospital;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.example.together.Activities.MyPetInfo.MyPetListActivity;
import com.example.together.Adapter.PetAdapter;
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

public class PetHospitalPetConditionActivity extends AppCompatActivity {

    private static final String TAG = "PetHospitalPetCondition";

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    List<Pet> lsPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_hospital_pet_condition);

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

                    pet.getPetname();
                    pet.getPetimageurl();

                    Log.d(TAG, pet+"펫이당");
                    Log.wtf(TAG, "did you get petweight? : "+pet.getPetweight());
                    Log.wtf(TAG, "did you get Birthday?: "+pet.getBirthday());
                    Log.wtf(TAG, "did you get name?"+pet.getPetname());
                    Log.wtf(TAG, "did u get image url?"+pet.getPetimageurl());
                    lsPet.add(pet);
                }

                RecyclerView recyclerview_dogs = (RecyclerView) findViewById(R.id.recyclerview_dogs);
                PetAdapter petAdapter = new PetAdapter(PetHospitalPetConditionActivity.this,lsPet);
                recyclerview_dogs.setAdapter(petAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}
