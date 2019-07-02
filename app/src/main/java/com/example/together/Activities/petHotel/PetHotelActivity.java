package com.example.together.Activities.petHotel;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.together.Activities.HomeActivity;
import com.example.together.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class PetHotelActivity extends AppCompatActivity {

    //이미지 배너 슬라이드
    ViewFlipper viewFlipper;
    int mYear, mMonth, mDay;

    ImageButton imageButton;

    TextView mCheckin; //체크인 숫자 뷰
    TextView mCheckout; // 체크아웃 숫자 뷰

    EditText Search;
    ImageButton backTo;

    Button HotelSearchBtn;

    //전체값 정렬;
    String First;
    String Last;
    String AddVal;

    //지역명 체크를 위한 지역 배열변수
    String[] AddressList = new String[] {"대구","대전","서울","부산","인천"};





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_hotel);

        //이미지 배너 슬라이드
        int images[] = {R.drawable.hb1, R.drawable.hb2 , R.drawable.hb3};

        viewFlipper = findViewById(R.id.v_flipper);
        //루프
//        for(int i=0; i<images.length; i++){
//            flipperImages(imges[i]);
//        }

        for(int image: images){
            flipperImages(image);
        }
        //이미지 배너 슬라이드


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


        mCheckin = (TextView) findViewById(R.id.Checkin);

        mCheckout = (TextView) findViewById(R.id.Checkout);


        //현재 날짜와 시간을 가져오기위한 Calendar 인스턴스 선언

        Calendar cal = new GregorianCalendar();

        mYear = cal.get(Calendar.YEAR);

        mMonth = cal.get(Calendar.MONTH);

        mDay = cal.get(Calendar.DAY_OF_MONTH);


        mCheckin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(PetHotelActivity.this, mDateSetListener, mYear
                        ,mMonth,mDay).show();
            }

        });

        mCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PetHotelActivity.this, mDateSetListener2, mYear
                        ,mMonth,mDay).show();
            }
        });
//        이곳까지는 달력 기능








        Search = findViewById(R.id.Search);
//지도버튼 클릭
//        AddressBtn = findViewById(R.id.etp_name);

//        AddressBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        HotelSearchBtn = findViewById(R.id.HotelSearch);

        HotelSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                First = mCheckin.getText().toString();
                Last = mCheckout.getText().toString();
                AddVal = Search.getText().toString();

                Toast.makeText(PetHotelActivity.this, First + Last + AddVal, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(PetHotelActivity.this, HotelListDataActivity.class);
                intent.putExtra("First_day", First);
                intent.putExtra("Last_day", Last);
                intent.putExtra("AddVal", AddVal);


                startActivity(intent);

            }
        });


//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                s = tv1.getText().toString();
//                Toast.makeText(PetHotelActivity.this, s, Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(PetHotelActivity.this, LoginActivity.class);
//                startActivity(intent);
//
//            }
//        });




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






    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;

            UpdateNow1();
        }
    };

    DatePickerDialog.OnDateSetListener mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;

            UpdateNow2();
        }
    };


    void UpdateNow1(){

        mCheckin.setText(String.format("%d/%d/%d", mYear, mMonth + 1, mDay));


    }

    void  UpdateNow2(){
        mCheckout.setText(String.format("%d/%d/%d", mYear, mMonth + 1, mDay));
    }

}



