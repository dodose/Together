package com.example.together.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.together.fragment.PostDetailFragment;
import com.example.together.model.Post;
import com.example.together.R;

import java.util.List;

public class MyPhotoAdapter extends RecyclerView.Adapter<MyPhotoAdapter.ViewHolder> {


    private Context context;
    private List<Post> mPosts;

    public MyPhotoAdapter(Context context, List<Post> mPosts){
        this.context = context;
        this.mPosts = mPosts;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.photos_item, viewGroup, false);
        return new MyPhotoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        final Post post = mPosts.get(i);

        Glide.with(context).load(post.getPostimage()).into(viewHolder.post_image);

        viewHolder.post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("postid", post.getPostid());
                editor.apply();

                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PostDetailFragment()).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView post_image;

        public ViewHolder(View itemView){
            super(itemView);

            post_image = itemView.findViewById(R.id.post_image);
        }

    }
}
