package com.example.together.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.together.R;
import com.example.together.adapter.PetchingBunyangAdapter;
import com.example.together.model.Pet;
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

    private RecyclerView recyclerView;
    private PetchingBunyangAdapter petchingBunyangAdapter;
    private List<Pet> petBunyangList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view =  inflater.inflate(R.layout.fragment_pet_bunyang, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        petBunyangList = new ArrayList<>();
        petchingBunyangAdapter = new PetchingBunyangAdapter(getContext(), petBunyangList);
        recyclerView.setAdapter(petchingBunyangAdapter);

        readPetBunyangList();

        return view;
    }

    private void readPetBunyangList()
    {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("PetchingBunyang").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                petBunyangList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Pet pet = snapshot.getValue(Pet.class);
                    petBunyangList.add(pet);
                }

                // 이 코드 다시한번 살피기
                Collections.reverse(petBunyangList);
                petchingBunyangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}