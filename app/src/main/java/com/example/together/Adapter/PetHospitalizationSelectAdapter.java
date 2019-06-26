package com.example.together.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: ");
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Glide.with(mContext)
                .asBitmap()
                .load(mPet.get(position))
                .into(viewHolder.mypet_image);

        //viewHolder.petName_Hospitalization.setText(mPet.get(position));

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView mypet_image;
        TextView petName_Hospitalization;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mypet_image = itemView.findViewById(R.id.mypet_image);
            petName_Hospitalization = itemView.findViewById(R.id.petName_Hospitalization);
        }
    }

}
