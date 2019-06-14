package com.example.together.Activities.GoodbyePet;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.together.R;

import java.util.Calendar;

public class GoodbyePetTimeDateSelectActivity extends AppCompatActivity {

    ViewFlipper viewFlipper;
    public static Button date, time, check;
    public static TextView set_date, set_time;
    public static final int Date_id=0;
    public static final int Time_id =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodbye_pet_time_date_select);

        int images[] = {R.drawable.b1, R.drawable.b2 , R.drawable.b3};

        viewFlipper = findViewById(R.id.v_flipper);
        //루프
//        for(int i=0; i<images.length; i++){
//            flipperImages(imges[i]);
//        }

        for(int image: images){
            flipperImages(image);
        }

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
                    Intent intent = new Intent(GoodbyePetTimeDateSelectActivity.this, GoodbyePetStoreListActivity.class);
                    startActivity(intent);
                }

            }
        });



    }
    public void flipperImages(int image){
        ImageView imageview = new ImageView(this);
        imageview.setBackgroundResource(image);

        viewFlipper.addView(imageview);
        viewFlipper.setFlipInterval(4000); //4초
        viewFlipper.setAutoStart(true);

        //애니메이션
        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);

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
            String date1 = String.valueOf(month) + "/" + String.valueOf(day)
                    + "/" + String.valueOf(year);
            set_date.setText(date1);
        }
    };

    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
            set_time.setText(time1);
        }
    };



}
