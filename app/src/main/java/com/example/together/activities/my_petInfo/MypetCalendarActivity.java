package com.example.together.activities.my_petInfo;


import android.content.Intent;
import android.graphics.Color;

import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.R;
import com.example.together.activities.decorators.EventDecorator;
import com.example.together.activities.decorators.OneDayDecorator;
import com.example.together.activities.decorators.SaturdayDecorator;
import com.example.together.activities.decorators.SundayDecorator;
import com.example.together.adapter.CalendarSetAdapter;
import com.example.together.model.CalendarData;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;


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
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;


public class MypetCalendarActivity extends AppCompatActivity{

    String petUid,petName;

    TextView petname,gone;

    private String strUrl;
    private URL Url;

    JSONObject jobj;

    MaterialCalendarView materialCalendarView;
    Toolbar mToolbar;

    // 상품 목록 리사이클 뷰 선언
    RecyclerView mRecycleView;
    RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<CalendarData> selectDay;

    //최초 월 배열
    public static ArrayList<CalendarData> CalMark;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();

    //Day
    String Day = null;

//횟수체크용
int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypet_calendar);

        Bundle Bx = getIntent().getExtras();

        if (Bx != null) {

            petUid = Bx.getString("petUid");
            petName = Bx.getString("petName");

        }


        gone = findViewById(R.id.gone);


        petname = findViewById(R.id.petname);
        petname.setText(petName+" 다이어리");

        //recyclerview 선언
        mRecycleView = findViewById(R.id.calendar_recycler);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);


        //툴바 선언
        mToolbar = findViewById(R.id.cal_toolbar);
        setSupportActionBar(mToolbar);


        //툴바 액션바 왼쪽에 버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_back);
        getSupportActionBar().setTitle("");


        materialCalendarView = findViewById(R.id.calendarView);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1))
                .setMaximumDate(CalendarDay.from(2030, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                oneDayDecorator);



        setChangeDayMark(petUid);

        //////////////// 처음 월 리스트에서 일정정보를다 가져와야하기때문에 정보를 다긁어와야함 처음부터 ////////////////////



        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Month = date.getMonth() + 1;

                if(Month < 10) {
                    if(date.getDay() < 10) {
                        Day = date.getYear() + "-0" + Month + "-0" + date.getDay();
                    }else{
                        Day = date.getYear() + "-0" + Month + "-" + date.getDay();
                    }
                }else {
                    if(date.getDay() < 10) {
                        Day = date.getYear() + "-" + Month + "-0" + date.getDay();
                    }else{
                        Day = date.getYear() + "-" + Month + "-" + date.getDay();
                    }
                }

                Log.e("1", Day + "");


                if(CalMark.size() != 0){

                    Log.e("2", CalMark + "");

                    selectDay = new ArrayList<>();

                    for (int i = 0; i < CalMark.size(); i++) {
                        if (Day.trim().equals(CalMark.get(i).setTime1.substring(0, 10).trim())) {

                            String time1 = CalMark.get(i).setTime1;
                            String time2 = CalMark.get(i).setTime2;
                            String type = CalMark.get(i).setType;
                            String content = CalMark.get(i).setContent;

                            selectDay.add(new CalendarData(time1, time2, type, content));
                        }
                    }
                    resetAdapter();
                }
//
                //reset




            }


        });


    }

    private void resetAdapter() {
        CalendarSetAdapter mAdapter = new CalendarSetAdapter(selectDay);
        mRecycleView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        ArrayList<CalendarData> Time_Result;

        ApiSimulator(ArrayList<CalendarData> list){
            this.Time_Result = list;
        }


        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {

            Log.e("timeResult",Time_Result+"");

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();

            /*특정날짜 달력에 점표시해주는곳*/
            /*월은 0이 1월 년,일은 그대로*/
            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환
            for(int i = 0 ; i < Time_Result.size() ; i ++){
                String SubStringTime = Time_Result.get(i).setTime1.substring(0,10);
                Log.e("sub",SubStringTime);
                String[] time = SubStringTime.split("-");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);


                calendar.set(year,month-1,dayy);
                //날짜는 원래대로 나오는데 calendar 계산식은 -1을 해줘야 제대로나옴 유의할것

                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
                Log.e("date",dates+"");

            }


            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            Log.e("dd",calendarDays+"");

            if (isFinishing()) {
                return;
            }

            materialCalendarView.addDecorator(new EventDecorator(Color.GREEN, calendarDays,MypetCalendarActivity.this));

        }
    }


    //액션바 등록
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendar_manu, menu) ;

        return true ;
    }


    //액션바 클릭 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                //                Intent intent = new Intent(ProductOrderActivity.this,HotelDetailActivity.class);
                //                startActivity(intent);
                return true;
            case R.id.show_add:

                Intent intent = new Intent(MypetCalendarActivity.this, MypetWriteCalendarActivity.class);
                intent.putExtra("Day",Day);
                intent.putExtra("petUid",petUid);
                startActivityForResult(intent,1);

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RESULT_FIRST_USER){
            switch (resultCode){
                // MainActivity 에서 요청할 때 보낸 요청 코드 (3000)
                case 1:
                    Toast.makeText(this, "작성완료", Toast.LENGTH_SHORT).show();
                    setChangeDayMark(petUid);

                    break;
            }
        }
    }


    public void setChangeDayMark(String petUid) {
        //서블릿 통신
        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                strUrl = "http://39.127.7.80:8080/CalendarList"; //탐색하고 싶은 URL이다.

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

                    wr.write(petUid);

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

                Log.e("들어옴",count+"");
                count++;
                CalMark = new ArrayList<>();

                try {
                    CalMark.clear();
                    JSONArray jsonArray = (JSONArray) jobj.get("result");

                    Log.e("jsonarry",jsonArray+"");
                    if(jsonArray.length() != 0) {

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String time1 = jsonObject.optString("settime1");
                                String time2 = jsonObject.optString("settime2");
                                String type = jsonObject.optString("Type");
                                String content = jsonObject.optString("Content");

                                CalMark.add(new CalendarData(time1, time2, type, content));

                            }

                                new ApiSimulator(CalMark).executeOnExecutor(Executors.newSingleThreadExecutor());

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }.execute();
    }


}
