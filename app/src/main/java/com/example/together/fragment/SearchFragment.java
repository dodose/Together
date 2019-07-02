package com.example.together.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.together.activities.goodbyePet.GoodbyeSelectActivity;
import com.example.together.activities.petGroup.PetGroupActivity;
import com.example.together.activities.petHospital.PetHospitalPetConditionActivity;
import com.example.together.activities.petHotel.PetHotelActivity;
import com.example.together.activities.petching.PetchingActivity;
import com.example.together.adapter.UserAdapter;
import com.example.together.model.User;
import com.example.together.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUsers;
    private TextView close;

    //버튼들 감추거나 보이기 표시를 위함
    private RelativeLayout selects;

    // 버튼 아이디들
    private ImageView petHotel, petching, petHospital, petGroup, goodbyePet;



    EditText search_bar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        close = view.findViewById(R.id.close);
        search_bar = view.findViewById(R.id.search_bar);
        selects = view.findViewById(R.id.selects);

        mUsers = new ArrayList<>();
        userAdapter = new UserAdapter(getContext(), mUsers, true);
        recyclerView.setAdapter(userAdapter);

        //imageView 들 각각의 아이디
        petHotel = view.findViewById(R.id.petHotel);
        petHospital = view.findViewById(R.id.petHospital);
        petching = view.findViewById(R.id.petching);
        petGroup = view.findViewById(R.id.petGroup);
        goodbyePet = view.findViewById(R.id.goodbyePet);


        // 시간되면 코드 더 짧게 줄이기..
        //petHotel 클릭시, activity 전환
        petHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getActivity(), PetHotelActivity.class));

            }
        });

        //petHospital activity로 전환

        petHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PetHospitalPetConditionActivity.class));

            }
        });

        //petching activity로 전환

        petching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PetchingActivity.class));

            }
        });

        //petGroup activity로 전환

        petGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PetGroupActivity.class));

            }
        });

        //goodbyePet activity로 전환

        goodbyePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GoodbyeSelectActivity.class));

            }
        });


        //search bar 클릭 전후의 변경된 화면 표시

        search_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selects.getVisibility()==view.VISIBLE)
                    selects.setVisibility(view.GONE);

                if (recyclerView.getVisibility()==view.GONE)
                    recyclerView.setVisibility(view.VISIBLE);
                readUsers();
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selects.getVisibility()==view.GONE)
                    selects.setVisibility(view.VISIBLE);

                 if(recyclerView.getVisibility()==view.VISIBLE)
                    recyclerView.setVisibility(view.GONE);
            }
        });




        search_bar.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUsers(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        return view;
    }


    private void searchUsers(String s){
        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("username")
                .startAt(s)
                .endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    mUsers.add(user);
                }

                userAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {



            }
        });
    }


    private void readUsers(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(search_bar.getText().toString().equals("")){
                    mUsers.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        User user = snapshot.getValue(User.class);
                        mUsers.add(user);
                    }

                    userAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }

}
