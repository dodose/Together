package com.example.together.Activities.PetHotel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.together.Activities.MyPetInfo.MyPetListActivity;
import com.example.together.Adapter.HotelAdapter;
import com.example.together.Fragment.ProfileFragment;
import com.example.together.Model.Hotel;
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
import java.util.Dictionary;
import java.util.List;


public class HotelListDataActivity extends AppCompatActivity{

    String first_day;
    String Last_day;
    String Address;
    private URL Url;
    private String strUrl;
    JSONObject jobj;
    Context context;

    Button daysBtn;
    TextView Addrserch;


    RecyclerView mRecycleView;
    RecyclerView.LayoutManager mLayoutManager;


    //넣을 변수들
    String mName;
    String mAddr;
    String mTime;
    String mcontent;
    String img_path;
    Float starcount;
    String firstprise;
    String etp_lat; //위도
    String etp_lnt; // 경도

    ImageButton activity_hotel_list_databackbtn;  //acitivity_hotel_list_databackbtn 버튼


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list_data);


        activity_hotel_list_databackbtn = findViewById(R.id.activity_hotel_list_databackbtn);  //뒤로 가기 버튼


        //뒤로가기ㅣ 테스트로 만들었음
        activity_hotel_list_databackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HotelListDataActivity.this, PetHotelActivity.class);
                startActivity(intent);

            }
        });
        //뒤로가기ㅣ 테스트로 만들었음-ㅇㅋ



        //recyclerview 선언

        mRecycleView = findViewById(R.id.recycler_view);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);



        //이전값에서 선언한 intent의 Extras 값을 넘겨주고 변수에 저장
        Bundle Ex = getIntent().getExtras();
        if (Ex != null) {
            first_day = Ex.getString("First_day");
            Last_day = Ex.getString("Last_day");
            Address = Ex.getString("AddVal");
        }
        Log.e("result", first_day);

        String f[] = first_day.split("/");
        String l[] = Last_day.split("/");

        int fr = Integer.parseInt(f[2]);
        int la = Integer.parseInt(l[2]);
        int Sum = la - fr + 1;

        daysBtn = findViewById(R.id.dayBtn);
        Addrserch = findViewById(R.id.Addrserch);
        daysBtn.setText(first_day.substring(5) + "~" + Last_day.substring(5) + " , " + Sum + "박");
        Addrserch.setText(Address);


        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                strUrl = "http://39.127.7.80:8080/test"; //탐색하고 싶은 URL이다.

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

                    wr.write(first_day + "=" + Last_day + "=" + Address);

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

                ArrayList<Hotel> HotelList = new ArrayList<>(); // 리사이클 뷰안에 넣기위한 Vo 배열
                try {

                    JSONArray jsonArray = (JSONArray) jobj.get("result");
//                    System.out.println(jsonArray.length());


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        mName = jsonObject.optString("etp_nm");
                        mAddr = jsonObject.optString("etp_addr");
                        mTime = jsonObject.optString("etp_if_time1") + "~" + jsonObject.optString("etp_if_time2");
                        mcontent = jsonObject.optString("etp_cont");
                        img_path = jsonObject.optString("etp_img_path");
                        starcount = Float.valueOf(jsonObject.optString("avg"));
                        firstprise = jsonObject.optString("firstprice");
                        etp_lat = jsonObject.optString("etp_lat");
                        etp_lnt = jsonObject.optString("etp_lnt");

                        Log.e("결과값",mName +" "+ mAddr +" "+ mTime +" "+ mcontent +" "+ img_path +" "+ starcount +" "+ firstprise +" "+ etp_lat +" "+ etp_lnt);


                        HotelList.add(new Hotel(img_path, mAddr, mName, mTime, mcontent,starcount,firstprise));


                    }
//                    System.out.println(HotelList);



                    //넣은 리스트를 리사이클 뷰를 통해 실행;
                    HotelAdapter myAdapter = new HotelAdapter(HotelList,context,first_day,Last_day);

                    mRecycleView.setAdapter(myAdapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute();



    }


}