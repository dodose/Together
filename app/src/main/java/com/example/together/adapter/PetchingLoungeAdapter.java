package com.example.together.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.together.R;
import com.example.together.activities.petching.PetchingBunyangDetailInfo;
import com.example.together.activities.petching.PetchingLoungeDetailInfoActivity;
import com.example.together.fragment.PetchingLoungeFragment;
import com.example.together.model.Lounge;
import com.example.together.model.Pet;
import com.example.together.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class PetchingLoungeAdapter extends RecyclerView.Adapter<PetchingLoungeAdapter.MyViewHolder> {


    private static final String TAG = "PetchingBunyangAdapter";


    String id;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    Context mContext;
    List<User> mUser;


    public PetchingLoungeAdapter(Context mContext, List<User> mUser)
    {
        this.mContext = mContext;
        this.mUser = mUser;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        Log.d(TAG, "did u bring id key"+id);
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_petching_lounge, parent, false);
        return new PetchingLoungeAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position)
    {



        final User user = mUser.get(position);
        user.getId();

        Log.d(TAG, "빼오냥"+user.getId());

        getRequestorUserInfo(mUser.get(position).getId(), viewHolder.img_requestor , viewHolder.requestor_name);



        viewHolder.img_requestor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(v.getContext(), PetchingLoungeDetailInfoActivity.class);

                intent.putExtra("requester_name",mUser.get(position).getFullname());
                intent.putExtra("requester_img", mUser.get(position).getImageurl());
                intent.putExtra("requester_intro", mUser.get(position).getBio());
                // intent.putExtra("requester_pet_id",mUser.get(position).getUid)

                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount()
    {
        return mUser.size();
                //mPetchingLounge.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        public Button allow, refuse;
        public ImageView img_requestor;
        public TextView requestor_name;

        public MyViewHolder(View itemView)
        {
            super(itemView);

            img_requestor = itemView.findViewById(R.id.img_requestor);

            allow = itemView.findViewById(R.id.allow);
            refuse = itemView.findViewById(R.id.refuse);

            requestor_name = itemView.findViewById(R.id.requestor_name);

        }
    }



    // 펫 정보 이름, 나이, 견종, 성별,
    private void getRequestorUserInfo(String userid, final ImageView img_requestor, final TextView requestor_name)
    {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                Glide.with(mContext).load(user.getImageurl()).into(img_requestor);

                requestor_name.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    public void setId(String mId) {
        id = mId;
    }






}