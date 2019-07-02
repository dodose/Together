package com.example.together.activities.petHotel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.together.adapter.ReviewAdapter;
import com.example.together.model.Review;
import com.example.together.R;

import java.util.ArrayList;

public class totalreviewActivity extends AppCompatActivity{


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


    //액션바 등록
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hotel_menu, menu) ;

        return true ;
    }


    //액션바 클릭 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //                ((TextView)findViewById(R.id.textView)).setText("SEARCH") ;
                return true;
            case R.id.settings:
                //                ((TextView)findViewById(R.id.textView)).setText("ACCOUNT") ;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
