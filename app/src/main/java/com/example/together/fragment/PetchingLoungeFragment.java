package com.example.together.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.together.R;
import com.example.together.adapter.PetchingBunyangAdapter;
import com.example.together.adapter.PetchingFriendsAdapter;
import com.example.together.adapter.PetchingLoungeAdapter;
import com.example.together.model.Lounge;
import com.example.together.model.PetchingBunyang;
import com.example.together.model.PetchingFriend;
import com.example.together.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PetchingLoungeFragment extends Fragment {

    private static final String TAG = "PetFirendsFragment";

    RecyclerView recyclerView;
    PetchingLoungeAdapter petchingLoungeAdapter;
    List<User> userList;
    FirebaseUser firebaseUser;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {


        View view =  inflater.inflate(R.layout.fragment_petching_lounge, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);


//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        Log.d(TAG, "주인"+firebaseUser.getUid());
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Lounge").child("PetchingBunyang").child(firebaseUser.getUid()).child(????).child("Requestor");
//        reference.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot childSnapshot : dataSnapshot.getChildren())
//                {
//                    User user = childSnapshot.getValue(User.class);
//                    String key = childSnapshot.getKey();
//
//                    user.setId(key);
//
//                    userList.add(user);
//
//                }
//
//                Collections.reverse(userList);
//                petchingLoungeAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Lounge").child("PetchingBunyang").child(firebaseUser.getUid()).child("PetId");
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("PetchingBunyang");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datas : dataSnapshot.getChildren())
                    for (DataSnapshot ds : datas.getChildren())
                    {
                        String id = ds.getKey();
                        Log.d(TAG, "안재욱: "+id);

                        reference1.child(id).child("Requestor").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                                {
                                    Log.d(TAG, "키리스트: " + dataSnapshot1);
                                    User user = dataSnapshot1.getValue(User.class);
                                    String key = dataSnapshot1.getKey();

                                    user.setId(key);

                                    userList.add(user);
                                }

                                    Collections.reverse(userList);
                                    petchingLoungeAdapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "펫키에러");
            }
        });



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        userList = new ArrayList<>();
        petchingLoungeAdapter = new PetchingLoungeAdapter(getContext(), userList);
        recyclerView.setAdapter(petchingLoungeAdapter);



        return view;


    }



//    public void getPetKeyWhatChosen()
//    {
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Lounge").child("PetchingBunyang").child(firebaseUser.getUid()).child("PetId");
//        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("PetchingBunyang");
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot datas : dataSnapshot.getChildren())
//                {
//                    for (DataSnapshot ds : datas.getChildren())
//                    {
//                        String id = ds.getKey();
//                        Log.d(TAG, "안재욱: "+id);
//
//                        reference1.child(id).child("Requestor").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren())
//                                    Log.d(TAG, "키리스트: "+ dataSnapshot1);
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.d(TAG, "펫키에러");
//            }
//        });
//
//
//    }


}
