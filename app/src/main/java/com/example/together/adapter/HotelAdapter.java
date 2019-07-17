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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HotelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    private ArrayList<Hotel> HotelArrayList;
    String first;
    String last;


    public HotelAdapter(ArrayList<Hotel> HotelArrayList, Context c, String first_day, String last_day){
        this.mContext = c;
        this.HotelArrayList = HotelArrayList;
        this.first = first_day;
        this.last = last_day;

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView etp_imgView;
        TextView starcount;
        TextView etp_name;
        TextView Time;
        TextView etp_addr;
        TextView content;
        TextView prise;
        // TextView 나중에 위도경도 변환값들어갈곳

        MyViewHolder(View view){
            super(view);

            etp_imgView = view.findViewById(R.id.drawableId);
            etp_name = view.findViewById(R.id.etp_name);
            etp_addr = view.findViewById(R.id.etp_addr);
            Time = view.findViewById(R.id.Time);
            starcount = view.findViewById(R.id.etp_star);
            prise = view.findViewById(R.id.product_firstPrice);

        }

    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotellist_item, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int position) {

        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        final int Position = position;

        Picasso.get().load(HotelArrayList.get(position).img_path).fit().into(myViewHolder.etp_imgView);
        myViewHolder.etp_addr.setText(HotelArrayList.get(position).etp_addr);
        myViewHolder.etp_name.setText(HotelArrayList.get(position).etp_name);
        myViewHolder.Time.setText(HotelArrayList.get(position).Time);
        myViewHolder.starcount.setText("★  " +HotelArrayList.get(position).starcount);
        myViewHolder.prise.setText(HotelArrayList.get(position).price + "원");


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String img = HotelArrayList.get(Position).img_path;
                String etp_lat = HotelArrayList.get(Position).etp_lat;//위도
                String etp_lnt = HotelArrayList.get(Position).etp_lnt; //경도
                String addr = HotelArrayList.get(Position).etp_addr;
                String na = HotelArrayList.get(Position).etp_name;

//                Log.e("firstAndlast",first+"~"+last);

                Intent intent = new Intent(v.getContext(), HotelDetailActivity.class);
                intent.putExtra("img",img);
                intent.putExtra("addr",addr);
                intent.putExtra("name",na);
                intent.putExtra("first",first.substring(5));
                intent.putExtra("last",last.substring(5));
                intent.putExtra("lat",etp_lat);
                intent.putExtra("lnt",etp_lnt);


                v.getContext().startActivity(intent);
            }

        });


    }


    @Override
    public int getItemCount() {

        return HotelArrayList.size();

    }







}



