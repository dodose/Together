package com.example.together.activities.goodbyePet;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.together.adapter.FuneralAdapter;
import com.example.together.model.Funeral;
import com.example.together.R;

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

public class GoodbyePetStoreListActivity extends AppCompatActivity {

    //db접속을위한 선언문
    private String strUrl;
    private URL Url;


    //컨텍스트
    Context context;


    //이전에 받아온 값
    String day;
    String Time;
    String addr;


    //넣을 변수들
    String mName;
    String mAddr;
    String mTime;
    String mcontent;
    String img_path;
    Float starcount;
    String etp_lat; //위도
    String etp_lnt; // 경도
    String price;
    String etp_cd; //업체코드

    //AsyncTask 결과값
    JSONObject jobj;

    //리사이클뷰 선언
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodbye_pet_store_list);


        //recyclerview 선언
        mRecyclerView = findViewById(R.id.funeral_recycle);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Bundle Ex = getIntent().getExtras();
        day = Ex.getString("day");
        Time = Ex.getString("time");
        addr = Ex.getString("addr");

        Log.e("result", day + Time + addr);



        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                strUrl = "http://39.127.7.80:8080/funeral_list"; //탐색하고 싶은 URL이다.

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

                    wr.write(day + "=" + Time + "=" + addr);

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
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                return jobj;
            }

            @Override
            protected void onPostExecute(JSONObject aVoid) {
                super.onPostExecute(aVoid);


                ArrayList<Funeral> FuneralList = new ArrayList<>(); // 리사이클 뷰안에 넣기위한 Vo 배열


                try {


                    JSONArray jsonArray = (JSONArray) jobj.get("result");


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        mName = jsonObject.optString("etp_nm");
                        mAddr = jsonObject.optString("etp_addr");
                        mTime = jsonObject.optString("etp_if_time1") + "~" + jsonObject.optString("etp_if_time2");
                        mcontent = jsonObject.optString("etp_cont");
                        img_path = jsonObject.optString("etp_img_path");
                        starcount = Float.valueOf(jsonObject.optString("avg"));
                        etp_lat = jsonObject.optString("etp_lat");
                        etp_lnt = jsonObject.optString("etp_lnt");
                        price = jsonObject.optString("firstprice");
                        etp_cd = jsonObject.optString("etp_cd");

                        Log.e("etp_cd",etp_cd);

                       FuneralList.add(new Funeral(img_path, mAddr, mName, mTime, mcontent,starcount,price,etp_cd));

                    }



                    FuneralAdapter myAdapter = new FuneralAdapter(FuneralList,context,day,Time,addr);

                    mRecyclerView.setAdapter(myAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }.execute();



    }
}
