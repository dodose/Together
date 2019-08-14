package com.example.together.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.R;
import com.example.together.adapter.TimesetAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

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

@SuppressLint("ValidFragment")
public class Hos_or_fragment2 extends Fragment {


    Context context;
    String topName;
    String date;

    private String strUrl;
    private URL Url;

    RecyclerView mRecycleView;
    RecyclerView.LayoutManager mLayoutManager;

    JSONObject jobj;

    public Hos_or_fragment2(String topName,String date) {
        this.topName = topName;
        this.date = date;
    }


    // Fragment 의 layout 을 설정하기 위해서는 onCreateView() 메소드를 사용한다.
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.hos_or_fragment2, container, false);


        //recyclerview 선언
        mRecycleView = v.findViewById(R.id.Timerecycler);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        Log.e("mlayout",mLayoutManager+"");
        mRecycleView.setLayoutManager(mLayoutManager);



        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                strUrl = "http://13.209.25.83:8080/Hospital_timeCheck"; //탐색하고 싶은 URL이다.

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

                    wr.write(topName + "=" + date);

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

                try {

                    JSONArray jsonArray = (JSONArray) jobj.get("result");

                    ArrayList<String> CheckTime = new ArrayList<>();

                    ArrayList<String> checklist = new ArrayList<>();

                    for(int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        CheckTime.add(jsonObject.optString("CheckTime1"));

                    }

                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                       String etp_time = jsonObject.optString("etp_time");
                       String Timeset = jsonObject.optString("TimeSet");

                       Log.e("timeArray",CheckTime+"");


                    String t_time1 = null;
                    String t_time2 = null;
                    String set;

                    String result[] = etp_time.split("~");
                    String timeset1 = result[0];
                    String timeset2 = result[1];

                    String s_time1[] = timeset1.split(":");
                    String s_time2[] = timeset2.split(":");

                    String Time1 = s_time1[0]; //업체의 첫시간
                    String Time2 = s_time2[0]; // 업체의 마감시간

                    Log.e("time1",Time1);
                    Log.e("time2",Time2);

                    checklist = new ArrayList<>(); // 날짜값들이 들어갈 공간

                    if(t_time1 == null) {
                        t_time1 = timeset1;
                    }

                    int v_t1 = Integer.parseInt(Time1);
                    int v_t2 = Integer.parseInt(Time2);
                    int count = 0;


                    for(int f=0; f<24; f++){

                        if(v_t1 != v_t2){


                            if(Timeset.equals("60")) {
                                if(v_t1 < 9) {
                                    checklist.add("0"+v_t1 + ":00" + "~" + "0"+Integer.parseInt(String.valueOf(v_t1 + 1)) + ":00");
                                }else if(v_t1 == 9){
                                    checklist.add("0"+v_t1 + ":00" + "~" +Integer.parseInt(String.valueOf(v_t1 + 1)) + ":00");
                                }else{
                                    checklist.add(v_t1 + ":00" + "~" +Integer.parseInt(String.valueOf(v_t1 + 1)) + ":00");
                                }
                                v_t1 += 1;
                                count += 1;
                            }else if(Timeset.equals("30")){
                                if(v_t1 < 9) {
                                checklist.add("0"+v_t1 + ":00" + "~" + "0"+v_t1 + ":30");
                                checklist.add("0"+v_t1 + ":30" + "~" +"0"+Integer.parseInt(String.valueOf(v_t1 + 1)) + ":00");
                                }else if(v_t1 == 9){
                                    checklist.add("0"+v_t1 + ":00" + "~" + "0"+v_t1 + ":30");
                                    checklist.add("0"+v_t1 + ":30" + "~" +Integer.parseInt(String.valueOf(v_t1 + 1)) + ":00");
                                }else{
                                    checklist.add(v_t1 + ":00" + "~" + v_t1 + ":30");
                                    checklist.add(v_t1 + ":30" + "~" +Integer.parseInt(String.valueOf(v_t1 + 1)) + ":00");
                                }
                                v_t1 += 1;
                                count += 2;
                            }else if(Timeset.equals("15")){
                                if(v_t1 < 9) {
                                checklist.add("0"+v_t1 + ":00" + "~" + "0"+v_t1 + ":15");
                                checklist.add("0"+v_t1 + ":15" + "~" + "0"+v_t1 + ":30");
                                checklist.add("0"+v_t1 + ":30" + "~" + "0"+v_t1 + ":45");
                                checklist.add("0"+v_t1 + ":45" + "~" +"0"+Integer.parseInt(String.valueOf(v_t1 + 1)) + ":00");
                                }else if(v_t1 == 9){
                                    checklist.add("0"+v_t1 + ":00" + "~" +"0"+ v_t1 + ":15");
                                    checklist.add("0"+v_t1 + ":15" + "~" + "0"+v_t1 + ":30");
                                    checklist.add("0"+v_t1 + ":30" + "~" + "0"+v_t1 + ":45");
                                    checklist.add("0"+v_t1 + ":45" + "~" +Integer.parseInt(String.valueOf(v_t1 + 1)) + ":00");
                                }else{
                                    checklist.add(v_t1 + ":00" + "~" + v_t1 + ":15");
                                    checklist.add(v_t1 + ":15" + "~" + v_t1 + ":30");
                                    checklist.add(v_t1 + ":30" + "~" + v_t1 + ":45");
                                    checklist.add(v_t1 + ":45" + "~" +Integer.parseInt(String.valueOf(v_t1 + 1)) + ":00");
                                }
                                v_t1 += 1;
                                count += 4;
                            }


                        }

                    }

                    Log.e("checklist",checklist+"");
                    TimesetAdapter myadapter = new TimesetAdapter(CheckTime,checklist);

                    mRecycleView.setAdapter(myadapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }.execute();



        return v;

    }

}
