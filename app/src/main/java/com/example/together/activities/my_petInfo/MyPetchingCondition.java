package com.example.together.activities.my_petInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.together.R;
import com.example.together.model.PetchingBunyang;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyPetchingCondition extends AppCompatActivity {

    private static final String TAG = "MyPetchingCondition";
    
    DatabaseReference reference, reference2;
    FirebaseUser firebaseUser;
    String petUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_petching_condition);

        Intent intent = getIntent();
        petUid = intent.getExtras().getString("petUid");

        reference = FirebaseDatabase.getInstance().getReference("PetchingBunyang");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren())
                {
                    String key = childSnapshot.getKey();

                    reference2 = FirebaseDatabase.getInstance().getReference("PetchingBunyang").child(key);
                    reference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            PetchingBunyang petchingBunyang = dataSnapshot.getValue(PetchingBunyang.class);
                            if (petUid.equals(petchingBunyang.getPetcode()))
                            {
                                Log.d(TAG, "분양아이디"+petchingBunyang.getPetBunyangId());
                                petchingBunyang.getPetBunyangId();
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
