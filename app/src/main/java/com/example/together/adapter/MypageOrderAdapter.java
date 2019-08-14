package com.example.together.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.R;
import com.example.together.model.UserOrder;

import java.util.ArrayList;

public class MypageOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    public ArrayList<UserOrder> userOrder;

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
            myViewHolder.stat1.setBackgroundResource(R.drawable.button_radiusw1);
            myViewHolder.stat2.setBackgroundResource(R.drawable.background_radius_borderblackw3);
            myViewHolder.stat3.setBackgroundResource(R.drawable.background_radius_borderblackw3);
            myViewHolder.detail_review.setVisibility(View.GONE);
            myViewHolder.detail_cancel.setVisibility(View.VISIBLE);
        }else if(userOrder.get(position).or_stat.equals("2")){
            myViewHolder.stat1.setBackgroundResource(R.drawable.background_radius_borderblackw3);
            myViewHolder.stat2.setBackgroundResource(R.drawable.button_radiusw1);
            myViewHolder.stat3.setBackgroundResource(R.drawable.background_radius_borderblackw3);
            myViewHolder.detail_review.setVisibility(View.VISIBLE);
            myViewHolder.detail_cancel.setVisibility(View.GONE);
        }else if((userOrder.get(position).or_stat.equals("3"))){
            myViewHolder.stat1.setBackgroundResource(R.drawable.background_radius_borderblackw3);
            myViewHolder.stat2.setBackgroundResource(R.drawable.background_radius_borderblackw3);
            myViewHolder.stat3.setBackgroundResource(R.drawable.button_radiusw1);
            myViewHolder.detail_review.setVisibility(View.GONE);
            myViewHolder.detail_cancel.setVisibility(View.GONE);
        }



        myViewHolder.detail_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("1","업체보기버튼 클릭 완료");
            }

        });

        myViewHolder.detail_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("2","취소버튼 클릭 완료");
            }
        });

    }

    @Override
    public int getItemCount() {
        return userOrder.size();
    }
}