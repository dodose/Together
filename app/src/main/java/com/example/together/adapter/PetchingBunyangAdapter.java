package com.example.together.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.R;
import com.example.together.activities.petching.PetchingBunyangDetailInfo;
import com.example.together.common.Common;
import com.example.together.model.Pet;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PetchingBunyangAdapter extends RecyclerView.Adapter<PetchingBunyangAdapter.MyViewHolder> {


    FirebaseUser firebaseUser;
    DatabaseReference reference;

    Context mContext;
    List<Pet> mPet;

    public PetchingBunyangAdapter(Context mContext, List<Pet> mPet) {
        this.mContext = mContext;
        this.mPet = mPet;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_petching_bunyang_pet_list, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position)
    {
        Common common = new Common();

        final Pet pet = mPet.get(position);

        viewHolder.petName.setText(mPet.get(position).getPetname());
        viewHolder.petBreed.setText(mPet.get(position).getPetbreed());

        //만나이 계산 메소드
        viewHolder.petAge.setText(common.ageCalculator(mPet.get(position).getBirthday()));
        Picasso.get().load(pet.getPetimageurl()).into(viewHolder.img_pet);

        //성별표시
        if (mPet.get(position).getGender().equals("Female"))
        {
            viewHolder.gender_w.setVisibility(View.VISIBLE);
        }else
        {
            viewHolder.gender_m.setVisibility(View.VISIBLE);
        }



        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, PetchingBunyangDetailInfo.class);

                // 디테일 정보 전달....
                intent.putExtra("petname", mPet.get(position).getPetname());
                intent.putExtra("intro", mPet.get(position).getIntro());
                intent.putExtra("Thumbnail", mPet.get(position).getPetimageurl());
                // start the activity
                mContext.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount()
    {
        return mPet.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        private TextView petName, petBreed, petAge;
        private ImageView gender_m, gender_w, bunyangpet_detail, img_pet;

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
            bunyangpet_detail = itemView.findViewById(R.id.bunyangpet_detail);

        }
    }






    // 펫 정보 이름, 나이, 견종, 성별,
    private void getPetInfo(final ImageView imageView, TextView petName, TextView petBreed, TextView petAge )
    {

         DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Pets").child(firebaseUser.getUid());
         reference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });

    }

}
