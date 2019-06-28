package com.example.together.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.together.Model.Pet;
import com.example.together.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PetHospitalizationSelectAdapter extends RecyclerView.Adapter<PetHospitalizationSelectAdapter.ViewHolder>  {

    private static final String TAG = "PetHospitalizationSelec";

    private ArrayList<Pet> mPet = new ArrayList<>();
    private Context mContext;


    public PetHospitalizationSelectAdapter(Context mContext, ArrayList<Pet> mPet){
        this.mContext = mContext;
        this.mPet = mPet;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");

        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_mypet_hospitalization, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        final Pet pet = mPet.get(position);

        viewHolder.petname.setText(mPet.get(position).getPetname());


        Glide.with(mContext)
                .load(pet.getPetimageurl())
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .into(viewHolder.petimage);

        //viewHolder.petName_Hospitalization.setText(mPet.get(position));

    }

    @Override
    public int getItemCount() {
        return mPet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView petimage;
        TextView petname;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            petimage = itemView.findViewById(R.id.petimage);
            petname = itemView.findViewById(R.id.petname);
        }
    }

}
