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
import com.example.together.activities.petching.PetchingBunyangDetailInfo;
import com.example.together.common.Common;
import com.example.together.model.PetchingBunyang;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;

public class PetchingBunyangAdapter extends RecyclerView.Adapter<PetchingBunyangAdapter.MyViewHolder> {


    private static final String TAG = "PetchingBunyangAdapter";

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    Context mContext;
    List<PetchingBunyang> mPetchingBunyang;

    public PetchingBunyangAdapter(Context mContext, List<PetchingBunyang> mPetchingBunyang) {
        this.mContext = mContext;
        this.mPetchingBunyang = mPetchingBunyang;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_petching_list, parent, false);
        return new PetchingBunyangAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position)
    {
        Common common = new Common();

        final PetchingBunyang petchingBunyang = mPetchingBunyang.get(position);
        petchingBunyang.getPetBunyangId();



        getPetInfo(mPetchingBunyang.get(position).getPetBunyangId(), viewHolder.img_pet, viewHolder.petName, viewHolder.petAge, viewHolder.petBreed, viewHolder.gender_m, viewHolder.gender_w);

        viewHolder.img_pet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), PetchingBunyangDetailInfo.class);

                // 디테일 정보 전달....

                intent.putExtra("petBunyangId",mPetchingBunyang.get(position).getPetBunyangId());
                intent.putExtra("petcode",mPetchingBunyang.get(position).getPetcode());


                mContext.startActivity(intent);

                // start the activity

            }
        });


    }


    @Override
    public int getItemCount()
    {
        return mPetchingBunyang.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder
    {

         public TextView petName, petBreed, petAge;
         public ImageView gender_m, gender_w, img_pet;

        public MyViewHolder(View itemView)
        {
            super(itemView);

            //펫 프로필
            petName = itemView.findViewById(R.id.petName);
            petBreed = itemView.findViewById(R.id.petBreed);
            petAge = itemView.findViewById(R.id.petAge);
            img_pet = itemView.findViewById(R.id.img_pet);

            //이미지들
            gender_m = itemView.findViewById(R.id.gender_m);
            gender_w = itemView.findViewById(R.id.gender_w);



        }
    }





    // 펫 정보 이름, 나이, 견종, 성별,
    private void getPetInfo(String petbunyangid, final ImageView imageView, final TextView petName, final TextView petAge, final TextView petBreed, final ImageView gender_m, final ImageView gender_w)
    {


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("PetchingBunyang").child(petbunyangid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PetchingBunyang petchingBunyang = dataSnapshot.getValue(PetchingBunyang.class);

                Glide.with(mContext).load(petchingBunyang.getPetImg()).fitCenter().into(imageView);


                petName.setText(petchingBunyang.getPetName());
                petBreed.setText(petchingBunyang.getPetBreed());
                petAge.setText(petchingBunyang.getAge()+"살");


                //성별표시
                if (petchingBunyang.getPetGender().equals("Female"))
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