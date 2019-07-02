package com.example.together.activities.petHotel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.together.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class reViewActivity extends AppCompatActivity {

    //접속중인 아이디 값 가져오기
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


    //db접속
    private String strUrl;
    private URL Url;
    private String result;

    //별점바
    RatingBar ratingBar;
    Button Btn;
    TextView rateDisplay;
    EditText reviewtext;


    //변수에 담을 내용들

    String review_content;
    String starcount;
    String user;

    //업체코드 변수
    String etp_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_view);

        ratingBar = findViewById(R.id.ratingBar); //별점란
        Btn = findViewById(R.id.insertreview); // 작성완료
        rateDisplay = findViewById(R.id.ratingDisplay); // 별점
        reviewtext = findViewById(R.id.reviewtext); // 내용적는곳

        //리뷰를 적기위해 가져오는 업체코드
        Bundle Ex = getIntent().getExtras();
         etp_code =  Ex.getString("etpcode");


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rateDisplay.setText(String.valueOf(rating));
            }
        });

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review_content = String.valueOf(reviewtext.getText());
                starcount = (String) rateDisplay.getText();
                user = firebaseUser.getEmail();

                Log.e("name",user);
                Log.e("star",starcount);
                Log.e("cont",review_content);
                Log.e("code",etp_code);

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        strUrl = "http://39.127.7.80:8080/Review"; //탐색하고 싶은 URL이다.

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

                            wr.write( user +"="+ etp_code + "=" + starcount + "=" + review_content);

                            wr.flush();

                            wr.close(); //전달후 닫아준다.


                            // 데이터 받아오는 곳
                            InputStream is = null;        //input스트림 개방
                            BufferedReader reader = null;

                            is = conn.getInputStream();
                            reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));  //문자열 셋 세팅
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


                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(reViewActivity.this);
                alert_confirm.setMessage("소중한 리뷰 감사합니다 메인으로 이동합니다.").setCancelable(false).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 'YES'
                                Intent intent = new Intent(reViewActivity.this,HotelDetailActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                            }
                        });
                AlertDialog alert = alert_confirm.create();
                alert.show();



            }
        });

    }

}
