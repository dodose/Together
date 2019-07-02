package com.example.together.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;

import com.example.together.R;
import com.example.together.activities.goodbyePet.GoodbyepetOrders;
import com.example.together.model.FuneralProdcutOrder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
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

public class f_productAdapter extends BaseAdapter {

    private ArrayList<FuneralProdcutOrder> list;
    private Context mCont;
    private Button order_Btn;
    private int t_sum;

    //접속한 사용자값
    FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();

    //날짜값
    private String day;
    private String time;
    private String code;

    //HTTPCONNETION
    private String strUrl;
    private URL Url;


    public f_productAdapter(ArrayList<FuneralProdcutOrder> f_order, Context mContext,Button Btn,String day,String time,String code) {
        super();
        list = f_order;
        mCont = mContext;
        order_Btn = Btn;
        this.day = day;
        this.time = time;
        this.code = code;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).hashCode();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        int sum = 0; //총가격 합산함수

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_funeralorder, parent, false);
        }
        TextView tv_name = convertView.findViewById(R.id.productnm); //상품이름
        TextView tv_price = convertView.findViewById(R.id.productpri); //상품 가격
        ImageView tv_img = convertView.findViewById(R.id.pro_img);
        Button tv_delete = convertView.findViewById(R.id.deleteBtn);

        FuneralProdcutOrder getProlist = list.get(position);

        tv_name.setText(getProlist.getName());
        tv_price.setText(getProlist.getPrice());
        Picasso.get().load(getProlist.getImg()).fit().into(tv_img);


        for(int i =0; i<list.size(); i++){
            sum = sum + Integer.parseInt(list.get(i).getPrice());
        }

        t_sum = sum;
        Log.e("sum", String.valueOf(sum));
        order_Btn.setText(list.size() + "개" +  t_sum + "원");



        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.size() == 1){
                    int sum_o = 0;
                    order_Btn.setText("선택한 상품이 없습니다.");
                }else{

                    order_Btn.setText("총 " + list.size() + "개  " +  String.valueOf(t_sum - Integer.parseInt(list.get(position).getPrice())) + " 원 예약하기");
                }

                list.remove(position);

                notifyDataSetChanged();
            }
        });



        order_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                final JSONObject jobj = new JSONObject();
                JSONArray jarry = new JSONArray();

                for(int i=0; i<list.size(); i++){
                    JSONObject map = new JSONObject();
                    try {
                        map.put("user",fuser.getEmail());
                        map.put("day",day);
                        map.put("time",time);
                        map.put("name",list.get(i).getName());
                        map.put("price",list.get(i).getPrice());
                        map.put("code",code);
                        jarry.put(map);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                try {
                    jobj.put("result",jarry);
                    Log.e("jboj",jobj+"");

                    new AsyncTask<Void, Void, JSONObject>() {

                        String j;

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            strUrl = "http://39.127.7.80:8080/funeral_order"; //탐색하고 싶은 URL이다.

                        }

                        @Override
                        protected JSONObject doInBackground(Void... voids) {

                            JSONObject Re_jobj;


                            try {
                                //서버 연결
                                Url = new URL(strUrl);  // URL화 한다.
                                HttpURLConnection conn = (HttpURLConnection) Url.openConnection(); // URL을 연결한 객체 생성.
                                conn.setRequestMethod("POST"); // post방식 통신
                                conn.setDoOutput(true);       // 쓰기모드 지정
                                conn.setDoInput(true);        // 읽기모드 지정

                                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                                conn.setRequestProperty("Accept", "application/json; utf-8");
                                conn.connect();


                                //데이터 전달 하는곳

                                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                                wr.write(String.valueOf(jobj));

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


                                Re_jobj = new JSONObject(rbuffer.toString().trim());
                                is.close();
                                conn.disconnect();


                                Log.e("result", String.valueOf(Re_jobj));

                                j = String.valueOf(Re_jobj);
                            } catch (MalformedURLException | ProtocolException exception) {
                                exception.printStackTrace();
                            } catch (IOException io) {
                                io.printStackTrace();
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return jobj;
                        }

                        @Override
                        protected void onPostExecute(JSONObject aVoid) {
                            super.onPostExecute(aVoid);

                            Log.e("주문완료","주문완료");
                            Intent intent = new Intent(v.getContext(), GoodbyepetOrders.class);
                            intent.putExtra("json",j);
                            v.getContext().startActivity(intent);

                        }
                    }.execute();



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        return convertView;
    }
}
