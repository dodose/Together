package com.example.together.Adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Aws on 28/01/2018.
 */

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.MyViewHolder> {
    FirebaseUser firebaseUser;
    private Context mContext;
    private List<Pet> mData;


    public PetAdapter(List<Pet> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public PetAdapter(MyPetListActivity myPetListActivity, List<Pet> lsPet) {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_mydoglist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final Pet pet = mData.get(position);


        holder.petname.setText(mData.get(position).getPetname());
        Glide.with(mContext).load(pet.getPetimage()).into(holder.petimage);
        holder.pet_cardview_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MyPetListActivity.class);

                // passing data to the book activity
                intent.putExtra("petname", mData.get(position).getPetname());
                intent.putExtra("intro", mData.get(position).getIntro());
                intent.putExtra("Thumbnail", mData.get(position).getPetimage());
                // start the activity
                mContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

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

