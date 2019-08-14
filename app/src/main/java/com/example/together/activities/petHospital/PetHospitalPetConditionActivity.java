package com.example.together.activities.petHospital;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.activities.HomeActivity;
import com.example.together.activities.aircalendar.AirCalendarDatePickerActivity;
import com.example.together.activities.goodbyePet.GoodbyeSelectActivity;
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
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PetHospitalPetConditionActivity extends AppCompatActivity {

    public final static int REQUEST_CODE = 1;

    private static final String TAG = "PetHospitalPetCondition";

    Button petHospital_search_btn, date_select;

    //이게 새로운 거
    Button select_clinic;

    private String selectDate;

    PetHospitalizationSelectAdapter petHospitalizationSelectAdapter;

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    List<Pet> lsPet;
    ArrayList<String> pet_condition = new ArrayList<String>();
    private String date;

    public static String petcode;
    Toolbar hosToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_hospital_pet_condition);

        //증상 채크박스선택

        hosToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(hosToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


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

        //진료과목 선택 버튼
        select_clinic = findViewById(R.id.select_clinic);

        //퉭
        select_clinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetHospitalPetConditionActivity.this, PetHospitalSelectBar.class);
                startActivityForResult(intent,2);

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
                intent.putExtra("PETCATEGORY", select_clinic.getText());
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

    //액션바 등록
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true ;
    }


    //액션바 클릭 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(PetHospitalPetConditionActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            switch (resultCode){

                case RESULT_OK:
                    Log.e("ㅇㅇ","들어와잇단다");
                    date = data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE);
                    date_select.setText(date);
                    break;
                case 2:
                    select_clinic.setText(data.getExtras().getString("result"));
                    break;
            }




    }


    public static void myPetcode(String selected_my_pet) {
        petcode = selected_my_pet;
    }


}
