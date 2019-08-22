package com.example.together.activities.petHotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.together.activities.HomeActivity;
import com.example.together.activities.petHospital.PetHospitalPetConditionActivity;
import com.example.together.adapter.ReviewAdapter;
import com.example.together.model.Review;
import com.example.together.R;

import java.util.ArrayList;

public class totalreviewActivity extends AppCompatActivity {


    //이전페이지에서 업체코드 받아오기
    String list;

    //툴바
    Toolbar myToolbar;

    //이전에 넘겨온 VO배열 변수를 받기위해 선언
    private ArrayList<Review> reviewlist;

    //Listview 설정
    ListView totalListview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totalreview);


        //뷰에잇는 변수 선언
        totalListview = findViewById(R.id.totalListview);



        //이전값 받아오기
        reviewlist = (ArrayList<Review>) getIntent().getSerializableExtra("array");




        //툴바 선언
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        //액션바 왼쪽에 버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_back);
        getSupportActionBar().setTitle("");


        ReviewAdapter reAdapter = new ReviewAdapter(reviewlist);

        totalListview.setAdapter(reAdapter);



    }



    //액션바 클릭 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(totalreviewActivity.this, HotelDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
