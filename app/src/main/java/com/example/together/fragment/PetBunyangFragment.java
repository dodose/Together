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
import com.example.together.model.PetchingBunyang;
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


public class PetBunyangFragment extends Fragment  {

    private static final String TAG = "PetBunyangFragment";

    RecyclerView recyclerView;
    PetchingBunyangAdapter petchingBunyangAdapter;
    List<PetchingBunyang> petBunyangList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view =  inflater.inflate(R.layout.fragment_pet_bunyang, container, false);


        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("PetchingBunyang");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                petBunyangList.clear();

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren())
                {
                    PetchingBunyang petchingBunyang = childSnapshot.getValue(PetchingBunyang.class);
                    String key = childSnapshot.getKey();
                    Log.d(TAG, "키키맨?"+key);

                    // as per Franks comment.
                    petchingBunyang.setPetBunyangId(key);

                    petBunyangList.add(petchingBunyang);
                    Log.d(TAG, "리스트키: "+petBunyangList);

                    Log.d(TAG, "키키: "+petchingBunyang);

                }



                Collections.reverse(petBunyangList);
                petchingBunyangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        petBunyangList = new ArrayList<>();
        petchingBunyangAdapter = new PetchingBunyangAdapter(getContext(), petBunyangList);
        recyclerView.setAdapter(petchingBunyangAdapter);


        return view;
    }


}