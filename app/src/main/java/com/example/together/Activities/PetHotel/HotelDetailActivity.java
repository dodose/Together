package com.example.together.Activities.PetHotel;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.together.Adapter.ProductAdapter;
import com.example.together.Model.Product;
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

public class HotelDetailActivity extends AppCompatActivity {

    TextView Topname;
    TextView Bigname;
    TextView detail_addr;
//
    String pre_addr;
    String pre_name;
    String pre_first;
    String pre_last;


    //서블릿 통신을위한 변수 선언
    JSONObject jobj;
    private URL Url;
    private String strUrl;

    //컨텐트 선언
    Context context;

    // 상품 목록 리사이클 뷰 선언
    RecyclerView mRecycleView;
    RecyclerView.LayoutManager mLayoutManager;

    // obj에서 받아온 item에넣을 변수 지정

    String name;
    String price;
    String cont;

    //view에잇는 Textview선언

    TextView info;
    TextView intro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);

        Topname = findViewById(R.id.HotelNameView);
        Bigname = findViewById(R.id.bigname);
        detail_addr = findViewById(R.id.detail_addr);
        info = findViewById(R.id.info);
        intro = findViewById(R.id.intro);

        Bundle Ex = getIntent().getExtras();

        Log.d("dd", String.valueOf(Ex));
        if (Ex != null) {
            pre_addr = Ex.getString("addr");
            pre_name = Ex.getString("name");
            pre_first = Ex.getString("first");
            pre_last = Ex.getString("last");

        }

        Topname.setText(pre_name);
        Bigname.setText(pre_name);
        detail_addr.setText(pre_addr);





        //recyclerview 선언

        mRecycleView = findViewById(R.id.product_recycle);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);


        //서블릿 통신
        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                strUrl = "http://39.127.7.80:8080/detail_product"; //탐색하고 싶은 URL이다.

            }

            @Override
            protected JSONObject doInBackground(Void... voids) {

                JSONArray data = new JSONArray();
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

                    wr.write(pre_addr + "=" + pre_name + "=" + pre_first + "=" + pre_last);

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

                ArrayList<Product> Productlist = new ArrayList<>(); // 리사이클 뷰안에 넣기위한 Vo 배열
                try {

                    JSONObject jsonObject = (JSONObject) jobj.get("result");

                    JSONArray productArray = (JSONArray) jsonObject.get("product"); //상품 배열 캐스트

                    JSONObject infoObj = (JSONObject) jsonObject.get("info"); // 정보 배열 캐스트

//                    Log.e("array", String.valueOf(productArray.length()));
//                    Log.e("array2", String.valueOf(productArray));
//                    Log.e("obj", String.valueOf(infoObj));


                    for (int i = 0; i < productArray.length(); i++) {
                        JSONObject product= productArray.getJSONObject(i);

                        price = product.optString("pd_price");
                        name = product.optString("pd_nm");
                        cont = product.optString("pd_content");



                        Productlist.add(new Product(R.drawable.hotel, name,price,cont));

//
                    }
                    System.out.println(Productlist);
//
//                    //넣은 리스트를 리사이클 뷰를 통해 실행;
                    ProductAdapter myAdapter = new ProductAdapter(Productlist,context);

                    mRecycleView.setAdapter(myAdapter);

                    infoObj.optString("etp_ph_no");
                    infoObj.optString("etp_license");
                    infoObj.optString("etp_email");
                    infoObj.optString("etp_info");
                    infoObj.optString("etp_intro");

                    info.setText(infoObj.optString("etp_info"));
                    intro.setText(infoObj.optString("etp_intro"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute();






    }
}
