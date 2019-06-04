package com.example.together.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.together.Activities.PetHotel.HotelListDataActivity;
import com.example.together.Model.Hotel;
import com.example.together.R;

import java.util.ArrayList;

public class HotelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


//    private LinearLayout LayoutLinear;

    private ArrayList<Hotel> HotelArrayList;

    private ItemClick itemClick;

    public interface  ItemClick{
        void onClick(View view, int position);
    }

    public HotelAdapter(ArrayList<Hotel> HotelArrayList){

        this.HotelArrayList = HotelArrayList;
    }

        public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;

        }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView etp_imgView;
        TextView text1;
        TextView etp_name;
        TextView content;
        TextView Time;

        View view;


        MyViewHolder(View view){
            super(view);

            etp_imgView = view.findViewById(R.id.drawableId);
            etp_name = view.findViewById(R.id.etp_name);
            text1 = view.findViewById(R.id.etp_addr);
            content = view.findViewById(R.id.content);
            Time = view.findViewById(R.id.Time);


        }

    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotellist_item, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder,int position) {

        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        final int Position = position;

        myViewHolder.etp_imgView.setImageResource(HotelArrayList.get(position).drawableId);
        myViewHolder.text1.setText(HotelArrayList.get(position).etp_addr);
        myViewHolder.etp_name.setText(HotelArrayList.get(position).etp_name);
        myViewHolder.content.setText(HotelArrayList.get(position).content);
        myViewHolder.Time.setText(HotelArrayList.get(position).Time);


        myViewHolder.etp_imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClick != null){
                    itemClick.onClick(v,Position);
//                    Toast.makeText(HotelListDataActivity.class, position, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {

        return HotelArrayList.size();

    }


}



