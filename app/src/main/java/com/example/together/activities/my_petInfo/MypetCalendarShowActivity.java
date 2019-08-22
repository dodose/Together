package com.example.together.activities.my_petInfo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.together.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
import java.util.Calendar;
import java.util.TimeZone;

public class MypetCalendarShowActivity extends AppCompatActivity {

    //dialog변수
    public static final int Date_id=0;
    public static final int Time_id =1;
    public static final int Date_id2=2;
    public static final int Time_id2=3;

    //private Task 변수
    private String strUrl;
    private URL Url;

    //json
    JSONObject jobj;


    //view에있는 변수들
    Button cancel,submit;
    TextView Days,Times,Days2,Times2;
    EditText content;

    //Intent받아온 변수
    String Excontent,Extime,Extime2;

    //접속중 유저
    FirebaseUser mUser;
    String userUid;

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypet_calendar_show);


        mUser = FirebaseAuth.getInstance().getCurrentUser();
        userUid = mUser.getEmail();

        Intent intent = getIntent();

        Excontent = intent.getExtras().getString("Content");
        Extime = intent.getExtras().getString("Time");
        Extime2 = intent.getExtras().getString("Time2");

        Log.e("time",Extime);
        Log.e("time2",Extime2);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //액션바 왼쪽에 버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_back);
        getSupportActionBar().setTitle("");

        submit = findViewById(R.id.submit);
        cancel = findViewById(R.id.cancel);

        content = findViewById(R.id.content_view);

        String stime[] = Extime.split(" ");
        String stime2[] = Extime2.split(" ");

        //focus될 viewText
        Days = findViewById(R.id.Days);
        Times = findViewById(R.id.Times);
        Days2 = findViewById(R.id.Days2);
        Times2 = findViewById(R.id.Times2);

        Days.setText(stime[0]);
        Times.setText(stime[1]);
        Days2.setText(stime2[0]);
        Times2.setText(stime2[1]);

        content.setText(Excontent);
        //내용 체크
        content.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
                if(content.getText().length() > 15){
                    content.setTextSize(15);
                }

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
            }

        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cont = content.getText().toString();
                if(cont != null) {
                    cont = content.getText().toString();
                }else{
                    Toast.makeText(MypetCalendarShowActivity.this, "내용을 적어주세요!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String fir = Days.getText().toString()+" "+Times.getText().toString();
                String la = Days2.getText().toString()+" "+Times2.getText().toString();
                //userUid
                //petUid
                JSONObject Sendobj = new JSONObject();
                try {

                    Sendobj.put("Excontent",Excontent);
                    Sendobj.put("ExTime",Extime);
                    Sendobj.put("ExTime2",Extime2);
                    Sendobj.put("content",cont);
                    Sendobj.put("first",fir);
                    Sendobj.put("last",la);
                    Sendobj.put("userUid",userUid);
                    Sendobj.put("Type","1");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                DBconnets(Sendobj);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MypetCalendarShowActivity.this);
                alert_confirm.setMessage("일정을 삭제하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 'YES'
                                String cont = content.getText().toString();
                                String fir = Days.getText().toString()+" "+Times.getText().toString();
                                String la = Days2.getText().toString()+" "+Times2.getText().toString();
                                //userUid
                                //petUid
                                JSONObject Sendobj = new JSONObject();
                                try {
                                    Sendobj.put("content",Excontent);
                                    Sendobj.put("first",Extime);
                                    Sendobj.put("last",Extime2);
                                    Sendobj.put("userUid",userUid);
                                    Sendobj.put("Type","2");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                DBconnets(Sendobj);

                            }
                        }).setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 'No'
                                return;
                            }
                        });
                AlertDialog alert = alert_confirm.create();
                alert.show();
            }
        });

        Days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Date_id);
            }
        });

        Times.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Time_id);
            }
        });

        Days2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Date_id2);
            }
        });

        Times2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Time_id2);
            }
        });


    }

    protected Dialog onCreateDialog(int id) {

        // Get the calander
        Calendar c = Calendar.getInstance();

        // From calander get the year, month, day, hour, minute
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        switch (id) {


            case Date_id:

                // Open the datepicker dialog
                return new DatePickerDialog(MypetCalendarShowActivity.this, date_listener, year,
                        month, day);
            case Time_id:

                // Open the timepicker dialog
                return new TimePickerDialog(MypetCalendarShowActivity.this, time_listener, hour,
                        minute, false);

            case Date_id2:
                return new DatePickerDialog(MypetCalendarShowActivity.this, date_listener2, year,
                        month, day);

            case Time_id2:
                return new TimePickerDialog(MypetCalendarShowActivity.this, time_listener2, hour,
                        minute, false);
        }
        return null;
    }

    // Date picker dialog
    DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // store the data in one string and set it to text
            String date1 = String.format("%02d-%02d-%02d",year,month+1,day);
            Days.setText(date1);
            Days2.setText(date1);
        }
    };

    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            String time1 = String.format("%02d:%02d",hour,minute);
            Times.setText(time1);
            Times2.setText(time1);
        }
    };

    // Date picker dialog
    DatePickerDialog.OnDateSetListener date_listener2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // store the data in one string and set it to text
            String date1 = String.format("%02d-%02d-%02d",year,month+1,day);
            Days2.setText(date1);
        }
    };

    TimePickerDialog.OnTimeSetListener time_listener2 = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            String time1 = String.format("%02d:%02d",hour,minute);

            Times2.setText(time1);
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(MypetCalendarShowActivity.this,MypetWriteCalendarActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }



    }

    public void DBconnets(JSONObject Sendobj){

        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                strUrl = "http://13.209.25.83:8080/UpdateCalendar"; //탐색하고 싶은 URL이다.

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

                    wr.write(Sendobj.toString());

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

                try {

                    if(jobj.getString("result").equals("update")){

                        Toast.makeText(MypetCalendarShowActivity.this, "수정완료", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        setResult(2,intent);
                        finish();

                    }else{

                        Toast.makeText(MypetCalendarShowActivity.this, "삭제완료", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        setResult(2,intent);
                        finish();


                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }.execute();

    }


}
