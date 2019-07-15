package com.example.together.activities.petHospital;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.together.R;
import com.example.together.adapter.TimesetAdapter;
import com.example.together.fragment.Hos_or_fragment1;
import com.example.together.fragment.Hos_or_fragment2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class PetHospitalCheckReservationInfoActivity extends AppCompatActivity {


    private boolean isFragmentA = true ;


    //이름셀렉트및 정보셀렉
    DatabaseReference reference;
    FirebaseUser firebaseUser;

    String petname;

    Toolbar mToolbar;

    //Textview&&Button
    Button selectPet;
    Button selectDate;
    Button selectEtp;

    ImageButton left;
    ImageButton right;

    //프래그먼트
    Hos_or_fragment1 fragment1;
    Fragment fragment2;

    String petcode;
    String dateday;
    String topName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_hospital_check_reservation_info);

        Bundle Bx = getIntent().getExtras();
        petcode = Bx.getString("petcode");
        dateday = Bx.getString("date");
        topName = Bx.getString("etp_name");

        //툴바 선언
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        //액션바 왼쪽에 버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_back);
        getSupportActionBar().setTitle("");

        selectPet = findViewById(R.id.pet_select_count);
        selectDate = findViewById(R.id.date_select);
        selectEtp = findViewById(R.id.etp_select);

        left = findViewById(R.id.LeftBtn);
        right = findViewById(R.id.RightBtn);

        selectDate.setText(dateday);
        selectEtp.setText(topName);

        //petcode를 통해 uid값을 가져옴
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Pets").child(firebaseUser.getUid()).child(petcode);

        reference.child("petname").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue() != null) {
                        try {
                            petname = (String) snapshot.getValue();
                            selectPet.setText(petname);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("TAG", " it's null.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //프래그먼트를 보여주기
        fragment1 = new Hos_or_fragment1();

        fragment2 = new Hos_or_fragment2(topName,dateday);

        left.setVisibility(View.INVISIBLE);
        //프레그먼트를 메니져로 보여줌
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_rigth).add(R.id.container,fragment1).commit();

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                left.setVisibility(View.INVISIBLE);
                String A = "1";
                switchFragment(A);
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                left.setVisibility(View.VISIBLE);
                String B = "2";
                switchFragment(B);

            }
        });

    }



    public void switchFragment(String value){
        Fragment selected = null;

        if (value == "1") {
            selected = fragment1;
        } else if(value == "2") {
            selected = fragment2;
        }

        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_rigth).replace(R.id.container, selected).commit();
    }


    //액션바 등록
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hospital_next, menu) ;

        return true ;
    }


    //액션바 클릭 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                //                Intent intent = new Intent(ProductOrderActivity.this,HotelDetailActivity.class);
                //                startActivity(intent);
                return true;
            case R.id.next:

                ArrayList<String> canserlist = new ArrayList<>();
                canserlist = Hos_or_fragment1.CanserLIst();
                String detailcanser = null;
                String orderTime = null;

                detailcanser = Hos_or_fragment1.editCanser();
                orderTime = TimesetAdapter.Timeset();

                if (canserlist.isEmpty()) {
                    Toast.makeText(this, "병명을 최소 하나이상 선택해 주세요", Toast.LENGTH_SHORT).show();
                } else if (orderTime == null) {
                    Toast.makeText(this, "예약날짜를 최소 하나를 선택해 주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(PetHospitalCheckReservationInfoActivity.this, PetHospitalShowReservationInfoActivity.class);
                    intent.putStringArrayListExtra("canser", canserlist);
                    intent.putExtra("orderTime", orderTime);
                    intent.putExtra("petcode", petcode);
                    intent.putExtra("date", dateday);
                    intent.putExtra("etp_nm", topName);
                    if(detailcanser != null){
                        intent.putExtra("edit",detailcanser);
                    }
                    startActivity(intent);

                }



                return true;

            default:
                return super.onOptionsItemSelected(item);

        }



    }



}
