package com.example.together.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.R;
import com.example.together.adapter.MypageOrderAdapter;
import com.example.together.model.UserOrder;
import com.google.android.material.tabs.TabLayout;
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

public class MypageFragment2 extends Fragment {

    private AppCompatActivity activity;

    TextView username;

    RecyclerView orderList_recycler_ing;
    RecyclerView orderList_recycler_success;
    RecyclerView orderList_recycler_cancle;

    FirebaseUser fuser;

    private String strUrl;
    private URL Url;

    JSONObject jobj;

    String or_cd;
    String or_stat;
    String or_dt;
    String or_dt2;
    String th_dt;
    String or_price;
    String etp_cd;
    String etp_nm;

    public MypageFragment2(TextView username) {
        this.username = username;
    }

    // Fragment 의 layout 을 설정하기 위해서는 onCreateView() 메소드를 사용한다.
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mypage_two, container, false);

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        //3탭기능 구성
        TabLayout tabs= view.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("진행중"));
        tabs.addTab(tabs.newTab().setText("완료내역"));
        tabs.addTab(tabs.newTab().setText("취소내역"));

        username.setText("예약 내역");

        orderList_recycler_ing = view.findViewById(R.id.orderList_recycler_ing);
        orderList_recycler_ing.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 1);
        orderList_recycler_ing.setLayoutManager(linearLayoutManager);

        orderList_recycler_success = view.findViewById(R.id.orderList_recycler_success);
        orderList_recycler_success.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager_success = new GridLayoutManager(getContext(), 1);
        orderList_recycler_success.setLayoutManager(linearLayoutManager_success);

        orderList_recycler_cancle = view.findViewById(R.id.orderList_recycler_cancle);
        orderList_recycler_cancle.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager_cancle = new GridLayoutManager(getContext(), 1);
        orderList_recycler_cancle.setLayoutManager(linearLayoutManager_cancle);


        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                strUrl = "http://13.209.25.83:8080/UserOrder_list"; //탐색하고 싶은 URL이다.

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

                    wr.write(fuser.getEmail());

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


                ArrayList<UserOrder> UserOrder_i = new ArrayList<>(); // 리사이클 뷰안에 넣기위한 Vo 배열 (진행)
                ArrayList<UserOrder> UserOrder_s = new ArrayList<>(); // 리사이클 뷰안에 넣기위한 Vo 배열 (완료)
                ArrayList<UserOrder> UserOrder_c = new ArrayList<>(); // 리사이클 뷰안에 넣기위한 Vo 배열 (취소)

                JSONArray jsonArray = null;
                try {
                    jsonArray = (JSONArray) jobj.get("result");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        or_cd = jsonObject.optString("or_cd");
                        or_stat = jsonObject.optString("or_stat");
                        or_dt = jsonObject.optString("or_dt");
                        or_dt2 = jsonObject.optString("or_dt2");
                        th_dt = jsonObject.optString("th_dt");
                        or_price = jsonObject.optString("or_price");
                        etp_cd = jsonObject.optString("etp_cd");
                        etp_nm = jsonObject.optString("etp_nm");

                        if(or_stat.equals("1")){
                            UserOrder_i.add(new UserOrder(or_cd, or_stat, or_dt, or_dt2, th_dt, or_price ,etp_cd,etp_nm));
                        }else if(or_stat.equals("2")){
                            UserOrder_s.add(new UserOrder(or_cd, or_stat, or_dt, or_dt2, th_dt, or_price ,etp_cd,etp_nm));
                        }else if(or_stat.equals("3")){
                            UserOrder_c.add(new UserOrder(or_cd, or_stat, or_dt, or_dt2, th_dt, or_price ,etp_cd,etp_nm));
                        }

                    }

                    MypageOrderAdapter mAdapter = new MypageOrderAdapter(UserOrder_i);
                    orderList_recycler_ing.setAdapter(mAdapter);

                    MypageOrderAdapter mAdapter1 = new MypageOrderAdapter(UserOrder_s);
                    orderList_recycler_success.setAdapter(mAdapter1);

                    MypageOrderAdapter mAdapter2 = new MypageOrderAdapter(UserOrder_c);
                    orderList_recycler_cancle.setAdapter(mAdapter2);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                activity = (AppCompatActivity) getActivity();

                //탭버튼을 클릭했을 때 프레그먼트 동작
                tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {



                        //선택된 탭 번호 반환
                        int position =tab.getPosition();

                        if(position == 0 ){

                                orderList_recycler_ing.setVisibility(View.VISIBLE);
                                orderList_recycler_success.setVisibility(View.GONE);
                                orderList_recycler_cancle.setVisibility(View.GONE);

                        }else if(position == 1 ){

                            orderList_recycler_ing.setVisibility(View.GONE);
                            orderList_recycler_success.setVisibility(View.VISIBLE);
                            orderList_recycler_cancle.setVisibility(View.GONE);

                        }else if(position == 2){

                            orderList_recycler_ing.setVisibility(View.GONE);
                            orderList_recycler_success.setVisibility(View.GONE);
                            orderList_recycler_cancle.setVisibility(View.VISIBLE);
                        }

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




        return view;
    }

}
