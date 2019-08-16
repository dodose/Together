package com.example.together.activities.petHospital;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.together.MypageOrder;
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
    Button close,myOrder;

    TextView order_time,status,etp_name,info;

    private String strUrl;
    private URL Url;

    JSONObject jobj;
    FirebaseUser fuser;
    String edit;

    JSONArray Cjarry = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_hospital_show_reservation_info);



        fuser = FirebaseAuth.getInstance().getCurrentUser();

        close = findViewById(R.id.close);

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

        order_time = findViewById(R.id.order_time);
        etp_name = findViewById(R.id.etp_nm);
        status = findViewById(R.id.status);
        info = findViewById(R.id.info);

        order_time.setText(date+" "+Time);
        etp_name.setText(etp_nm);
        status.setText("예약 대기중");
        info.setText("상세내역\n\n"+"선택한 질병명\n\n"+canser+"\n\n"+"문의주신내용\n\n"+edit);

        myOrder = findViewById(R.id.myOrder);

        myOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetHospitalShowReservationInfoActivity.this, MypageOrder.class);
                startActivity(intent);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetHospitalShowReservationInfoActivity.this, PetHospitalDetailInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });


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

            JSONArray Cjarry = new JSONArray();

            for(int i=0; i<canser.size(); i++){
                JSONObject Cjobj = new JSONObject();
                Cjobj.put("list",canser.get(i));
                Cjarry.put(Cjobj);
            }
            SendObj.put("Canser",Cjarry);
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
                strUrl = "http://13.209.25.83:8080/Hospital_ordersSet"; //탐색하고 싶은 URL이다.

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
    //뒤로가기 버튼 막기
    @Override public void onBackPressed() { //super.onBackPressed();
    }

}
