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
import android.widget.LinearLayout;

import com.example.together.R;
import com.example.together.activities.petching.PetchingLoungeDetailInfoActivity;
import com.example.together.adapter.PetchingLoungeAdapter;
import com.example.together.adapter.PetchingLoungeFriendAdapter;
import com.example.together.model.Pet;
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

    RecyclerView recyclerView, second_recycler_view;
    PetchingLoungeAdapter petchingLoungeAdapter;
    PetchingLoungeFriendAdapter petchingLoungeFriendAdapter;


    List<User> userList;
    List<User> userList2;
    FirebaseUser firebaseUser;
    List<String> idList = new ArrayList<>();
    List<String> idFriendList = new ArrayList<>();
    public String petKey;
    public LinearLayout layout_for_adjust;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {


        View view =  inflater.inflate(R.layout.fragment_petching_lounge, container, false);

        layout_for_adjust = view.findViewById(R.id.layout_for_adjust);

        recyclerView = view.findViewById(R.id.recycler_view);
        second_recycler_view = view.findViewById(R.id.second_recycler_view);
        recyclerView.setHasFixedSize(true);
        second_recycler_view.setHasFixedSize(true);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Lounge").child("PetchingBunyang").child(firebaseUser.getUid()).child("PetId");

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Lounge").child("PetchingFriend").child(firebaseUser.getUid()).child("PetId");


        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        idList.clear();
                        String id = ds.getKey();
                        petchingLoungeAdapter.setId(id);


                        DatabaseReference reference1 =
                                FirebaseDatabase.getInstance().getReference( "Lounge").child("PetchingBunyang").child(firebaseUser.getUid()).child("PetId").child(id).child("Requestor");
                        reference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                                {
                                    if (snapshot.getKey()!=null)
                                    {
                                        layout_for_adjust.setVisibility(View.GONE);
                                    }
                                    idList.add(snapshot.getKey());

                                }

                                showUsers();

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




        // 두번째 recycler_view
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    idFriendList.clear();
                    String id = ds.getKey();
                    petchingLoungeFriendAdapter.setId(id);


                    DatabaseReference reference3 =
                            FirebaseDatabase.getInstance().getReference( "Lounge").child("PetchingFriend").child(firebaseUser.getUid()).child("PetId").child(id).child("Requestor");
                    reference3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot snapshot : dataSnapshot.getChildren())
                            {

                                idFriendList.add(snapshot.getKey());

                            }

                            showUsers2();

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




        Log.d(TAG, "하마: "+petKey);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        userList = new ArrayList<>();
        petchingLoungeAdapter = new PetchingLoungeAdapter(getContext(), userList);
        recyclerView.setAdapter(petchingLoungeAdapter);




        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        second_recycler_view.setLayoutManager(linearLayoutManager2);
        userList2 = new ArrayList<>();
        petchingLoungeFriendAdapter = new PetchingLoungeFriendAdapter(getContext(), userList2);
        second_recycler_view.setAdapter(petchingLoungeFriendAdapter);



        return view;

    }




    private void showUsers(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    for (String id : idList){
                        if (user.getId().equals(id)){
                            userList.add(user);
                        }
                    }
                }
                Collections.reverse(userList);
                petchingLoungeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void showUsers2(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList2.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    for (String id : idFriendList){
                        if (user.getId().equals(id)){
                            userList2.add(user);
                        }
                    }
                }
                Collections.reverse(userList2);
                petchingLoungeFriendAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
