package com.example.together.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.R;
import com.example.together.activities.my_petInfo.MypetCalendarActivity;
import com.example.together.activities.my_petInfo.MypetCalendarShowActivity;
import com.example.together.model.CalendarData;

import java.util.ArrayList;

public class CalendarSetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<CalendarData> calMark;
    public Context mContext;

    public CalendarSetAdapter(ArrayList<CalendarData> calMark,Context c) {
            this.calMark = calMark;
            this.mContext = c;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Time1,Time2, setCont;
        View colorview;

        // TextView 나중에 위도경도 변환값들어갈곳

        MyViewHolder(View view){
            super(view);

            Time1 = view.findViewById(R.id.time1);
            Time2 = view.findViewById(R.id.time2);
            setCont = view.findViewById(R.id.cont);
            colorview = view.findViewById(R.id.viewcolor);

        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_calendar, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {

        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        final int Position = position;

        Log.e("array",calMark+"");

        myViewHolder.Time1.setText(calMark.get(position).setTime1.substring(11,16));
        myViewHolder.Time2.setText(calMark.get(position).setTime2.substring(11,16));
        myViewHolder.setCont.setText(calMark.get(position).setContent);

        if(calMark.get(position).setType.equals("1")){
            myViewHolder.colorview.setBackgroundColor(Color.GREEN);
        }else{
            myViewHolder.colorview.setBackgroundColor(Color.RED);
        }

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(v.getContext(), MypetCalendarShowActivity.class);
                intent.putExtra("Content",calMark.get(position).setContent);
                intent.putExtra("Time",calMark.get(position).setTime1);
                intent.putExtra("Time2",calMark.get(position).setTime2);
                ((MypetCalendarActivity) mContext).startActivityForResult(intent,2);
            }

        });

    }

    @Override
    public int getItemCount() {
        return calMark.size();
    }
}
