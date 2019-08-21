package com.example.together.activities.my_petInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.together.R;
import com.example.together.adapter.PetchingConditionAdapter;
import com.example.together.model.PetchingBunyang;
import com.example.together.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MyPetchingCondition extends AppCompatActivity {

    private static final String TAG = "MyPetchingCondition";
    
    DatabaseReference reference, reference2, reference3, reference4;
    FirebaseUser firebaseUser;
    String petUid;
    PetchingConditionAdapter petchingConditionAdapter;

    List<User> lsUser;
    RecyclerView recyclerview_requester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_petching_condition);

        recyclerview_requester = findViewById(R.id.recyclerview_requester);

        Intent intent = getIntent();
        petUid = intent.getExtras().getString("petUid");

        reference = FirebaseDatabase.getInstance().getReference("PetchingBunyang");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren())
                {
                    String key = childSnapshot.getKey();
                    Log.d(TAG, "키키키 "+key);

                    reference2 = FirebaseDatabase.getInstance().getReference("PetchingBunyang").child(key);
                    reference2.addValueEventListener(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            PetchingBunyang petchingBunyang = dataSnapshot.getValue(PetchingBunyang.class);

                            Log.d(TAG, "Twice"+petchingBunyang.getPetcode());
                            Log.d(TAG, "사자"+petUid);
                            if (petUid.equals(petchingBunyang.getPetcode()))
                            {
                                Log.d(TAG, "사과"+petchingBunyang.getPetcode());

                                String banana = petchingBunyang.getPetcode();
                                Log.d(TAG, "바나나"+banana);

                                Log.d(TAG, "바보"+petUid.equals(petchingBunyang.getPetcode()));
                                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                                reference3 = FirebaseDatabase.getInstance().getReference("Lounge").child("PetchingBunyang").child(firebaseUser.getUid()).child("PetId").child(banana).child("Requestor");
                                Log.d(TAG, "강아지");
                                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot2)
                                    {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                                        {
                                            User user = snapshot.getValue(User.class);
                                            Log.d(TAG, "신청자아이디키"+user.getId());
                                            String requesterUserKey = user.getId();
                                            reference4 = FirebaseDatabase.getInstance().getReference("Users").child(requesterUserKey);
                                            reference4.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    User user = dataSnapshot.getValue(User.class);
                                                    user.getImageurl();
                                                    user.getUsername();
                                                    Log.d(TAG, "유저이름"+user.getUsername());
                                                    Log.d(TAG, "유저이미지"+user.getImageurl());
                                                    lsUser.add(user);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                                            recyclerview_requester.setLayoutManager(linearLayoutManager);
                                            petchingConditionAdapter = new PetchingConditionAdapter(MyPetchingCondition.this, lsUser);
                                        }

                                    }




                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
