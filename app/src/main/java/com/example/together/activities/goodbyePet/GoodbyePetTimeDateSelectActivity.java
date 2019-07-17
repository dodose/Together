package com.example.together.activities.goodbyePet;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.R;
import com.example.together.adapter.RecommentAdapter;
import com.example.together.model.Recommend;

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
import java.util.Calendar;

public class GoodbyePetTimeDateSelectActivity extends AppCompatActivity {

    public static Button date, time, check;
    public static TextView set_date, set_time;
    public static final int Date_id=0;
    public static final int Time_id =1;

    RecyclerView mRecycleView;
    RecyclerView.LayoutManager mLayoutManager;

    private String strUrl;
    private URL Url;

    JSONObject jobj;

    //Spinner 변수 추가
    ArrayAdapter<CharSequence> adspin1, adspin2; //어댑터를 선언
    String choice_do="";
    String choice_se="";
    //Spinner 변수 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodbye_pet_time_date_select);


        check = (Button) findViewById(R.id.check);
        date = (Button) findViewById(R.id.selectdate);
        time = (Button) findViewById(R.id.selecttime);
        set_date = (TextView) findViewById(R.id.set_date);
        set_time = (TextView) findViewById(R.id.set_time);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // Show Date dialog
                showDialog(Date_id);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Show time dialog
                showDialog(Time_id);
            }
        });

        // Spinner 값 --------------------
        // 1. 다량의 데이터
        // 2. Adapter
        // 3. AdapterView : Spinner

        // city 와 gu 를 담을 두개의 Spinner 객체
        final Spinner spin1 = (Spinner) findViewById(R.id.spinner1);   // 이게 도시 선택
        final Spinner spin2 = (Spinner) findViewById(R.id.spinner2);   //이게 시/군/구 선택

        adspin1 = ArrayAdapter.createFromResource(this, R.array.spinner_do,
                android.R.layout.simple_spinner_dropdown_item);

        adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin1.setAdapter(adspin1);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adspin1.getItem(i).equals("서울특별시")) {
                    choice_do = "서울"; //버튼 클릭시 출력을 위해 값을 넣음
                    adspin2 = ArrayAdapter.createFromResource(GoodbyePetTimeDateSelectActivity.this, R.array.spinner_do_seoul,
                            android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            //아무것도 선택 안했을때 부분, 자동완성됨
                        }
                    });
                }else if(adspin1.getItem(i).equals("대구광역시")) {
                    choice_do = "대구";
                    adspin2 = ArrayAdapter.createFromResource(GoodbyePetTimeDateSelectActivity.this,
                            R.array.spinner_do_daegu, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else if(adspin1.getItem(i).equals("부산광역시")) {
                    choice_do = "부산";
                    adspin2 = ArrayAdapter.createFromResource(GoodbyePetTimeDateSelectActivity.this,
                            R.array.spinner_do_busan, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else if(adspin1.getItem(i).equals("인천광역시")) {
                    choice_do = "인천";
                    adspin2 = ArrayAdapter.createFromResource(GoodbyePetTimeDateSelectActivity.this,
                            R.array.spinner_do_Incheon, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else if(adspin1.getItem(i).equals("광주광역시")) {
                    choice_do = "광주";
                    adspin2 = ArrayAdapter.createFromResource(GoodbyePetTimeDateSelectActivity.this,
                            R.array.spinner_do_Gwangju, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else if(adspin1.getItem(i).equals("대전광역시")) {
                    choice_do = "대전";
                    adspin2 = ArrayAdapter.createFromResource(GoodbyePetTimeDateSelectActivity.this,
                            R.array.spinner_do_Daejeon, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else if(adspin1.getItem(i).equals("울산광역시")) {
                    choice_do = "울산";
                    adspin2 = ArrayAdapter.createFromResource(GoodbyePetTimeDateSelectActivity.this,
                            R.array.spinner_do_Ulsan, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else if(adspin1.getItem(i).equals("세종특별자치시")) {
                    choice_do = "세종시";
                    adspin2 = ArrayAdapter.createFromResource(GoodbyePetTimeDateSelectActivity.this,
                            R.array.spinner_do_Sejong_Special, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else if(adspin1.getItem(i).equals("경기도")) {
                    choice_do = "경기도";
                    adspin2 = ArrayAdapter.createFromResource(GoodbyePetTimeDateSelectActivity.this,
                            R.array.spinner_do_Gyeonggido, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else if(adspin1.getItem(i).equals("강원도")) {
                    choice_do = "강원도";
                    adspin2 = ArrayAdapter.createFromResource(GoodbyePetTimeDateSelectActivity.this,
                            R.array.spinner_do_Gangwondo, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else if(adspin1.getItem(i).equals("충청북도")) {
                    choice_do = "충북";
                    adspin2 = ArrayAdapter.createFromResource(GoodbyePetTimeDateSelectActivity.this,
                            R.array.spinner_do_Chungcheongbukdo, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else if(adspin1.getItem(i).equals("충청남도")) {
                    choice_do = "충남";
                    adspin2 = ArrayAdapter.createFromResource(GoodbyePetTimeDateSelectActivity.this,
                            R.array.spinner_do_Chungcheongnamdo, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else if(adspin1.getItem(i).equals("전라북도")) {
                    choice_do = "전북";
                    adspin2 = ArrayAdapter.createFromResource(GoodbyePetTimeDateSelectActivity.this,
                            R.array.spinner_do_Jeollabukdo, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else if(adspin1.getItem(i).equals("전라남도")) {
                    choice_do = "전남";
                    adspin2 = ArrayAdapter.createFromResource(GoodbyePetTimeDateSelectActivity.this,
                            R.array.spinner_do_Jeollanamdo, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else if(adspin1.getItem(i).equals("경상북도")) {
                    choice_do = "경북";
                    adspin2 = ArrayAdapter.createFromResource(GoodbyePetTimeDateSelectActivity.this,
                            R.array.spinner_do_Gyeongsangbukdo, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else if(adspin1.getItem(i).equals("경상남도")) {
                    choice_do = "경남";
                    adspin2 = ArrayAdapter.createFromResource(GoodbyePetTimeDateSelectActivity.this,
                            R.array.spinner_do_Gyeongsangnamdo, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else if(adspin1.getItem(i).equals("제주특별자치도")) {
                    choice_do = "제주도";
                    adspin2 = ArrayAdapter.createFromResource(GoodbyePetTimeDateSelectActivity.this,
                            R.array.spinner_do_JejuSpecial, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
            }//end of onItemSelected

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        //확인 클릭시 등록된 업체 스토어리스트로 이동
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 시간 날짜 입력되었는지 확인한 후에 입력하였으면 넘기기
                if(set_date==null){
                    Toast.makeText(GoodbyePetTimeDateSelectActivity.this, "날짜를 입력해주세요", Toast.LENGTH_SHORT).show();

                }else if(set_time==null){
                    Toast.makeText(GoodbyePetTimeDateSelectActivity.this, "시간을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{

                    String day = (String) set_date.getText();
                    String time = (String) set_time.getText();
                    String addr = choice_do+" "+choice_se;

                    Intent intent = new Intent(GoodbyePetTimeDateSelectActivity.this, com.example.together.activities.goodbyePet.GoodbyePetStoreListActivity.class);
                    intent.putExtra("day",day);
                    intent.putExtra("time",time);
                    intent.putExtra("addr",addr);
                    startActivity(intent);
                }

            }
        });

        //recyclerview 선언
        mRecycleView = findViewById(R.id.recommend_recycler);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);

        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                strUrl = "http://39.127.7.80:8080/Recommend_list"; //탐색하고 싶은 URL이다.

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

                    wr.write("완료");

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


                ArrayList<Recommend> Recommendlist = new ArrayList<Recommend>();

                try {
                    JSONArray jsonArray = (JSONArray) jobj.get("result");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String mName = jsonObject.optString("etp_nm");
                        String mAddr = jsonObject.optString("etp_addr");
                        String mTime = jsonObject.optString("etp_time1") + "~" + jsonObject.optString("etp_time2");
                        String img_path = jsonObject.optString("etp_image");
                        String reviewcount =jsonObject.optString("reviewcount");
                        String reviewavg = jsonObject.optString("reviewavg");



                        Recommendlist.add(new Recommend(mName,mAddr,mTime,img_path,reviewcount,reviewavg));

                    }


                    //넣은 리스트를 리사이클 뷰를 통해 실행;
                   RecommentAdapter myAdapter = new RecommentAdapter(Recommendlist);

                    mRecycleView.setAdapter(myAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }.execute();

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
                return new DatePickerDialog(GoodbyePetTimeDateSelectActivity.this, date_listener, year,
                        month, day);
            case Time_id:

                // Open the timepicker dialog
                return new TimePickerDialog(GoodbyePetTimeDateSelectActivity.this, time_listener, hour,
                        minute, false);

        }
        return null;
    }

    // Date picker dialog
    DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // store the data in one string and set it to text
            String date1 = String.valueOf(year) + "/" + String.valueOf(month+1)
                    + "/" + String.valueOf(day);
            set_date.setText(date1);
        }
    };

    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            String time1 = String.valueOf(hour) + ":00";
            set_time.setText(time1);
        }
    };



}
