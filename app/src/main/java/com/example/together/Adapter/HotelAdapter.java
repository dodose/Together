package com.example.together.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
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
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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
            content = view.findViewById(R.id.content);
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
        myViewHolder.content.setText(HotelArrayList.get(position).content);
        myViewHolder.Time.setText(HotelArrayList.get(position).Time);
        myViewHolder.starcount.setText("★  " +HotelArrayList.get(position).starcount);
        myViewHolder.prise.setText(HotelArrayList.get(position).price + "원");


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String img = HotelArrayList.get(Position).img_path;
                String addr = (String) myViewHolder.etp_addr.getText();
                String na = (String) myViewHolder.etp_name.getText();

                    Intent intent = new Intent(v.getContext(), HotelDetailActivity.class);
                    intent.putExtra("img",img);
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



