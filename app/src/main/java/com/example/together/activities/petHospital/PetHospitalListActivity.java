package com.example.together.activities.petHospital;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.R;
import com.example.together.adapter.HospitalAdapter;
import com.example.together.model.Hospital;
import com.example.together.model.Pet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.List;

public class PetHospitalListActivity extends AppCompatActivity {

    private static final String TAG = "PetHospitalListActivity";

    Context mContent;

    DatabaseReference reference;
    FirebaseUser firebaseUser;
    List<Pet> lsPet;
    private String petname;
    private String strUrl;
    private URL Url;
    private JSONObject jobj;
    //TextView

    //저장변수
    String mName;
    String mAddr;
    String mTime;
    String img_path;
    Float starcount;
    String etp_lat;
    String etp_lnt;
    String etp_cd;
    String etpcontent;


    //recycler view 선언
    RecyclerView mRecycleView;
    RecyclerView.LayoutManager mLayoutManager;


    Button selectDay, selectPet;

    final static String addr = "대구";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_hospital_list);

        Bundle Bx = getIntent().getExtras();
        final String petcode = Bx.getString("PETCODE");
        final String day = Bx.getString("SELECTDAY");
        final ArrayList<String> petcategory = Bx.getStringArrayList("PETCATEGORY");

        //petcode를 통해 uid값을 가져옴
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Pets").child(firebaseUser.getUid()).child(petcode);

        reference.child("petname").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue() != null) {
                        try {
                            petname = (String) snapshot.getValue();
                            selectPet.setText(petname);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("TAG", " it's null.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //recyclerview 선언

        mRecycleView = findViewById(R.id.Hospital_recycler);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);

        selectDay = findViewById(R.id.date_select);
        selectPet = findViewById(R.id.pet_select_count);

        selectDay.setText(day);
        selectPet.setText("값이올예정");


        final JSONObject t_map = new JSONObject();
        JSONArray tjarry = new JSONArray();

        for (int i = 0; i < petcategory.size(); i++) {
            try {
                JSONObject map = new JSONObject();
                map.put("Day", day);
                map.put("category", petcategory.get(i));
                tjarry.put(map);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        try {
            t_map.put("result", tjarry);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                strUrl = "http://39.127.7.80:8080/Hospital_list"; //탐색하고 싶은 URL이다.

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

                    wr.write(String.valueOf(t_map));

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
                    conn.disconnect();


                    Log.e("result", String.valueOf(jobj));


                } catch (MalformedURLException | ProtocolException exception) {
                    exception.printStackTrace();
                } catch (IOException io) {
                    io.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return jobj;
            }

            @Override
            protected void onPostExecute(JSONObject aVoid) {
                super.onPostExecute(aVoid);

                ArrayList<Hospital> HospitalList = new ArrayList<>(); // 리사이클 뷰안에 넣기위한 Vo 배열


                try {


                    JSONArray jsonArray = (JSONArray) jobj.get("result");


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        mName = jsonObject.optString("etp_nm");
                        mAddr = jsonObject.optString("etp_addr");
                        mTime = jsonObject.optString("if_time1") + "~" + jsonObject.optString("if_time2");
                        img_path = jsonObject.optString("etp_img_path");
                        etpcontent = jsonObject.optString("etp_content");
                        starcount = Float.valueOf(jsonObject.optString("avg"));
                        etp_lat = jsonObject.optString("etp_lat");
                        etp_lnt = jsonObject.optString("etp_lnt");
                        etp_cd = jsonObject.optString("etp_cd");


                        HospitalList.add(new Hospital(img_path, mAddr, mName, mTime, starcount, etp_cd, etpcontent));

                    }


                    HospitalAdapter myAdapter = new HospitalAdapter(HospitalList, mContent,petcode,day);

                    mRecycleView.setAdapter(myAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();





    }

}
