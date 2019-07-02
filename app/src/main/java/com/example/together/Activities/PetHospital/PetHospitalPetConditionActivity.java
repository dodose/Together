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
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class PetHospitalPetConditionActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "PetHospitalPetCondition";
    private CheckBox dpt_all, dpt_internal, dpt_surgery, dpt_skin, dpt_eyes, dpt_dentist, dpt_birth;

    Button petHospital_search_btn, date_select;


    FirebaseUser firebaseUser;
    DatabaseReference reference;
    List<Pet> lsPet;
    boolean[] checkedItems;
    ArrayList<String> pet_condition = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_hospital_pet_condition);

        //증상 채크박스선택

        //종합
        dpt_all = findViewById(R.id.dpt_all);
        //내과
        dpt_internal = findViewById(R.id.dpt_internal);
        //외과
        dpt_surgery = findViewById(R.id.dpt_surgery);
        //피부과
        dpt_skin = findViewById(R.id.dpt_skin);
        //안과
        dpt_eyes = findViewById(R.id.dpt_eyes);
        //치과
        dpt_dentist = findViewById(R.id.dpt_dentist);
        //산과
        dpt_birth = findViewById(R.id.dpt_birth);


        // 달력 선택
        date_select = findViewById(R.id.date_select);

        date_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //병원 검색버튼
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


    // 채크박스 선택 부분
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        pet_condition.clear();

        if (dpt_all.isChecked()) pet_condition.add(dpt_all.getText().toString());
        if (dpt_internal.isChecked()) pet_condition.add(dpt_internal.getText().toString());
        if (dpt_surgery.isChecked()) pet_condition.add(dpt_surgery.getText().toString());
        if (dpt_skin.isChecked()) pet_condition.add(dpt_skin.getText().toString());
        if (dpt_eyes.isChecked()) pet_condition.add(dpt_eyes.getText().toString());
        if (dpt_dentist.isChecked()) pet_condition.add(dpt_dentist.getText().toString());
        if (dpt_birth.isChecked()) pet_condition.add(dpt_birth.getText().toString());


        for (int i = 0; i<pet_condition.size(); i++){
            Log.d(TAG, "onCheckedChanged: "+pet_condition.get(i)+"채크박스 값");
        }

        //   Intent intent = new Intent(PetHospitalPetConditionActivity.this, );
    }
}
