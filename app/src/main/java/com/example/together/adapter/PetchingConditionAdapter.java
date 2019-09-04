package com.example.together.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.together.R;
import com.example.together.activities.chat.ChatsActivity;
import com.example.together.activities.chat.MessageActivity;
import com.example.together.activities.my_petInfo.MyPetListActivity;
import com.example.together.model.Pet;
import com.example.together.model.User;
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
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
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

    private URL Url;
    private String strUrl;
    JSONObject jobj;


    public PetchingConditionAdapter(Context mContext, List<User> mUser, String petUid, String petbungyangid)
    {
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
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()){

                            Pet pet = dataSnapshot.getValue(Pet.class);


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

                            UpdateCalUser(mUser.get(position).getId(),mPetUid,firebaseUser.getUid());

                            if (hashMap!= null)
                            {
                                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                FirebaseDatabase.getInstance().getReference("Pets").child(firebaseUser.getUid()).child(mPetUid).removeValue();
                                FirebaseDatabase.getInstance().getReference("Lounge").child("PetchingBunyang").child(firebaseUser.getUid()).child("PetId")
                                        .child(mPetbungyangid).child("Requestor").child(mUser.get(position).getId()).removeValue();
                                FirebaseDatabase.getInstance().getReference("PetchingBunyang").child(mPetbungyangid).removeValue();

                            }
//
                            Intent intent = new Intent(mContext, ChatsActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            mContext.startActivity(intent);

                        }else
                            {
                                Intent intent = new Intent(mContext, MyPetListActivity.class);
                                mContext.startActivity(intent);
                            }
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

    public void UpdateCalUser(String userid,String mPetUid,String senduserid){
        // 캘린더 데이터베이스에 삽입

        JSONObject putjobj = new JSONObject();
        try {
            putjobj.put("userid",userid);
            putjobj.put("petid",mPetUid);
            putjobj.put("senduserid",senduserid);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                strUrl = "http://13.209.25.83:8080/UpdateCalUser"; //탐색하고 싶은 URL이다.

            }

            @Override
            protected JSONObject doInBackground(Void... voids) {

                jobj = new JSONObject();

                try {
                    //서버 연결
                    Url = new URL(strUrl);  // URL화 한다.
                    HttpURLConnection conn = (HttpURLConnection) Url.openConnection(); // URL을 연결한 객체 생성.
                    conn.setRequestMethod("POST"); // post방식 통신
                    conn.setDoOutput(true);       // 쓰기모드 지정
                    conn.setDoInput(true);        // 읽기모드 지정

                    conn.setRequestProperty("Content-Type", "application/json; utf-8");
                    conn.setRequestProperty("Accept", "application/json; utf-8");
                    conn.connect();


                    //데이터 전달 하는곳

                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(putjobj.toString());

                    wr.flush();

                    wr.close(); //전달후 닫아준다.


                    // 데이터 받아오는 곳
                    InputStream is = null;        //input스트림 개방
                    BufferedReader reader = null;

                    is = conn.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));  //문자열 셋 세팅
                    StringBuffer rbuffer = new StringBuffer();   //문자열을 담기 위한 객체
                    String line = null;

                    rbuffer.append(reader.readLine());

                    jobj = new JSONObject(rbuffer.toString().trim());
                    is.close();
//                    result = rbuffer.toString().trim();
                    conn.disconnect();



                } catch (MalformedURLException | ProtocolException exception) {
                    exception.printStackTrace();
                } catch (IOException io) {
                    io.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(JSONObject aVoid) {
                super.onPostExecute(aVoid);


                MessageActivity messageActivity = new MessageActivity();
                messageActivity.sendMessage(senduserid,userid,"펫정보를 양도하였습니다.");
                messageActivity.sendpushAlert(userid,"Together","새로운 펫의 정보가 있습니다.");

                        Toast.makeText(mContext, "양도 완료! 마이페이지에서 확인해주세요", Toast.LENGTH_SHORT).show();

            }
        }.execute();
    }
}
