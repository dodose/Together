package com.example.together.Adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.together.Activities.MyPetInfo.MyPetListActivity;
import com.example.together.Model.Pet;
import com.example.together.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * Created by Aws on 28/01/2018.
 */

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.MyViewHolder> {

    private static final String TAG = "PetAdapter";

    private Context mContext;
    private List<Pet> mPet;


    public PetAdapter(Context mContext, List<Pet> mPet) {
        this.mContext = mContext;
        this.mPet = mPet;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_mydoglist, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Pet pet = mPet.get(position);


        holder.petname.setText(mPet.get(position).getPetname());
        // holder.petimage.setImageResource(mPet.get());

        Log.d(TAG, "펫이미지: "+pet.getPetimageurl());

        Glide.with(mContext)
                .load(pet.getPetimageurl())
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .into(holder.petimage);

        holder.pet_cardview_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MyPetListActivity.class);

                // passing data to the book activity
                intent.putExtra("petname", mPet.get(position).getPetname());
                intent.putExtra("intro", mPet.get(position).getIntro());
                intent.putExtra("Thumbnail", mPet.get(position).getPetimageurl());
                // start the activity
                mContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mPet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView petname;
        ImageView petimage;
        CardView pet_cardview_id;


        public MyViewHolder(View itemView) {
            super(itemView);

            petname = (TextView) itemView.findViewById(R.id.dog_name);
            petimage = (ImageView) itemView.findViewById(R.id.dog_img_id);
            pet_cardview_id = (CardView) itemView.findViewById(R.id.pet_cardview_id);


        }
    }


}
