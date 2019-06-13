package com.example.together.Activities.GoodbyePet;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.together.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class GoodbyePetStoreListActivity extends AppCompatActivity {

    //db접속을위한 선언문
    private String strUrl;
    private URL Url;

    String result;


    //이전에 받아온 값
    String day;
    String Time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodbye_pet_store_list);

        Bundle Ex = getIntent().getExtras();
        day = Ex.getString("day");
        Time = Ex.getString("time");




        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                strUrl = "http://39.127.7.80:8080/funeral_list"; //탐색하고 싶은 URL이다.

            }

            @Override
            protected Void doInBackground(Void... voids) {


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

                    wr.write(day + "=" + Time );

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

                    result = rbuffer.toString().trim();
                    is.close();
                    conn.disconnect();


                    Log.e("result", result);


                } catch (MalformedURLException | ProtocolException exception) {
                    exception.printStackTrace();
                } catch (IOException io) {
                    io.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);



            }
        }.execute();



    }
}
