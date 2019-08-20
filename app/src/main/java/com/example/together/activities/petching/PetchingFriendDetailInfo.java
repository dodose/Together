package com.example.together.activities.petching;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.together.R;
import com.example.together.model.PetchingFriend;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class PetchingFriendDetailInfo extends AppCompatActivity {

    private TextView petName, petBreed, petAge, petFriendIntro;
    private ImageView petFriendImage, gender_m, gender_w;
    private Button friend_request;
    public String owner, petFriendId, petcode;

    DatabaseReference reference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        Intent intent = getIntent();
        petFriendId = intent.getStringExtra("petFriendId");
        petcode = intent.getStringExtra("petcode");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petching_friend_detail_info);

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


        reference = FirebaseDatabase.getInstance().getReference("PetchingFriend").child(petId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PetchingFriend petchingFriend = dataSnapshot.getValue(PetchingFriend.class);
                petName.setText(petchingFriend.getPetName());
                petAge.setText(petchingFriend.getAge()+"살");
                petBreed.setText(petchingFriend.getPetBreed());
                Picasso.get().load(petchingFriend.getPetImg()).fit().into(petFriendImage);

                owner = petchingFriend.getOwner();


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


        friend_request.setOnClickListener(v -> {
            Log.d("주인", "주인은: "+owner);
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Lounge").child("PetchingFriend").child(owner).child("PetId").child(petFriendId).child("Requestor");

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put(firebaseUser.getUid(),true);

            reference.setValue(hashMap);


            Intent lounge = new Intent(PetchingFriendDetailInfo.this, PetchingActivity.class);
            startActivity(lounge);

        });


    }

}
