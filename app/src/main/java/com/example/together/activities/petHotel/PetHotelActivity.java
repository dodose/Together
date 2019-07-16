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

import java.util.Calendar;
import java.util.GregorianCalendar;

public class PetHotelActivity extends AppCompatActivity {

    //이미지 배너 슬라이드
    ViewFlipper viewFlipper;
    int mYear, mMonth, mDay;

    ImageButton imageButton;

    TextView date;

    EditText location;
    ImageButton backTo;

    Button HotelSearchBtn;

    //전체값 정렬;

    String AddVal;


    //지역명 체크를 위한 지역 배열변수
    String[] AddressList = new String[] {"대구","대전","서울","부산","인천"};





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_hotel);

        //텍스트뷰 2개 연결

        backTo = (ImageButton) findViewById(R.id.backTo);

        backTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(PetHotelActivity.this, HomeActivity.class);
                intent.putExtra("flag","flag");
                startActivity(intent);

            }
        });


        date = findViewById(R.id.date);
        location = findViewById(R.id.location);





        //현재 날짜와 시간을 가져오기위한 Calendar 인스턴스 선언

        Calendar cal = new GregorianCalendar();

        mYear = cal.get(Calendar.YEAR);

        mMonth = cal.get(Calendar.MONTH);

        mDay = cal.get(Calendar.DAY_OF_MONTH);

        HotelSearchBtn = findViewById(R.id.searchBtn);

        HotelSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dd = date.getText().toString();

                String split[] = dd.split("~");
                AddVal = location.getText().toString();


//                Toast.makeText(PetHotelActivity.this, First + Last + AddVal, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(PetHotelActivity.this, HotelListDataActivity.class);
                intent.putExtra("First_day", split[0]);
                intent.putExtra("Last_day", split[1]);
                intent.putExtra("AddVal", AddVal);


                startActivity(intent);

            }
        });




    }

}




