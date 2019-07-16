package com.example.together.activities.goodbyePet;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.together.R;

import java.util.Calendar;

public class GoodbyePetTimeDateSelectActivity extends AppCompatActivity {

    public static Button date, time, check;
    public static TextView set_date, set_time;
    public static final int Date_id=0;
    public static final int Time_id =1;

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
                    choice_do = "서울특별시"; //버튼 클릭시 출력을 위해 값을 넣음
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
                    choice_do = "대구광역시";
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
                    choice_do = "부산광역시";
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
                    choice_do = "인천광역시";
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
                    choice_do = "광주광역시";
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
                    choice_do = "대전광역시";
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
                    choice_do = "울산광역시";
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
                    choice_do = "세종특별자치시";
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
                    choice_do = "충청북도";
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
                    choice_do = "충청남도";
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
                    choice_do = "전라북도";
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
                    choice_do = "전라남도";
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
                    choice_do = "경상북도";
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
                    choice_do = "경상남도";
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
                    choice_do = "제주특별자치도";
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
//                    String addr = (String) tvAddr.getText();

                    Intent intent = new Intent(GoodbyePetTimeDateSelectActivity.this, com.example.together.activities.goodbyePet.GoodbyePetStoreListActivity.class);
                    intent.putExtra("day",day);
                    intent.putExtra("time",time);
//                    intent.putExtra("addr",addr);
                    startActivity(intent);
                }

            }
        });



    }
    //    public void flipperImages(int image){
//        ImageView imageview = new ImageView(this);
//        imageview.setBackgroundResource(image);
//
//        viewFlipper.addView(imageview);
//        viewFlipper.setFlipInterval(4000); //4초
//        viewFlipper.setAutoStart(true);
//
//        //애니메이션
//        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
//        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);
//
//    }
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
