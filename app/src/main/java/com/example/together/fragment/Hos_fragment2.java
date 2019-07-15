package com.example.together.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.together.R;
import com.example.together.adapter.ReviewAdapter;
import com.example.together.model.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class Hos_fragment2 extends Fragment {


    private JSONObject jobj;

    //리스트 뷰 선언
    private ListView reviewListview;

    //TextView
    TextView total_count;
    TextView reviewtitle;

    public Hos_fragment2(JSONObject jobj) {
        this.jobj = jobj;
    }

    // Fragment 의 layout 을 설정하기 위해서는 onCreateView() 메소드를 사용한다.

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

            View v = inflater.inflate(R.layout.tab_hos_fragment2, container, false);

            reviewListview = v.findViewById(R.id.reviewList);
            total_count = v.findViewById(R.id.total_count);
            reviewtitle = v.findViewById(R.id.reviewtitle);

        try {

            ArrayList<Review> ReviewList = new ArrayList<>(); // 리사이클 뷰안에 넣기위한 Vo 배열

            JSONObject jsonobject = (JSONObject) jobj.get("result");

            JSONArray jarry = (JSONArray) jsonobject.get("review");

            Log.e("dd", jarry + "");
            if(jarry.getJSONObject(0).optString("user_nm").equals("없음")) {
                reviewtitle.setText("아직 작성된 리뷰가 없습니다!");
                total_count.setText("0.0");
            }else {
                reviewtitle.setText("리뷰");
                float sum = 0;
                    for (int i = 0; i < jarry.length(); i++) {

                        JSONObject jsonObject = jarry.getJSONObject(i);
                        String user_id = jsonObject.optString("user_id");
                        String user_nm = jsonObject.optString("user_nm");
                        Float rb_avg = Float.valueOf(jsonObject.optString("rb_avg"));
                        String rb_dt = jsonObject.optString("rb_dt");
                        String rb_content = jsonObject.optString("rb_contents");

                        ReviewList.add(new Review(user_id, rb_content, rb_avg, rb_dt, user_nm));
                        sum = sum + Float.valueOf(jsonObject.optString("rb_avg"));
                    }
                 double total = sum/jarry.length();

                   total_count.setText(String.valueOf(total));

                ReviewAdapter reAdapter = new ReviewAdapter(ReviewList);

                reviewListview.setAdapter(reAdapter);
            }




        }catch (JSONException e){
            e.printStackTrace();
        }
        return v;

    }

}
