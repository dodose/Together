package com.example.together.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.together.Activities.PetHospital.PetHospitalPetConditionActivity;
import com.example.together.Model.Pet;
import com.example.together.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PetHospitalizationSelectAdapter extends RecyclerView.Adapter<PetHospitalizationSelectAdapter.ViewHolder>  {

    private static final String TAG = "PetHospitalizationSelec";


    private Context mContext;
    private List<Pet> mPet;

    private ItemClick itemClick;

    public interface ItemClick {
        public void onClick(View view,int position);
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }



    public PetHospitalizationSelectAdapter(Context mContext, List<Pet> mPet){
        this.mContext = mContext;
        this.mPet = mPet;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d(TAG, "onCreateViewHolder: ");

        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_mypet_hospitalization, parent, false));
    }



    @Override
    public void onBindViewHolder(ViewHolder viewHolder,  int position) {

        final Pet pet = mPet.get(position);

        viewHolder.petname.setText(mPet.get(position).getPetname());


        Glide.with(mContext)
                .load(pet.getPetimageurl())
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .into(viewHolder.petimage);

//        if (!mPet.get(position).)

    }

    @Override
    public int getItemCount() {
        return mPet.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CircleImageView petimage;
        TextView petname;
        CardView pet_cardview_id;

        //아이템 클릭시 실행 함수


        //아이템 클릭시 실행 함수 등록 함수



        public ViewHolder(final View itemView) {

            super(itemView);

            petimage = itemView.findViewById(R.id.petimage);
            petname = itemView.findViewById(R.id.petname);
            pet_cardview_id = itemView.findViewById(R.id.pet_cardview_id);



        }


        @Override
        public void onClick(View v) {

        }
    }

}
