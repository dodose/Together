package com.example.together.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.R;
import com.example.together.activities.EtpInfoActivity;
import com.example.together.activities.reViewActivity;
import com.example.together.model.UserOrder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class MypageOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    public ArrayList<UserOrder> userOrder;
    public JSONObject jobj;
    public URL Url;
    public String strUrl;

    public MypageOrderAdapter(ArrayList<UserOrder> userOrder) {
        this.userOrder = userOrder;

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


    TextView or_cd,or_price,th_dt,etp_nm,pd_nm,or_dt;
    TextView stat1,stat2,stat3;
    Button detail_show,detail_review,detail_cancel;

    // TextView 나중에 위도경도 변환값들어갈곳

    MyViewHolder(View view){
        super(view);

        or_cd = view.findViewById(R.id.or_cd);
        or_price = view.findViewById(R.id.or_price);
        th_dt = view.findViewById(R.id.th_dt);
        etp_nm = view.findViewById(R.id.etp_nm);
        pd_nm = view.findViewById(R.id.pd_nm);
        or_dt = view.findViewById(R.id.or_dt);

        stat1 = view.findViewById(R.id.stat1);
        stat2 = view.findViewById(R.id.stat2);
        stat3 = view.findViewById(R.id.stat3);

        detail_show = view.findViewById(R.id.detail_show);
        detail_review = view.findViewById(R.id.detail_review);
        detail_cancel = view.findViewById(R.id.detail_cancle);


    }

}


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list, parent,false);
        return new MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {

        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        final int Position = position;


        myViewHolder.or_cd.setText(userOrder.get(position).or_cd);
        myViewHolder.th_dt.setText(userOrder.get(position).th_dt);
        myViewHolder.etp_nm.setText(userOrder.get(position).etp_nm);
//        myViewHolder.pd_nm.setText(userOrder.get(position).pd_cd);
        myViewHolder.or_dt.setText(userOrder.get(position).or_dt + " ~ " + userOrder.get(position).or_dt2);


        if(userOrder.get(position).or_price.equals("0")){
            myViewHolder.or_price.setText("가격없음");
        }else{
            myViewHolder.or_price.setText(userOrder.get(position).or_price + " 원");
        }



        if(userOrder.get(position).or_stat.equals("1")){
            myViewHolder.stat1.setBackgroundResource(R.drawable.background_radius_borderblackw2);
            myViewHolder.stat2.setBackgroundResource(R.drawable.background_radius_borderblackw3);
            myViewHolder.stat3.setBackgroundResource(R.drawable.background_radius_borderblackw3);
            myViewHolder.detail_review.setVisibility(View.GONE);
            myViewHolder.detail_cancel.setVisibility(View.VISIBLE);
        }else if(userOrder.get(position).or_stat.equals("2")){
            myViewHolder.stat1.setBackgroundResource(R.drawable.background_radius_borderblackw3);
            myViewHolder.stat2.setBackgroundResource(R.drawable.background_radius_borderblackw2);
            myViewHolder.stat3.setBackgroundResource(R.drawable.background_radius_borderblackw3);
            myViewHolder.detail_review.setVisibility(View.VISIBLE);
            myViewHolder.detail_cancel.setVisibility(View.GONE);
        }else if((userOrder.get(position).or_stat.equals("3"))){
            myViewHolder.stat1.setBackgroundResource(R.drawable.background_radius_borderblackw3);
            myViewHolder.stat2.setBackgroundResource(R.drawable.background_radius_borderblackw3);
            myViewHolder.stat3.setBackgroundResource(R.drawable.background_radius_borderblackw2);
            myViewHolder.detail_review.setVisibility(View.GONE);
            myViewHolder.detail_cancel.setVisibility(View.GONE);
        }



        myViewHolder.detail_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), EtpInfoActivity.class);
                intent.putExtra("etp_code",userOrder.get(position).etp_cd);
                v.getContext().startActivity(intent);
            }

        });

        myViewHolder.detail_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(v.getContext());
                alert_confirm.setMessage("예약을 취소하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Log.e("확인","확인");
                                // 'YES'
                                    JSONObject in = new JSONObject();
                                    try {
                                        in.put("or_cd",userOrder.get(position).or_cd);
                                        in.put("etp_cd",userOrder.get(position).etp_cd);
                                        in.put("day",userOrder.get(position).or_dt);
                                        in.put("day2",userOrder.get(position).or_dt2);

                                        DBconnet(in);

                                        userOrder.remove(position);
                                        notifyDataSetChanged();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                            }
                        }).setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 'No'
                                return;
                            }
                        });
                AlertDialog alert = alert_confirm.create();
                alert.show();


            }
        });

        myViewHolder.detail_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), reViewActivity.class);
                      intent.putExtra("etpcode",userOrder.get(position).etp_cd);
                      v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userOrder.size();
    }

    public void DBconnet(JSONObject in){

        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                strUrl = "http://39.127.7.80:8080/Order_cancle"; //탐색하고 싶은 URL이다.

            }

            @Override
            protected JSONObject doInBackground(Void... voids) {

                jobj = new JSONObject();

                try {
                    //서버 연결
                    Url = new URL(strUrl);  // URL화 한다.
                    HttpURLConnection conn = (HttpURLConnection) Url.openConnection(); // URL을 연결한 객체 생성.
                    conn.setRequestMethod("POST"); // post방식 통신
                    conn.setDoOutput(true);       // 쓰기모드 지정
                    conn.setDoInput(true);        // 읽기모드 지정yu

                    conn.setRequestProperty("Content-Type", "application/json; utf-8");
                    conn.setRequestProperty("Accept", "application/json; utf-8");
                    conn.connect();


                    //데이터 전달 하는곳

                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(in.toString());

                    wr.flush();

                    wr.close(); //전달후 닫아준다.


                    // 데이터 받아오는 곳
                    InputStream is = null;        //input스트림 개방
                    BufferedReader reader = null;

                    is = conn.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));  //문자열 셋 세팅
                    StringBuffer rbuffer = new StringBuffer();   //문자열을 담기 위한 객체
                    String line = null;

                    rbuffer.append(reader.readLine());


                    jobj = new JSONObject(rbuffer.toString().trim());
                    is.close();
                    conn.disconnect();


                    Log.e("result", String.valueOf(jobj));


                } catch (MalformedURLException | ProtocolException exception) {
                    exception.printStackTrace();
                } catch (IOException io) {
                    io.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return jobj;
            }

            @Override
            protected void onPostExecute(JSONObject aVoid) {
                super.onPostExecute(aVoid);

                notifyDataSetChanged();


            }
        }.execute();

    }
}