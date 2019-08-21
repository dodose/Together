package com.example.together.activities.petching;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.together.R;
import com.example.together.activities.HomeActivity;
import com.example.together.activities.petHospital.PetHospitalPetConditionActivity;
import com.example.together.adapter.PetHospitalizationSelectAdapter;
import com.example.together.adapter.PetchingMyPetInfoEditAdapter;
import com.example.together.model.Pet;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PetchingSelectPetActivity extends AppCompatActivity {

    private static final String TAG = "PetchingSelectPetActivi";
    public static String petcode;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private PetchingMyPetInfoEditAdapter petchingMyPetInfoEditAdapter;
    PetHospitalizationSelectAdapter petHospitalizationSelectAdapter;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    List<Pet> lsPet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petching_select_pet);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_back);
        getSupportActionBar().setTitle("");


        //프래그먼트로 데이터 전송
        final FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction t = manager.beginTransaction();


        viewPager = findViewById(R.id.main_petchingPetInfo_tabs_pager);
        petchingMyPetInfoEditAdapter = new PetchingMyPetInfoEditAdapter(getSupportFragmentManager());
        tabLayout = findViewById(R.id.myPetInfoEdit);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(petchingMyPetInfoEditAdapter);

        lsPet = new ArrayList<>();

        // My dog info



        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Pets").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren())
                {

                    String key = childSnapshot.getKey();
                    Pet pet = childSnapshot.getValue(Pet.class);

                    lsPet.add(pet);

                    Log.d(TAG, "야야야성별"+pet.getGender());
                    Log.d(TAG, "야야야생일"+pet.getBirthday());
                }


                LinearLayoutManager layoutManager = new LinearLayoutManager(PetchingSelectPetActivity.this, LinearLayoutManager.HORIZONTAL, false);
                RecyclerView recyclerview_dogs = findViewById(R.id.recyclerview_dogs);
                recyclerview_dogs.setLayoutManager(layoutManager);
                recyclerview_dogs.setNestedScrollingEnabled(false);

                petHospitalizationSelectAdapter = new PetHospitalizationSelectAdapter(PetchingSelectPetActivity.this, lsPet);
                recyclerview_dogs.setAdapter(petHospitalizationSelectAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true ;
    }


    //액션바 클릭 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(PetchingSelectPetActivity.this, PetchingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
