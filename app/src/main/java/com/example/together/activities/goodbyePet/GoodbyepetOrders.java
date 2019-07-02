package com.example.together.activities.goodbyePet;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.together.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GoodbyepetOrders extends AppCompatActivity {

    TextView appendproduct;
    TextView order_code;
    TextView stat;
    TextView etpname;
    TextView day1;
    TextView day2;
    TextView price;

    private int t_sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodbyepet_orders);

        appendproduct = findViewById(R.id.pro_nm);
        order_code = findViewById(R.id.order_cd);
        stat = findViewById(R.id.status);
        etpname = findViewById(R.id.etp_nm);
        day1 = findViewById(R.id.first);
        day2 = findViewById(R.id.second);
        price = findViewById(R.id.total_price2);


        Intent Ex = getIntent();
        try {
            JSONObject obj = new JSONObject(Ex.getStringExtra("json"));
            JSONArray jarry = (JSONArray) obj.get("result");
            ArrayList<Integer> aa = new ArrayList<>();
            for(int i=0; i<jarry.length(); i++){
                JSONObject jsonObject = jarry.getJSONObject(i);
                String pro_nm = jsonObject.optString("pro_nm");
                String pro_price = jsonObject.optString("price");

                aa.add(Integer.valueOf(pro_price));
                appendproduct.setTextSize(17);


                if(i == jarry.length()-1) {
                    appendproduct.append(pro_nm);

                }else{
                    appendproduct.append(pro_nm + ",");
                }

            }
            int sum = 0;

            for(int i=0; i<aa.size(); i++){
                sum = aa.get(i) + sum;
            }
            t_sum = sum;
            Log.e("sum",t_sum+"");

            JSONObject jsonObject = jarry.getJSONObject(0);
            String etp_nm = jsonObject.optString("etp_nm");
            String or_dt = jsonObject.optString("or_dt");
            String or_dt2 = jsonObject.optString("or_dt2");
            String th_dt = jsonObject.optString("th_dt");
            String status = jsonObject.optString("stat");
            String order_cd = jsonObject.optString("order_cd");

            order_code.setText("No. " + order_cd);
            if(status.equals("1")) {
                stat.setTextColor(Color.RED);
                stat.setText("확인 대기중");
            }else{
                stat.setTextColor(Color.BLUE);
                stat.setText("확인 완료");
            }
            etpname.setText(etp_nm);
            day1.setText(or_dt.substring(5));
            day2.setText(or_dt2.substring(5));

            price.setText("결제 가격 : " + t_sum + "원");



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
