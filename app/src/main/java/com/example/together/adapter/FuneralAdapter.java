package com.example.together.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.together.activities.goodbyePet.GoodbyepetMenuSelectActivity;
import com.example.together.model.Funeral;
import com.example.together.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FuneralAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        Context mContext;
        private ArrayList<Funeral> FuneralArrayList;
        String day;
        String time;
        String addr;

        public FuneralAdapter(ArrayList<Funeral> FuneralArrayList, Context c, String day,String time,String addr){
                this.mContext = c;
                this.FuneralArrayList = FuneralArrayList;
                this.day = day;
                this.time = time;
                this.addr = addr;

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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet_store_list, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {

        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        final int Position = position;

        Log.e("array",FuneralArrayList+"");

        Picasso.get().load(FuneralArrayList.get(position).img_path).fit().into(myViewHolder.etp_imgView);
        myViewHolder.etp_addr.setText(FuneralArrayList.get(position).etp_addr);
        myViewHolder.etp_name.setText(FuneralArrayList.get(position).etp_name);
        myViewHolder.content.setText(FuneralArrayList.get(position).content);
        myViewHolder.Time.setText(FuneralArrayList.get(position).Time);
        myViewHolder.starcount.setText("★  " +FuneralArrayList.get(position).starcount);
        myViewHolder.prise.setText(FuneralArrayList.get(position).price + "원~");

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String img = FuneralArrayList.get(Position).img_path;
                String addr = (String) myViewHolder.etp_addr.getText();
                String na = (String) myViewHolder.etp_name.getText();
                String code = FuneralArrayList.get(position).etp_cd;

                Intent intent = new Intent(v.getContext(), GoodbyepetMenuSelectActivity.class);
                intent.putExtra("img",img);
                intent.putExtra("addr",addr);
                intent.putExtra("name",na);
                intent.putExtra("code",code);
                intent.putExtra("day",day);
                intent.putExtra("time",time);


                v.getContext().startActivity(intent);
            }

        });

    }

    @Override
    public int getItemCount() {
        return FuneralArrayList.size();
    }
}
