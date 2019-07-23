package com.example.together.activities.petching;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class PetchingFriendDetailInfo extends AppCompatActivity {

    private TextView petName, petBreed, petAge, petFriendIntro;
    private ImageView petFriendImage, gender_m, gender_w;
    private Button friend_request;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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

        Intent intent = getIntent();
        String petId = intent.getStringExtra("petId");


        reference = FirebaseDatabase.getInstance().getReference("PetchingFriend").child(petId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PetchingFriend petchingFriend = dataSnapshot.getValue(PetchingFriend.class);
                petName.setText(petchingFriend.getPetName());
                petAge.setText(petchingFriend.getAge()+"살");
                petBreed.setText(petchingFriend.getPetBreed());
                Picasso.get().load(petchingFriend.getPetImg()).fit().into(petFriendImage);


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



        });


    }

}
