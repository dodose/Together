package com.example.together.activities.petching;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.FocusFinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.together.R;
import com.example.together.activities.HomeActivity;
import com.example.together.activities.petHospital.PetHospitalPetConditionActivity;
import com.example.together.model.PetchingFriend;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class PetchingFriendDetailInfo extends AppCompatActivity {

    private static final String TAG = "PetchingFriendDetailInf";

    private TextView petName, petBreed, petAge, petFriendIntro;
    private ImageView petFriendImage, gender_m, gender_w;
    private Button friend_request;
    public String owner, petFriendId, petcode;
    private Toolbar toolbar;
    DatabaseReference reference;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        Intent intent = getIntent();
        petFriendId = intent.getStringExtra("petFriendId");
        Log.d(TAG, "통통"+petFriendId);
        petcode = intent.getStringExtra("petcode");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petching_friend_detail_info);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_back);
        getSupportActionBar().setTitle("");


        petName = findViewById(R.id.petName);
        petBreed = findViewById(R.id.petBreed);
        petAge = findViewById(R.id.petAge);
        petFriendIntro = findViewById(R.id.petFriendIntro);

        petFriendImage = findViewById(R.id.petFriendImage);
        gender_m = findViewById(R.id.gender_m);
        gender_w = findViewById(R.id.gender_w);

        friend_request = findViewById(R.id.friend_request);

        Intent intent2 = getIntent();
        String petId = intent2.getStringExtra("petId");


        reference = FirebaseDatabase.getInstance().getReference("PetchingFriend").child(petFriendId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PetchingFriend petchingFriend = dataSnapshot.getValue(PetchingFriend.class);
                petName.setText(petchingFriend.getPetName());
                petAge.setText(petchingFriend.getAge()+"살");
                petBreed.setText(petchingFriend.getPetBreed());
                Picasso.get().load(petchingFriend.getPetImg()).fit().into(petFriendImage);

                owner = petchingFriend.getOwner();
                Log.d("주인", "onDataChange: "+owner);


                if (petchingFriend.getPetGender().equals("Female"))
                {
                    gender_w.setVisibility(View.VISIBLE);
                }else
                {
                    gender_m.setVisibility(View.VISIBLE);
                }

                petFriendIntro.setText(petchingFriend.getIntro_dog());



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //친구신청 버튼
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        friend_request.setOnClickListener(v -> {
            Log.d("주인", "주인은: "+owner);
            Log.d("프랜드", "프랜드: "+petFriendId);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Lounge").child("PetchingFriend").child(owner).child("PetId").child(petFriendId).child("Requestor");

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put(firebaseUser.getUid(),true);

            reference.updateChildren(hashMap);


            Intent lounge = new Intent(PetchingFriendDetailInfo.this, PetchingActivity.class);
            startActivity(lounge);

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
                Intent intent = new Intent(PetchingFriendDetailInfo.this, PetchingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
