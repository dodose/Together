package com.example.together.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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


public class TabFragment3 extends Fragment {

    //정보 서블릿 통신
    private URL Url;
    private String strUrl;
    JSONObject jobj;


    //정보를 담기위한 변수 선언
    String etp_nm;
    String etp_addr;
    String etp_ph_no;
    String etp_lat;
    String etp_lnt;
    String etp_email;
    String etp_info;
    String etp_intro;
    String etp_time;
    String etp_user;
    String etp_img;
    String etp_license;

    //해당하는 layout 변수

    TextView time;
    TextView ph;
    TextView addr;
    TextView info;
    TextView intro;
    TextView nm;
    TextView license;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final String etp_cd= getArguments().getString("code"); // 업체코드 가져오기

        final View view = inflater.inflate(R.layout.tab_fragment_3, container, false);

        time = view.findViewById(R.id.etp_time);
        ph = view.findViewById(R.id.etp_ph);
        addr = view.findViewById(R.id.etp_addr);
        info = view.findViewById(R.id.etp_info);
        intro = view.findViewById(R.id.etp_intro2);
        nm = view.findViewById(R.id.etp_nm);
        license = view.findViewById(R.id.etp_license);



    new AsyncTask<Void, Void, JSONObject>() {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            strUrl = "http://39.127.7.80:8080/FuneralInfo"; //탐색하고 싶은 URL이다.

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


                Log.e("resultInfointro", jobj + "");


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

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject info = jsonArray.getJSONObject(i);

                   etp_nm = info.optString("etp_nm");
                   etp_addr = info.optString("etp_addr");
                   etp_ph_no= info.optString("etp_ph_no");
                   etp_lat= info.optString("etp_lat");
                   etp_lnt= info.optString("etp_lnt");
                   etp_email = info.optString("etp_email");
                   etp_info = info.optString("etp_info");
                   etp_intro= info.optString("etp_intro");
                   etp_time= info.optString("etp_time");
                   etp_user= info.optString("etp_user");
                   etp_img= info.optString("etp_img");
                   etp_license = info.optString("etp_license");


                }

                time.setText(etp_time);
                ph.setText(etp_ph_no);
                addr.setText(etp_addr);
                info.setText(etp_info);
                intro.setText(etp_intro);
                nm.setText(etp_nm);
                license.setText(etp_license);

                Log.e("etp_time",time.getText()+"");

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }.execute();

        return view;
    }
}
