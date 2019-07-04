package com.example.together.activities.petHospital;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.together.activities.aircalendar.AirCalendarDatePickerActivity;
import com.example.together.adapter.PetHospitalizationSelectAdapter;
import com.example.together.model.Pet;
import com.example.together.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.together.activities.aircalendar.core.AirCalendarIntent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PetHospitalPetConditionActivity extends AppCompatActivity  {

    public final static int REQUEST_CODE = 1;

    private static final String TAG = "PetHospitalPetCondition";
    private CheckBox dpt_all, dpt_internal, dpt_surgery, dpt_skin, dpt_eyes, dpt_dentist, dpt_birth;

    Button petHospital_search_btn, date_select;

    private String selectDate;

    PetHospitalizationSelectAdapter petHospitalizationSelectAdapter;

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    List<Pet> lsPet;
    ArrayList<String> pet_condition = new ArrayList<String>();
    private String date;

    public static String petcode;





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
                AirCalendarIntent intent = new AirCalendarIntent(PetHospitalPetConditionActivity.this);
                intent.setSelectButtonText("Select");
                intent.setResetBtnText("Reset");
                intent.setWeekStart(Calendar.MONDAY);
                intent.putExtra("pet_hospital",-1);
                startActivityForResult(intent, REQUEST_CODE);
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
                intent.putExtra("PETCODE", petcode);
                intent.putExtra("SELECTDAY",date);
                intent.putStringArrayListExtra("PETCATEGORY", pet_condition);
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
                    RecyclerView recyclerview_dogs = findViewById(R.id.recyclerview_dogs);
                    recyclerview_dogs.setLayoutManager(layoutManager);

                    petHospitalizationSelectAdapter = new PetHospitalizationSelectAdapter(PetHospitalPetConditionActivity.this, lsPet);
                    recyclerview_dogs.setAdapter(petHospitalizationSelectAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

            date = data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE);
//            data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE);
            if(data != null)
            {
                Toast.makeText(this, "Select Date range : " + data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE) + " ~ " + data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE), Toast.LENGTH_SHORT).show();
            }
        }
    }



    // 채크박스 값 선택여부
    public void selectItem(View view)
    {
        boolean checked = ((CheckBox) view).isChecked();
            switch (view.getId())
            {
                //종합
                case R.id.dpt_all:
                    if (checked)
                    {
                        pet_condition.add("종합");
                    }
                    else
                    {
                        pet_condition.remove("종합");
                    }
                    break;

                //내과
                case R.id.dpt_internal:
                    if (checked)
                    {
                        pet_condition.add("내과");
                    }
                    else
                    {
                        pet_condition.remove("내과");
                    }
                    break;

                //외과
                case R.id.dpt_surgery:
                    if (checked)
                    {
                        pet_condition.add("외과");
                    }
                    else
                    {
                        pet_condition.remove("외과");
                    }
                    break;
                //피부과
                case R.id.dpt_skin:
                    if (checked)
                    {
                        pet_condition.add("피부과");
                    }
                    else
                    {
                        pet_condition.remove("피부과");
                    }
                    break;
                //안과
                case R.id.dpt_eyes:
                    if (checked)
                    {
                        pet_condition.add("안과");
                    }
                    else
                    {
                        pet_condition.remove("안과");
                    }
                    break;
                //치과
                case R.id.dpt_dentist:
                    if (checked)
                    {
                        pet_condition.add("치과");
                    }
                    else
                    {
                        pet_condition.remove("치과");
                    }
                    break;
                //산과
                case R.id.dpt_birth:
                    if (checked)
                    {
                        pet_condition.add("산과");
                    }
                    else
                    {
                        pet_condition.remove("산과");
                    }
                    break;
            }


            //값들어오는지 채크해보자
            for (int i=0; i<pet_condition.size(); i++)
            {
                Log.d(TAG, "selectItem: 병원 "+pet_condition.get(i));
            }




    }

    public static void myPetcode(String selected_my_pet) {
        petcode = selected_my_pet;
    }


}
