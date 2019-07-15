package com.example.together.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.together.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

@SuppressLint("ValidFragment")
public class Hos_fragment1 extends Fragment {

    private JSONObject jobj;
    String mName;
    String mAddr;
    String mTime ;
    String img_path;
    String etp_lat;
    String etp_lnt;
    String etp_license;
    String etp_ph ;
    String etp_intro;
    String etp_info ;
    String etp_email;

    //해당 뷰의 텍스트
    TextView time;
    TextView addr;
    TextView ph;
    TextView info;
    TextView intro;
    TextView name;
    TextView license;


    public Hos_fragment1(JSONObject jobj) {
        this.jobj = jobj;
    }

    // Fragment 의 layout 을 설정하기 위해서는 onCreateView() 메소드를 사용한다.
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.tab_hos_fragment1, container, false);
        time = v.findViewById(R.id.etp_time);
        addr = v.findViewById(R.id.etp_addr);
        ph = v.findViewById(R.id.etp_ph);
        info = v.findViewById(R.id.etp_info);
        intro = v.findViewById(R.id.etp_intro2);
        name = v.findViewById(R.id.etp_nm);
        license = v.findViewById(R.id.etp_license);




        try {
            JSONObject jsonobject = (JSONObject) jobj.get("result");

            JSONArray jarry = (JSONArray) jsonobject.get("info");
            Log.e("dd",jsonobject+"");

            for (int i = 0; i < jarry.length(); i++) {
//
                JSONObject jsonObject = jarry.getJSONObject(0);

                mName = jsonObject.optString("etp_nm");
                mAddr = jsonObject.optString("etp_addr");
                mTime = jsonObject.optString("etp_time");
                img_path = jsonObject.optString("etp_img");
                etp_lat = jsonObject.optString("etp_lat");
                etp_lnt = jsonObject.optString("etp_lnt");
                etp_license = jsonObject.optString("etp_license");
                etp_ph = jsonObject.optString("etp_ph_no");
                etp_intro = jsonObject.optString("etp_intro");
                etp_info = jsonObject.optString("etp_info");
                etp_email = jsonObject.optString("etp_email");

            }

            time.setText(mTime);
            addr.setText(mAddr);
            ph.setText(etp_ph);
            info.setText(etp_intro);
            intro.setText(etp_info);
            name.setText(mName);
            license.setText(etp_license);

         } catch (JSONException e) { e.printStackTrace(); }

        return v;

    }

}
