package com.example.together.adapter;

import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.R;
import com.example.together.fragment.Hos_or_fragment2;
import com.example.together.model.Pet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TimesetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<String> checkTime;
    ArrayList<String> checklist;

    int count=0;

    public static int mSelectedItem = -1;
    static String checkTimeset;



    public TimesetAdapter(ArrayList<String> checkTime, ArrayList<String> checklist) {
        this.checkTime = checkTime;
        this.checklist = checklist;
    }

    public static String Timeset() {
        return checkTimeset;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        RadioButton clickradio;
        TextView checkset;
        LinearLayout colorLinear;

        MyViewHolder(View v){
            super(v);

            //라디오 버튼 설정
            clickradio = (RadioButton) v.findViewById(R.id.clickradio);

            //확인유무
            checkset = v.findViewById(R.id.okset);

            colorLinear = v.findViewById(R.id.colorLinear);

            clickradio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    checkTimeset = clickradio.getText().toString();

                    notifyDataSetChanged();


                }
            });

        }


    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timecheck, parent,false);

        TimesetAdapter.MyViewHolder viewHolder = new TimesetAdapter.MyViewHolder(v);

        return viewHolder;
    }

    @Override


    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        final int Position = position;

       myViewHolder.clickradio.setChecked(mSelectedItem == position);

        myViewHolder.clickradio.setText(checklist.get(Position));
        String result[] = checklist.get(Position).split("~");

        if(checkTime.isEmpty()){

            myViewHolder.checkset.setTextColor(Color.BLUE);
            myViewHolder.checkset.setText("예약가능");

        }else{

            for(int i = 0; i<checkTime.size(); i++){

                if(result[0].trim().equals(checkTime.get(i).substring(10).trim())){
                    myViewHolder.checkset.setTextColor(Color.RED);
                    myViewHolder.checkset.setText("예약불가");
                    break;
                }else{
                    myViewHolder.checkset.setTextColor(Color.BLUE);
                    myViewHolder.checkset.setText("예약가능");
                }


            }
        }

    }


    @Override
    public int getItemCount() {

        return checklist.size();

    }
}
