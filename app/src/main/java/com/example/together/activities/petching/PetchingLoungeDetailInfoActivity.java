package com.example.together.activities.petching;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.together.R;
import com.example.together.activities.chat.MessageActivity;
import com.example.together.fragment.PetchingLoungeFragment;
import com.example.together.model.PetchingLounge;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class PetchingLoungeDetailInfoActivity extends AppCompatActivity {

    private static final String TAG = "PetchingLoungeDetailInf";
    FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;
    TextView requester_name, requester_intro;
    ImageView requester_img;
    Button refuse, accept;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petching_lounge_detail_info);

        requester_name = findViewById(R.id.requester_name);
        requester_intro = findViewById(R.id.requester_intro);
        requester_img = findViewById(R.id.requester_img);

        refuse = findViewById(R.id.refuse);
        accept = findViewById(R.id.accept);


         Intent intent = getIntent();

         String name = intent.getStringExtra("requester_name");
         String img = intent.getStringExtra("requester_img");
         String intro = intent.getStringExtra("requester_intro");
         String petkey = intent.getStringExtra("pet_id");
         String requester_id = intent.getStringExtra("requester_id");


         Picasso.get().load(img).fit().into(requester_img);
         requester_intro.setText(intro);
         requester_name.setText(name);

//         PetchingLoungeFragment petchingLoungeFragment = new PetchingLoungeFragment();





         //거절 클릭시 데이터 삭제petKey
         refuse.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Log.d(TAG, "신청자 "+requester_id);
                 firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                 FirebaseDatabase.getInstance().getReference("Lounge").child("PetchingBunyang").child(firebaseUser.getUid()).child("PetId").child(petkey).child("Requestor").child(requester_id).removeValue();

                 Intent backView = new Intent(PetchingLoungeDetailInfoActivity.this, PetchingActivity.class);
                 backView.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(backView);

             }
         });



         // 수락 클릭시 매칭성사 메시지 보내지게끔
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                Log.e("user",firebaseUser.getUid());
                Log.e("recever",requester_id);
                String sendName = "투개더";
                String message = "펫칭 신청을 수락하였습니다.";
                MessageActivity messageActivity = new MessageActivity();
                messageActivity.sendpushAlert(requester_id,sendName,message);
                messageActivity.sendMessage(firebaseUser.getUid(),requester_id,message);
            }
        });

    }
}