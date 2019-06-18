package com.example.together.Activities.GoodbyePet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.together.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class GoodbyepetReservationResultActivity extends AppCompatActivity {

    private TextView tvParent, tvChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_goodbyepet_reservation_result);

            tvParent = findViewById(R.id.parent);
            tvChild = findViewById(R.id.child);

            for (int i = 0; i < MyCategoriesExpandableListAdapter.parentItems.size(); i++ ) {

                for (int j = 0; j < MyCategoriesExpandableListAdapter.childItems.get(i).size(); j++ ){

                    String isChildChecked = MyCategoriesExpandableListAdapter.childItems.get(i).get(j).get(ConstantManager.Parameter.IS_CHECKED);

                    if (isChildChecked.equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE)) {

                        tvParent.setText(tvParent.getText() + MyCategoriesExpandableListAdapter.childItems.get(i).get(j).get(ConstantManager.Parameter.SUB_CATEGORY_NAME));
                        tvChild.setText(tvChild.getText() + MyCategoriesExpandableListAdapter.childItems.get(i).get(j).get(ConstantManager.Parameter.SUB_CATEGORY_PRICE));

                    }

                }

            }



    }
}
