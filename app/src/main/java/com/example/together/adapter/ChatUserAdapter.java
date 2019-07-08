package com.example.together.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.together.activities.chat.MessageActivity;
import com.example.together.model.User;
import com.example.together.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatUserAdapter extends RecyclerView.Adapter<ChatUserAdapter.ViewHolder> {

    private static final String TAG = "ChatUserAdapter";

    private Context mContext;
    private List<User> mUsers;
    private boolean ischat;


    public ChatUserAdapter (Context mContext, List<User> mUsers, boolean ischat){
        this.mContext = mContext;
        this.mUsers =  mUsers;
        this.ischat = ischat;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_user_item, parent, false);
        return new ChatUserAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final User user = mUsers.get(position);

        holder.username.setText(user.getUsername());
        if (user.getImageurl().equals("default")){
            holder.image_profile.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(mContext).load(user.getImageurl()).into(holder.image_profile);
        }


        Log.d(TAG , "나오나?"+ischat);

        if (ischat){
            Log.d(TAG, "AAAAA"+user.getStatus());
            if (user.getStatus().equals("online")){
                holder.img_on.setVisibility(View.VISIBLE);
                holder.img_off.setVisibility(View.GONE);
            } else {
                holder.img_on.setVisibility(View.GONE);
                holder.img_off.setVisibility(View.VISIBLE);
            }
        }else {
            holder.img_on.setVisibility(View.GONE);
            holder.img_off.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MessageActivity.class);
            intent.putExtra("userid", user.getId());
            mContext.startActivity(intent);

        });


    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView image_profile;
        private CircleImageView img_on;
        private CircleImageView img_off;

        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            image_profile = itemView.findViewById(R.id.image_profile);
            img_on = itemView.findViewById(R.id.img_on);
            img_off = itemView.findViewById(R.id.img_off);
        }
    }



}
