package com.example.together.activities.petHospital;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.together.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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


import androidx.appcompat.app.AppCompatActivity;

public class PetHospitalShowReservationInfoActivity extends AppCompatActivity {

    JSONObject SendObj = new JSONObject();

    private String strUrl;
    private URL Url;

    JSONObject jobj;
    FirebaseUser fuser;
    String edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_hospital_show_reservation_info);

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        Bundle Bx = getIntent().getExtras();

        ArrayList<String> canser = Bx.getStringArrayList("canser");
        String Time = Bx.getString("orderTime");
        String petcode = Bx.getString("petcode");
        String date = Bx.getString("date");
        String etp_nm = Bx.getString("etp_nm");
        if(Bx.getString("edit") == null){
            edit = "입력정보 없음";
        }else{
            edit = Bx.getString("edit");

        }

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        try {
            SendObj.put("user_id",fuser.getEmail());
            SendObj.put("Time",Time);
            SendObj.put("petcode",petcode);
            SendObj.put("date",date);
            SendObj.put("etp_nm",etp_nm);
            if(edit.equals("입력정보없음")){
                SendObj.put("edit","내용없음");
            }else{
                SendObj.put("edit",edit);
            }
            SendObj.put("canser",canser);
            Log.e("jobj",SendObj+"");

//
            //서블릿 통신

        } catch (JSONException e) {
            e.printStackTrace();
        }


        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                strUrl = "http://39.127.7.80:8080/Hospital_ordersSet"; //탐색하고 싶은 URL이다.

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

                    wr.write(SendObj.toString());

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


                    Log.e("result", jobj + "");


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


            }


        }.execute();




    }
}
