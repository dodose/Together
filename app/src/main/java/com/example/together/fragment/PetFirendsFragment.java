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
import com.example.together.model.PetchingBunyang;
import com.example.together.model.PetchingFriend;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PetFirendsFragment extends Fragment {

    private static final String TAG = "PetFirendsFragment";

    RecyclerView recyclerView;
    PetchingFriendsAdapter petchingFriendsAdapter;
    List<PetchingFriend> petFriendsList;

    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

         View view =  inflater.inflate(R.layout.fragment_pet_firends, container, false);

         recyclerView = view.findViewById(R.id.recycler_view);
         recyclerView.setHasFixedSize(true);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("PetchingFriend");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                petFriendsList.clear();

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren())
                {
                    PetchingFriend petchingFriend = childSnapshot.getValue(PetchingFriend.class);
                    String key = childSnapshot.getKey();
                    Log.d(TAG, "키키맨?"+key);

                    // as per Franks comment.
                    petchingFriend.setPetFriendId(key);


                    petFriendsList.add(petchingFriend);
                    Log.d(TAG, "리스트키: "+petFriendsList);

                    Log.d(TAG, "키키: "+petchingFriend);

                }

                Log.d(TAG, " "+petFriendsList);

                Collections.reverse(petFriendsList);
                petchingFriendsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        petFriendsList = new ArrayList<>();
        petchingFriendsAdapter = new PetchingFriendsAdapter(getContext(), petFriendsList);
        recyclerView.setAdapter(petchingFriendsAdapter);



        return view;


    }

}
