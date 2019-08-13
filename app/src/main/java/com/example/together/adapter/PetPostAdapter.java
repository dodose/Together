package com.example.together.adapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.together.activities.my_petInfo.MyPetInfoCheckActivity;
import com.example.together.activities.my_petInfo.MyPetListActivity;
import com.example.together.activities.petching.PetchingStoryDetailActivity;
import com.example.together.fragment.PostDetailFragment;
import com.example.together.model.Pet;
import com.example.together.R;
import com.example.together.model.Post;


import java.util.List;

/**
 * Created by Aws on 28/01/2018.
 */

public class PetPostAdapter extends RecyclerView.Adapter<PetPostAdapter.MyViewHolder> {

    private static final String TAG = "PetAdapter";

    private Context mContext;
    private List<Post> mPost;



    public PetPostAdapter(Context mContext, List<Post> mPost)
    {
        this.mContext = mContext;
        this.mPost = mPost;
        Log.d(TAG, "오쿠레타 "+mPost);
        Log.d(TAG, "댕댕이"+mPost.size());
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_mydoglist_post, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        final Post post = mPost.get(position);

        Glide.with(mContext)
                .load(post.getPostimage())
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .into(holder.dog_post_img);


        holder.pet_cardview_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PetchingStoryDetailActivity.class);
                intent.putExtra("postid",mPost.get(position).getPostid());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "카운트: "+mPost.size());
        return mPost.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView dog_post_img;
        CardView pet_cardview_id;


        public MyViewHolder(View itemView)
        {
            super(itemView);

            dog_post_img =  itemView.findViewById(R.id.dog_post_img);
            pet_cardview_id =  itemView.findViewById(R.id.pet_cardview_id);

        }
    }


}
