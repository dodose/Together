package com.example.together.activities.chat;

import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.together.MyFirebaseMessagingService;
import com.example.together.activities.LoginActivity;
import com.example.together.adapter.MessageAdapter;
import com.example.together.model.Chat;
import com.example.together.model.User;
import com.example.together.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    private static final String TAG = "MessageActivity";

    private String strUrl;
    private URL Url;

    CircleImageView image_profile;
    TextView username;

    FirebaseUser fuser;
    DatabaseReference reference;

    ImageButton btn_send;
    EditText text_send;

    Intent intent;

    MessageAdapter messageAdapter;
    List<Chat> mchat;

    RecyclerView recyclerView;

    ValueEventListener seenListener;

    String userid;


    boolean notify = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        image_profile = findViewById(R.id.image_profile);
        username = findViewById(R.id.username);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);

        intent = getIntent();
        final String userid = intent.getStringExtra("userid");


        btn_send.setOnClickListener(v -> {

            notify = true;

            String msg = text_send.getText().toString();
            if (!msg.equals("")){
                sendMessage(fuser.getUid(), userid, msg);
            }else {
                Toast.makeText(MessageActivity.this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
            }
            text_send.setText("");

            //푸쉬알림
            sendpushAlert(userid);


        });

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImageurl().equals("default")){
                    image_profile.setImageResource(R.mipmap.ic_launcher);

                }else {
                    Glide.with(getApplicationContext()).load(user.getImageurl()).into(image_profile);
                }


                readMessages(fuser.getUid(), userid, user.getImageurl());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        seenMessage(userid);
    }

    public void sendpushAlert(String userid) {

        reference = FirebaseDatabase.getInstance().getReference("Tokens").child(userid).child("TokenUid");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                HashMap<String,Object> tokenKey = (HashMap<String, Object>) dataSnapshot.getValue();

                new AsyncTask<Void, Void, JSONObject>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        strUrl = "https://fcm.googleapis.com/fcm/send"; //탐색하고 싶은 URL이다.

                    }

                    @Override
                    protected JSONObject doInBackground(Void... voids) {


                        try {
                            //서버 연결
                            String apiKey = "AAAAjMWWtVI:APA91bFVgt1CuhihHU_ErDkpq24MiC7SN4ERW5UurfLLHoa938CRaWzl9Y6zNWGWtSVwuWXosQ-oMTbuMGiD66sn5r0JYgU033VT_Es8MbxPOItpt6BwjiKrU14bfkX6XbiS4Hfb33iH";
                            Url = new URL(strUrl);
                            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/json; utf-8");
                            conn.setRequestProperty("Authorization", "key=" + apiKey);
                            conn.setDoOutput(true);       // 쓰기모드 지정


                            String input = "{\"notification\" : {\"title\" : \"[Together Talk] \", \"body\" : \"새로운 메세지가 왔습니다..\"}, \"to\":\""+tokenKey.get("token").toString()+"\"}";

                            OutputStream os = conn.getOutputStream();

                            // 서버에서 날려서 한글 깨지는 사람은 아래처럼  UTF-8로 인코딩해서 날려주자
                            os.write(input.getBytes("UTF-8"));
                            os.flush();
                            os.close();

                            int responseCode = conn.getResponseCode();
                            System.out.println("\nSending 'POST' request to URL : " + Url);
                            System.out.println("Post parameters : " + input);
                            System.out.println("Response Code : " + responseCode);

                            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            String inputLine;
                            StringBuffer response = new StringBuffer();

                            while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                            }
                            in.close();
                            // print result
                            System.out.println(response.toString());
                            //데이터 전달 하는곳

//                            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//
//                            wr.write();
//
//                            wr.flush();
//
//                            wr.close(); //전달후 닫아준다.
//
//
//                            // 데이터 받아오는 곳
//                            InputStream is = null;        //input스트림 개방
//                            BufferedReader reader = null;
//
//                            is = conn.getInputStream();
//                            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));  //문자열 셋 세팅
//                            StringBuffer rbuffer = new StringBuffer();   //문자열을 담기 위한 객체
//                            String line = null;
//
//                            rbuffer.append(reader.readLine());
//
//                            String jobj = rbuffer.toString().trim();
//                            is.close();
//
//                            conn.disconnect();
//
//
//                            Log.e("result", jobj + "");


                        } catch (MalformedURLException | ProtocolException exception) {
                            exception.printStackTrace();
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(JSONObject aVoid) {
                        super.onPostExecute(aVoid);

                    }


                }.execute();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


}



    public void seenMessage(String userid){
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(fuser.getUid()) && chat.getSender().equals(userid)){
                        HashMap<String , Object> hashMap = new HashMap<>();
                        hashMap.put("isseen", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void sendMessage(String sender, final String receiver, String message){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);

        reference.child("Chats").push().setValue(hashMap);

        final String msg = message;

        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Log.d(TAG, "노티파이"+notify);
                if (notify) {

                }else{
                    notify = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    private void readMessages(final String myid, final String userid, final String imageurl){
        mchat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
                        mchat.add(chat);
                    }

                    messageAdapter = new MessageAdapter(MessageActivity.this, mchat, imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        reference.removeEventListener(seenListener);
    }

}

