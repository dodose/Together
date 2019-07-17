package com.example.together.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.activities.petHotel.HotelDetailActivity;
import com.example.together.model.Hotel;
import com.example.together.R;
import com.example.together.model.Recommend;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Recommend> RecommentList;



    public RecommentAdapter(ArrayList<Recommend> recommentlist){

        this.RecommentList = recommentlist;

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView funeral_img;
        TextView etp_name;
        TextView Time;
        TextView dis_addr;
        TextView startext;
        TextView star;
        TextView totalreview;

        // TextView 나중에 위도경도 변환값들어갈곳

        MyViewHolder(View view){
            super(view);

            funeral_img = view.findViewById(R.id.funeral_img);
            etp_name = view.findViewById(R.id.etp_name);
            Time = view.findViewById(R.id.Time);
            dis_addr = view.findViewById(R.id.dis_addr);
            startext = view.findViewById(R.id.startext);
            star = view.findViewById(R.id.star);
            totalreview = view.findViewById(R.id.totalreview);


        }

    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int position) {

        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        final int Position = position;

        Picasso.get().load(RecommentList.get(Position).img_path).fit().into(myViewHolder.funeral_img);
        myViewHolder.etp_name.setText(RecommentList.get(Position).etp_name);
        myViewHolder.Time.setText(RecommentList.get(Position).Time);
        myViewHolder.dis_addr.setText(RecommentList.get(Position).etp_addr);
        myViewHolder.star.setText("평점 "+RecommentList.get(Position).reviewavg);
        if(Float.parseFloat(RecommentList.get(Position).reviewavg) == 5.0) {
            myViewHolder.startext.setText("★★★★★");
        }else if(Float.parseFloat(RecommentList.get(Position).reviewavg) < 4.9 && Float.parseFloat(RecommentList.get(Position).reviewavg) >= 4.0){
            myViewHolder.startext.setText("★★★★☆");
        }else if(Float.parseFloat(RecommentList.get(Position).reviewavg) < 3.9 && Float.parseFloat(RecommentList.get(Position).reviewavg) >= 3.0){
            myViewHolder.startext.setText("★★★☆☆");
        }else if(Float.parseFloat(RecommentList.get(Position).reviewavg) < 2.9 && Float.parseFloat(RecommentList.get(Position).reviewavg) >= 2.0){
            myViewHolder.startext.setText("★★☆☆☆");
        }else{
            myViewHolder.startext.setText("★☆☆☆☆");
        }
        myViewHolder.totalreview.setText("총 리뷰 " +RecommentList.get(Position).reviewcount +"개");

//        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String img = HotelArrayList.get(Position).img_path;
//                String etp_lat = HotelArrayList.get(Position).etp_lat;//위도
//                String etp_lnt = HotelArrayList.get(Position).etp_lnt; //경도
//                String addr = (String) myViewHolder.etp_addr.getText();
//                String na = (String) myViewHolder.etp_name.getText();
//
////                Log.e("firstAndlast",first+"~"+last);
//
//                Intent intent = new Intent(v.getContext(), HotelDetailActivity.class);
//                intent.putExtra("img",img);
//                intent.putExtra("addr",addr);
//                intent.putExtra("name",na);
//                intent.putExtra("first",first.substring(5));
//                intent.putExtra("last",last.substring(5));
//                intent.putExtra("lat",etp_lat);
//                intent.putExtra("lnt",etp_lnt);
//
//
//                v.getContext().startActivity(intent);
//            }
//
//        });


    }


    @Override
    public int getItemCount() {

        return RecommentList.size();

    }







}



