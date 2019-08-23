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

import java.util.ArrayList;
import java.util.List;

public class MyPetchingCondition extends AppCompatActivity {

    private static final String TAG = "MyPetchingCondition";
    
    DatabaseReference reference, reference2, reference3, reference4;
    FirebaseUser firebaseUser;
    public String petUid;
    String petbungyangid;
    PetchingConditionAdapter petchingConditionAdapter;
    PetchingBunyang petchingBunyang;

    List<User> lsUser;
    List<String> idList;
    RecyclerView recyclerview_requester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_petching_condition);

        recyclerview_requester = findViewById(R.id.recyclerview_requester);

        Intent intent = getIntent();
        petUid = intent.getExtras().getString("petUid");

        idList = new ArrayList<>();
        lsUser = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("PetchingBunyang");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren())
                {
                    String key = childSnapshot.getKey();
                    Log.d(TAG, "젠틀맨 "+key);

                    reference2 = FirebaseDatabase.getInstance().getReference("PetchingBunyang").child(key);
                    reference2.addValueEventListener(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                            PetchingBunyang petchingBunyang = dataSnapshot2.getValue(PetchingBunyang.class);

                            Log.d(TAG, "아아아아"+petchingBunyang.getPetcode());
                            Log.d(TAG, "Twice"+petchingBunyang.getPetbunyangid());
                            Log.d(TAG, "사자"+petUid);
                            if (petUid.equals(petchingBunyang.getPetcode()))
                            {
                                Log.d(TAG, "빡빡"+dataSnapshot2.child("petBunyangId").getValue());

                                petbungyangid = petchingBunyang.getPetbunyangid();

                                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                                reference3 = FirebaseDatabase.getInstance().getReference("Lounge").child("PetchingBunyang").child(firebaseUser.getUid()).child("PetId").child(petbungyangid).child("Requestor");
                                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot3)
                                    {
                                        idList.clear();
                                        for (DataSnapshot childSnapshot2 : dataSnapshot3.getChildren())
                                        {
                                            idList.add(childSnapshot2.getKey());
                                            Log.d(TAG, "신청자아이디키"+idList);
                                            Log.d(TAG, "스냅키"+childSnapshot2.getKey());

                                            reference4 = FirebaseDatabase.getInstance().getReference("Users").child(childSnapshot2.getKey());
                                            reference4.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot4) {
                                                    User user = dataSnapshot4.getValue(User.class);
                                                    lsUser.add(user);
                                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyPetchingCondition.this, LinearLayoutManager.VERTICAL, false);
                                                    recyclerview_requester.setLayoutManager(linearLayoutManager);
                                                    petchingConditionAdapter = new PetchingConditionAdapter(MyPetchingCondition.this, lsUser, petUid, petbungyangid);
                                                    recyclerview_requester.setAdapter(petchingConditionAdapter);
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
