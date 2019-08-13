package com.example.together.activities.goodbyePet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.together.R;
import com.example.together.activities.HomeActivity;
import com.example.together.activities.petHotel.HotelDetailActivity;
import com.example.together.activities.petHotel.reViewActivity;

public class GoodbyeSelectActivity extends AppCompatActivity {

    ImageView goto_rainbow_search, goto_goodbypet_gallery;
    ViewFlipper viewFlipper;
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodbye_select);

        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_back);
        getSupportActionBar().setTitle("");

        int images[] = {R.drawable.o1, R.drawable.o2, R.drawable.o3, R.drawable.o4, R.drawable.o5, R.drawable.o6, R.drawable.o7};
        //이미지 슬라이드임ㅇㅇ
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

    //액션바 등록
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true ;
    }


    //액션바 클릭 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(GoodbyeSelectActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void flipperImages(int image) {
        ImageView imageview = new ImageView(this);
        imageview.setBackgroundResource(image);

        viewFlipper.addView(imageview);
        viewFlipper.setFlipInterval(3000); //4초
        viewFlipper.setAutoStart(true);

        //애니메이션
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
        //이미지 슬라이드임ㅇㅇ


        goto_rainbow_search = findViewById(R.id.goto_rainbow_search);
        goto_goodbypet_gallery = findViewById(R.id.goto_goodbypet_gallery);
//        goodbyePet_how_to_use = findViewById(R.id.goodbyePet_how_to_use);

        goto_rainbow_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodbyeSelectActivity.this, GoodbyePetTimeDateSelectActivity.class);
                startActivity(intent);
            }
        });


        goto_goodbypet_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodbyeSelectActivity.this, GoodbyeMemorialActivity.class);
                startActivity(intent);
            }
        });


//        goodbyePet_how_to_use.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(GoodbyeSelectActivity.this, GoodbyeHowActivity.class );
//                startActivity(intent);
//            }
//        });


    }


}
