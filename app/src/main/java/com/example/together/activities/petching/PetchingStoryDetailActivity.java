package com.example.together.activities.petching;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.together.R;
import com.example.together.adapter.PostAdapter;
import com.example.together.model.Post;
import com.example.together.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PetchingStoryDetailActivity extends AppCompatActivity {

    private static final String TAG = "PetchingStoryDetailActi";
    String postid;
    ImageView image_profile, post_image;
    TextView username, description;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petching_story_detail);

        image_profile = findViewById(R.id.image_profile);
        post_image = findViewById(R.id.post_image);
        username = findViewById(R.id.username);
        description = findViewById(R.id.description);


        readPost();

    }


    public void readPost(){

        Intent intent = getIntent();
        postid = intent.getExtras().getString("postid");

        Log.d(TAG, "포그바 "+postid);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts").child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Post post = dataSnapshot.getValue(Post.class);

                description.setText(post.getDescription());
                Picasso.get().load(post.getPostimage()).fit().into(post_image);

                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users").child(post.getPublisher());
                reference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        username.setText(user.getUsername());
                        Picasso.get().load(user.getImageurl()).fit().into(image_profile);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {



            }
        });


    }

}
