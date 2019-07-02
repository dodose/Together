package com.example.together.Activities.goodbyePet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.example.together.adapter.f_productAdapter;
import com.example.together.model.FuneralProdcutOrder;
import com.example.together.R;

import java.util.ArrayList;

public class GoodbyepetReservationResultActivity extends AppCompatActivity {

//    private TextView tvParent, tvChild;


    Toolbar myToolbar;

    ListView funeralview;

    Button Btn;

    private ArrayList<FuneralProdcutOrder> f_order = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_goodbyepet_reservation_result);


            int total_price = 0;
            //리스트뷰선언
        funeralview = findViewById(R.id.funeral_order);

        //예약 버튼

        Btn = findViewById(R.id.f_orderBtn);

        //툴바 선언
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //액션바 왼쪽에 버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


            for (int i = 0; i < MyCategoriesExpandableListAdapter.parentItems.size(); i++ ) {

                for (int j = 0; j < MyCategoriesExpandableListAdapter.childItems.get(i).size(); j++ ){

                    String isChildChecked = MyCategoriesExpandableListAdapter.childItems.get(i).get(j).get(ConstantManager.Parameter.IS_CHECKED);

                    if (isChildChecked.equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE)) {

//                        tvParent.setText(tvParent.getText() + MyCategoriesExpandableListAdapter.childItems.get(i).get(j).get(ConstantManager.Parameter.SUB_CATEGORY_NAME));
//                        tvChild.setText(tvChild.getText() + MyCategoriesExpandableListAdapter.childItems.get(i).get(j).get(ConstantManager.Parameter.SUB_CATEGORY_PRICE));
                        String name =  MyCategoriesExpandableListAdapter.childItems.get(i).get(j).get(ConstantManager.Parameter.SUB_CATEGORY_NAME);
                        String price = MyCategoriesExpandableListAdapter.childItems.get(i).get(j).get(ConstantManager.Parameter.SUB_CATEGORY_PRICE);
                        String img = MyCategoriesExpandableListAdapter.childItems.get(i).get(j).get(ConstantManager.Parameter.SUB_CATEGORY_IMAGE);

                            f_order.add(new FuneralProdcutOrder(name,price,img));
                            total_price = total_price + Integer.parseInt(price);


                    }

                }

            }

            f_productAdapter orAdapter = new f_productAdapter(f_order);
//            orAdapter.setHasStableId(true);
            Log.e("view activiey", f_order.size()+"안녕");
            funeralview.setAdapter(orAdapter);

            Btn.setText(f_order.size()+"개   " + total_price+" 원 예약 신청하기");





            Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });


    }

    //액션바 등록
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.alldelete_manu, menu) ;

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
