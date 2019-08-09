package com.example.together.activities.petHotel;

import android.app.DatePickerDialog;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.together.activities.HomeActivity;
import com.example.together.R;
import com.example.together.activities.aircalendar.AirCalendarDatePickerActivity;
import com.example.together.activities.aircalendar.core.AirCalendarIntent;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class PetHotelActivity extends AppCompatActivity {

    public final static int REQUEST_CODE = 1;
    int mYear, mMonth, mDay;

    TextView date;

    TextView location;

    Button HotelSearchBtn;

    //전체값 정렬;

    String AddVal;
    String AddDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_hotel);

        //텍스트뷰 2개 연결

        date = findViewById(R.id.date);
        location = findViewById(R.id.location);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AirCalendarIntent intent = new AirCalendarIntent(PetHotelActivity.this);
                intent.setSelectButtonText("Select");
                intent.setResetBtnText("Reset");
                intent.setWeekStart(Calendar.MONDAY);
//                intent.putExtra("pet_hospital",-1);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(PetHotelActivity.this,HotelLocationSelect.class);
                    startActivityForResult(intent,2);
            }
        });


        HotelSearchBtn = findViewById(R.id.searchBtn);

        HotelSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String split[] = AddDate.split("~");
                AddVal = location.getText().toString();
                Intent intent = new Intent(PetHotelActivity.this, HotelListDataActivity.class);
                intent.putExtra("First_day", split[0]);
                intent.putExtra("Last_day", split[1]);
                intent.putExtra("AddVal", AddVal);

                startActivity(intent);

            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                Log.e("requestCode",requestCode+"");
                Log.e("resultCode",resultCode+"");

            switch (resultCode){

                case RESULT_OK:
                    date.setText(data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE).substring(5)+"~"+data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE).substring(5));
                    AddDate = data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE)+"~"+data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE);
                    break;
                case 2:{
                    location.setText(data.getExtras().getString("result"));
                    break;
                }

            }


        }


}




