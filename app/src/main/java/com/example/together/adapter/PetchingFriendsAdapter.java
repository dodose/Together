package com.example.together.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.together.R;
import com.example.together.activities.petching.PetchingFriendDetailInfo;
import com.example.together.common.Common;
import com.example.together.model.PetchingFriend;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PetchingFriendsAdapter extends RecyclerView.Adapter<PetchingFriendsAdapter.MyViewHolder> {


    private static final String TAG = "PetchingFriendsAdapter";

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    Context mContext;
    List<PetchingFriend> mPetchingFriends;


    public PetchingFriendsAdapter(Context mContext, List<PetchingFriend> mPetchingFriends) {
        this.mContext = mContext;
        this.mPetchingFriends = mPetchingFriends;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_petching_list, parent, false);
        return new PetchingFriendsAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position)
    {
        Common common = new Common();

        final PetchingFriend petchingFriends = mPetchingFriends.get(position);
        petchingFriends.getPetFriendId();



        getPetInfo(mPetchingFriends.get(position).getPetFriendId(), viewHolder.img_pet, viewHolder.petName, viewHolder.petAge, viewHolder.petBreed, viewHolder.gender_m, viewHolder.gender_w);

        viewHolder.bunyangpet_detail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), PetchingFriendDetailInfo.class);

                // 디테일 정보 전달....

                intent.putExtra("petId",mPetchingFriends.get(position).getPetFriendId());


                mContext.startActivity(intent);

                // start the activity

            }
        });


    }


    @Override
    public int getItemCount()
    {
        return mPetchingFriends.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        public TextView petName, petBreed, petAge;
        public ImageView gender_m, gender_w, bunyangpet_detail, img_pet;

        public MyViewHolder(View itemView)
        {
            super(itemView);

            //펫 프로필
            petName = itemView.findViewById(R.id.petName);
            petBreed = itemView.findViewById(R.id.petBreed);
            petAge = itemView.findViewById(R.id.petAge);
            img_pet = itemView.findViewById(R.id.img_pet);

            //이미지들
            img_pet = itemView.findViewById(R.id.img_pet);
            gender_m = itemView.findViewById(R.id.gender_m);
            gender_w = itemView.findViewById(R.id.gender_w);

            //상세보기 넘어가는 icon
            //추후 아이디값 이름 변경하기...!!!!!!!
            bunyangpet_detail = itemView.findViewById(R.id.bunyangpet_detail);


        }
    }





    // 펫 정보 이름, 나이, 견종, 성별,
    private void getPetInfo(String petFriendid, final ImageView imageView, final TextView petAge, final TextView petName, final TextView petBreed, final ImageView gender_m, final ImageView gender_w)
    {

        Log.d(TAG, "펫분양: "+petFriendid);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Log.d(TAG, "getPetInfo: 키사랑"+petFriendid);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("PetchingFriend").child(petFriendid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PetchingFriend petchingFriend = dataSnapshot.getValue(PetchingFriend.class);

                Log.d(TAG, "onDataChange: ##친구이름"+petchingFriend.getPetName());
                Log.d(TAG, "onDataChange: ##친구이미지"+petchingFriend.getPetImg());
                Log.d(TAG, "onDataChange: ##친구나이"+petchingFriend.getAge());

                Glide.with(mContext).load(petchingFriend.getPetImg()).into(imageView);
                Log.d(TAG, "onDataChange: ##"+petFriendid);


                petName.setText(petchingFriend.getPetName());
                petBreed.setText(petchingFriend.getPetBreed());
                petAge.setText(petchingFriend.getAge()+"살");

                //성별표시
                if (petchingFriend.getPetGender().equals("Female"))
                {
                    gender_w.setVisibility(View.VISIBLE);
                }else
                {
                    gender_m.setVisibility(View.VISIBLE);
                }
//

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
