package com.example.together.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.together.R;
import com.example.together.activities.HomeActivity;
import com.example.together.activities.chat.ChatsActivity;
import com.example.together.activities.my_petInfo.MyPetListActivity;
import com.example.together.activities.my_petInfo.MyPetchingCondition;
import com.example.together.model.Pet;
import com.example.together.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PetchingConditionAdapter extends RecyclerView.Adapter<PetchingConditionAdapter.MyViewHolder>
{



    private static final String TAG = "PetchingConditionAdapte";

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    Context mContext;
    List<User> mUser;
    String mPetUid, mPetbungyangid;


    public PetchingConditionAdapter(Context mContext, List<User> mUser, String petUid, String petbungyangid)
    {
        Log.d(TAG, "국어");
        this.mContext = mContext;
        this.mUser = mUser;
        this.mPetUid = petUid;
        this.mPetbungyangid = petbungyangid;
    }


    @NonNull
    @Override
    public PetchingConditionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PetchingConditionAdapter.MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_petching_condition, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PetchingConditionAdapter.MyViewHolder holder, final int position)
    {

        Log.d(TAG, "악어");

        final User user = mUser.get(position);

        Glide.with(mContext)
                .load(user.getImageurl())
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .into(holder.image_profile);

        holder.username.setText(mUser.get(position).getUsername()+"님에게 인계");

        holder.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mUser.get(position).getId() -> 리퀘스터 아이디

                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Pets").child(firebaseUser.getUid()).child(mPetUid);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Pet pet = dataSnapshot.getValue(Pet.class);

                        Log.d(TAG, "멍멍"+pet.getBirthday());

                            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Pets").child(mUser.get(position).getId()).child(mPetUid);

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("birthday", pet.getBirthday());
                            hashMap.put("gender", pet.getGender());
                            hashMap.put("petbreed", pet.getPetbreed());
                            hashMap.put("petid", mPetUid);
                            hashMap.put("petimageurl", pet.getPetimageurl());
                            hashMap.put("petname",pet.getPetname());
                            hashMap.put("petching_status","no");
                            hashMap.put("petweight",pet.getPetweight());

                            reference2.setValue(hashMap);

                            if (hashMap!= null)
                            {
                                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                FirebaseDatabase.getInstance().getReference("Pets").child(firebaseUser.getUid()).child(mPetUid).removeValue();
                                FirebaseDatabase.getInstance().getReference("Lounge").child("PetchingBunyang").child(firebaseUser.getUid()).child("PetId")
                                        .child(mPetbungyangid).child("Requestor").child(mUser.get(position).getId()).removeValue();
                                FirebaseDatabase.getInstance().getReference("PetchingBunyang").child(mPetbungyangid).removeValue();

                            }

                            Intent intent = new Intent(v.getContext(), HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            v.getContext().startActivity(intent);



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        holder.refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Pets").child(firebaseUser.getUid()).child(mPetUid);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Pet pet = dataSnapshot.getValue(Pet.class);
                        FirebaseDatabase.getInstance().getReference("Lounge").child("PetchingBunyang").child(firebaseUser.getUid()).child("PetId")
                                .child(mPetbungyangid).child("Requestor").child(pet.getPetid()).removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return mUser.size() ;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        CircleImageView image_profile;
        TextView username;
        Button ok, refuse;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            image_profile = itemView.findViewById(R.id.image_profile);
            username = itemView.findViewById(R.id.username);
            ok = itemView.findViewById(R.id.ok);
            refuse = itemView.findViewById(R.id.refuse);


        }
    }
}
