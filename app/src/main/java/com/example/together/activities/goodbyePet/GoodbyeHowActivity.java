package com.example.together.activities.goodbyePet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.together.R;

public class GoodbyeHowActivity extends AppCompatActivity {
    ViewFlipper viewFlipper;
    ImageButton activity_goodbye_how_databackbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodbye_how);

        int images[] = {R.drawable.o1, R.drawable.o2, R.drawable.o3,R.drawable.o4,R.drawable.o5,R.drawable.o6,R.drawable.o7};

        activity_goodbye_how_databackbtn = findViewById(R.id.activity_hotel_list_databackbtn);  //뒤로 가기 버튼




        viewFlipper = findViewById(R.id.v_flipper);
        //루프
//        for(int i=0; i<images.length; i++){
//            flipperImages(imges[i]);
//        }

        for (int image : images) {
            flipperImages(image);
        }
        //이미지 배너 슬라이드
    }
    public void flipperImages(int image){
        ImageView imageview = new ImageView(this);
        imageview.setBackgroundResource(image);

        viewFlipper.addView(imageview);
        viewFlipper.setFlipInterval(3000); //4초
        viewFlipper.setAutoStart(true);

        //애니메이션
        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);

    }
    }
