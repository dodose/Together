package com.example.together.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.together.R;
import com.example.together.model.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PetchingConditionAdapter extends RecyclerView.Adapter<PetchingConditionAdapter.MyViewHolder>
{
    private static final String TAG = "PetchingConditionAdapte";

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    Context mContext;
    List<User> mUser;


    public PetchingConditionAdapter(Context mContext, List<User> mUser)
    {
        this.mContext = mContext;
        this.mUser = mUser;
    }


    @NonNull
    @Override
    public PetchingConditionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PetchingConditionAdapter.MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_petching_condition, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PetchingConditionAdapter.MyViewHolder holder, final int position)
    {
        final User user = mUser.get(position);

        Glide.with(mContext)
                .load(user.getImageurl())
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .into(holder.image_profile);

        holder.username.setText(mUser.get(position).getUsername());

        holder.refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
