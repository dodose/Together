package com.example.together.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.together.Activities.PetHotel.HotelDetailActivity;
import com.example.together.Activities.PetHotel.HotelListDataActivity;
import com.example.together.Activities.PetHotel.PetHotelActivity;
import com.example.together.Model.Hotel;
import com.example.together.R;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

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
        TextView text1;
        TextView etp_name;
        TextView content;
        TextView Time;




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
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int position) {

        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        final int Position = position;

        myViewHolder.etp_imgView.setImageResource(HotelArrayList.get(position).drawableId);
        myViewHolder.text1.setText(HotelArrayList.get(position).etp_addr);
        myViewHolder.etp_name.setText(HotelArrayList.get(position).etp_name);
        myViewHolder.content.setText(HotelArrayList.get(position).content);
        myViewHolder.Time.setText(HotelArrayList.get(position).Time);


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(),myViewHolder.text1.getText(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(v.getContext(),myViewHolder.etp_name.getText(), Toast.LENGTH_SHORT).show();
                String addr = (String) myViewHolder.text1.getText();
                String na = (String) myViewHolder.etp_name.getText();



                    Intent intent = new Intent(v.getContext(), HotelDetailActivity.class);
                    intent.putExtra("addr",addr);
                    intent.putExtra("name",na);
                    intent.putExtra("first",first.substring(5));
                    intent.putExtra("last",last.substring(5));

                    v.getContext().startActivity(intent);
            }

        });


        }


    @Override
    public int getItemCount() {

        return HotelArrayList.size();

    }






}



