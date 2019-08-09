package com.example.together.activities.petHotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.together.R;
import com.example.together.fragment.locationFrag1;
import com.example.together.fragment.locationFrag2;

import org.w3c.dom.Text;

public class HotelLocationSelect extends AppCompatActivity {

    Toolbar AddressAndSerch;
    EditText changeText;
    TextView changeView;
    locationFrag1 fragment1;
    Context mContext;

    static String finalAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_location_select);

        AddressAndSerch = findViewById(R.id.AddressAndSearch);
        changeText = findViewById(R.id.edit); // 검색 구문
        changeView = findViewById(R.id.textview); // 기본구문

        //프래그먼트를 보여주기
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentin, fragment1.newInstance()).commit();


        //툴바 액션바 왼쪽에 버튼
        setSupportActionBar(AddressAndSerch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_back);
        getSupportActionBar().setTitle("");



    }

    public void replaceFragment(Fragment fragment, String addressName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentin, fragment).commit();      // Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
        locationFrag2.localreplace(addressName);
    }


    //액션바 등록
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_manu, menu) ;

        return true ;
    }


    //액션바 클릭 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                if (changeText.getVisibility() == View.VISIBLE){
                    changeView.setVisibility(View.VISIBLE);
                    changeText.setVisibility(View.GONE);
                }else{
                    //뒤로가기버튼
                }
                return true;
            case R.id.search:
                changeText.setVisibility(View.VISIBLE);
                changeView.setVisibility(View.GONE);


                return true;

            default:
                return super.onOptionsItemSelected(item);

        }



    }
}
