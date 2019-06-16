package com.example.together.Activities.GoodbyePet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.together.R;

public class GoodbyepetReservationResultActivity extends AppCompatActivity {

    private TextView tvParent, tvChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_goodbyepet_reservation_result);

            tvParent = findViewById(R.id.parent);
            tvChild = findViewById(R.id.child);

            Log.e("parentsize",MyCategoriesExpandableListAdapter.parentItems.size()+"");
            Log.e("childsize",MyCategoriesExpandableListAdapter.childItems.size()+"");
            Log.e("dddddddddddddddd",MyCategoriesExpandableListAdapter.parentItems.get(0).size()+"");

//            Log.e("나오세요" ,MyCategoriesExpandableListAdapter.childItems.toString());


            for (int j = 0; j < MyCategoriesExpandableListAdapter.childItems.size(); j++ ){

                if(MyCategoriesExpandableListAdapter.parentItems.get(j).get(ConstantManager.Parameter.IS_CHECKED) == "YES"){
                    Log.e("Yes","ㅇㅇㅇㅇㅇㅇ");

                }


        }
    }
}
