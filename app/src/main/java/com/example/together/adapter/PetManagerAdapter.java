package com.example.together.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.together.activities.PostActivity;
import com.example.together.activities.chat.PetManagerActivity;
import com.example.together.activities.my_petInfo.MyPetInfoCheckActivity;
import com.example.together.activities.petHospital.PetHospitalPetConditionActivity;
import com.example.together.activities.petching.PetchingSelectPetActivity;
import com.example.together.fragment.PetBunYangInfoEditFragment;
import com.example.together.fragment.PetFriendsInfoEditFragment;
import com.example.together.model.Pet;
import com.example.together.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PetManagerAdapter extends RecyclerView.Adapter<PetManagerAdapter.ViewHolder>  {

    private static final String TAG = "PetHospitalizationSelec";

    public int row_index = -1;
    private Context mContext;
    private List<Pet> mPet;
    public boolean selectedPet;



    private ItemClick itemClick;


    public interface ItemClick {
        public void onClick(View view,int position);
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }



    public PetManagerAdapter(Context mContext, List<Pet> mPet){
        this.mContext = mContext;
        this.mPet = mPet;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d(TAG, "onCreateViewHolder: ");
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_mypet_hospitalization, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        final Pet pet = mPet.get(position);

        viewHolder.petname.setText(mPet.get(position).getPetname());


        Glide.with(mContext)
                .load(pet.getPetimageurl())
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .into(viewHolder.petimage);



        viewHolder.pet_cardview_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position;
                notifyDataSetChanged();
                String selected_my_pet = mPet.get(position).getPetid();

                Intent intent = new Intent(mContext, MyPetInfoCheckActivity.class);
                intent.putExtra("petUid",selected_my_pet);
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mPet.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CircleImageView petimage;
        TextView petname;
        CardView pet_cardview_id;



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
