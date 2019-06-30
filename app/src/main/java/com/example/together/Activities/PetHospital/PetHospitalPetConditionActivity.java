package com.example.together.Activities.PetHospital;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.together.Activities.MyPetInfo.MyPetListActivity;
import com.example.together.Adapter.PetAdapter;
import com.example.together.Adapter.PetHospitalizationSelectAdapter;
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

    Button petHospital_search_btn;

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    List<Pet> lsPet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_hospital_pet_condition);

        petHospital_search_btn = findViewById(R.id.petHospital_search_btn);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        lsPet = new ArrayList<>();



        petHospital_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetHospitalPetConditionActivity.this, PetHospitalListActivity.class);
                startActivity(intent);
            }
        });



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

                    lsPet.add(pet);
                }

                LinearLayoutManager layoutManager = new LinearLayoutManager(PetHospitalPetConditionActivity.this, LinearLayoutManager.HORIZONTAL, false);
                RecyclerView recyclerview_dogs =  findViewById(R.id.recyclerview_dogs);
                recyclerview_dogs.setLayoutManager(layoutManager);
                PetHospitalizationSelectAdapter petHospitalizationSelectAdapter = new PetHospitalizationSelectAdapter(PetHospitalPetConditionActivity.this, lsPet);
                recyclerview_dogs.setAdapter(petHospitalizationSelectAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}
