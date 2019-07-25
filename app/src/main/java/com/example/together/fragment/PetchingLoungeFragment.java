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

import com.example.together.R;
import com.example.together.adapter.PetchingBunyangAdapter;
import com.example.together.adapter.PetchingFriendsAdapter;
import com.example.together.adapter.PetchingLoungeAdapter;
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

        View view =  inflater.inflate(R.layout.fragment_pet_firends, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);



        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("PetchingBunyang").child(firebaseUser.getUid()).child("Requestor");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren())
                {
                    User user = childSnapshot.getValue(User.class);
                    String key = childSnapshot.getKey();

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        userList = new ArrayList<>();
        petchingLoungeAdapter = new PetchingLoungeAdapter(getContext(), userList);
        recyclerView.setAdapter(petchingLoungeAdapter);



        return view;


    }

}
