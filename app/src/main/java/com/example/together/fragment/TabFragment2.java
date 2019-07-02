package com.example.together.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.together.adapter.ReviewAdapter;
import com.example.together.model.Review;
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


public class TabFragment2 extends Fragment {

    //서블릿통신
    private URL Url;
    private String strUrl;
    JSONObject jobj;
    String result;

    //별점 부여
    TextView total_count;

    //리스트 뷰 선언
   private ListView reviewListview;


    //db에서 읽어온 review 저장할 변수선언

    String user_id ;
    String user_nm ;
    String reviewcontent;
    Float star;
    String cont_dt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final String etp_cd= getArguments().getString("code"); // 업체코드 가져오기

        final View view = inflater.inflate(R.layout.tab_fragment_2, container, false);

        total_count = view.findViewById(R.id.total_count);
        reviewListview = view.findViewById(R.id.reviewList);

        //서블릿 통신
        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                strUrl = "http://39.127.7.80:8080/Funeral_review"; //탐색하고 싶은 URL이다.

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

                    wr.write(etp_cd);

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


                    Log.e("result", jobj+"");


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

                ArrayList<Review> ReviewList = new ArrayList<>(); // 리사이클 뷰안에 넣기위한 Vo 배열

                float sum = 0;
                float avg;
                String total = null;
                try {
                    JSONArray jsonArray = (JSONArray) jobj.get("result");



                    if(jsonArray.length() == 0){
                        Log.e("결과값 보기","값이 비엇음");
                        total_count.setText("0.0");
                    }else {

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject review = jsonArray.getJSONObject(i);

                            user_id = review.optString("user_id");
                            user_nm = review.optString("user_nm");
                            reviewcontent = review.optString("rb_contents");
                            star = Float.valueOf(review.optString("rb_avg"));
                            cont_dt = review.optString("rb_dt");

                            ReviewList.add(new Review(user_id,reviewcontent,star,cont_dt,user_nm));



                            sum = star + sum;
//
                            avg = sum/jsonArray.length();
                            
                            total = String.valueOf(avg);

                        }

                        total_count.setText(total);

                        ReviewAdapter reAdapter = new ReviewAdapter(ReviewList);

                        reviewListview.setAdapter(reAdapter);

//

                    }

//                    JSONObject jsonObject = (JSONObject) jobj.get("result");
//
//                    JSONArray reviewArray = (JSONArray) jsonObject.get("review"); //리뷰 배열 캐스트
                } catch (JSONException e) {
                    e.printStackTrace();
                }


             }


        }.execute();


        return view;
    }

}
