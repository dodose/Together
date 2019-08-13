package com.example.together.activities.petHospital;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.together.R;
import com.example.together.fragment.Hos_fragment1;
import com.example.together.fragment.Hos_fragment2;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

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

public class PetHospitalDetailInfoActivity extends AppCompatActivity {

    TextView topNmae;
    ImageView topImg;


    //Task
    private String strUrl;
    private URL Url;

    JSONObject jobj;

    //프래그먼트
    Hos_fragment1 fragment1;
    Hos_fragment2 fragment2;

    //전송
    ImageButton order_Btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_hospital_detail_info);

        Bundle Bx = getIntent().getExtras();
        String name = Bx.getString("name");
        String img = Bx.getString("img");
        final String petcode = Bx.getString("petcode");
        final String date = Bx.getString("date");
        final String code = Bx.getString("code");
        final String Address = Bx.getString("Address");


        topNmae =findViewById(R.id.petHospitalName);
        topImg = findViewById(R.id.petHospitalImage);
        order_Btn = findViewById(R.id.hos_order);


        topNmae.setText(name);
        Picasso.get().load(img).fit().into(topImg);


        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                strUrl = "http://13.209.25.83:8080/Hospital_detail"; //탐색하고 싶은 URL이다.

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

                    wr.write(code);

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


                    Log.e("hospitaldetalinfo", String.valueOf(jobj));


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

                //프래그먼트를 보여주기
                fragment1 = new Hos_fragment1(jobj);

                fragment2 = new Hos_fragment2(jobj);


                //프레그먼트를 메니져로 보여줌
                getSupportFragmentManager().beginTransaction().add(R.id.container,fragment1).commit();

                //3탭기능 구성
                TabLayout tabs=(TabLayout)findViewById(R.id.tabs);
                tabs.addTab(tabs.newTab().setText("정보"));
                tabs.addTab(tabs.newTab().setText("리뷰"));


                //탭버튼을 클릭했을 때 프레그먼트 동작
                tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {

                        //선택된 탭 번호 반환
                        int position =tab.getPosition();

                        Fragment selected = null;

                        if(position == 0 ){

                            selected = fragment1;

                        }else if(position == 1 ){

                            selected = fragment2;

                        }

                        //선택된 프레그먼트로 바꿔줌
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {


                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

            }
        }.execute();


        order_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetHospitalDetailInfoActivity.this,PetHospitalCheckReservationInfoActivity.class);
                intent.putExtra("date",date);
                intent.putExtra("petcode",petcode);
                intent.putExtra("etp_name",topNmae.getText());
                intent.putExtra("Address",Address);
                startActivity(intent);
            }
        });







    }
}
