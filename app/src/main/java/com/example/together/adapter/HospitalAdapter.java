package com.example.together.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.R;
import com.example.together.activities.petHospital.PetHospitalDetailInfoActivity;
import com.example.together.model.Hospital;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HospitalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context c;
    private ArrayList<Hospital> list;
    private String petcode;
    private String date;
    private String Address;

    public HospitalAdapter(ArrayList<Hospital> hospitalList, Context mContent,String petcode ,String date,String Address) {
        this.list = hospitalList;
        this.c = mContent;
        this.petcode = petcode;
        this.date = date;
        this.Address = Address;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView etp_imgView;
        TextView starcount;
        TextView etp_name;
        TextView Time;
        TextView etp_addr;
        TextView content;
        TextView dis_km;

        // TextView 나중에 위도경도 변환값들어갈곳

        MyViewHolder(View view){
            super(view);

            etp_imgView = view.findViewById(R.id.drawableId);
            etp_name = view.findViewById(R.id.etp_name);
            etp_addr = view.findViewById(R.id.etp_addr);
            content = view.findViewById(R.id.content);
            Time = view.findViewById(R.id.Time);
            starcount = view.findViewById(R.id.etp_star);
            dis_km = view.findViewById(R.id.dis_km);



        }

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet_store_hlist, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {

        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        final int Position = position;

        Picasso.get().load(list.get(position).img_path).fit().into(myViewHolder.etp_imgView);
        myViewHolder.etp_addr.setText(list.get(position).mAddr);
        myViewHolder.etp_name.setText(list.get(position).mName);
        myViewHolder.content.setText(list.get(position).content);
        myViewHolder.Time.setText(list.get(position).mTime);
        myViewHolder.starcount.setText("★  " +list.get(position).starcount);
        myViewHolder.dis_km.setText(list.get(position).km+ " km");


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = list.get(Position).etp_cd;
                String img = list.get(Position).img_path;
                String Name = list.get(Position).mName;
                String etp_addr = list.get(Position).mAddr;

//
                Intent intent = new Intent(v.getContext(), PetHospitalDetailInfoActivity.class);
                intent.putExtra("petcode",petcode);
                intent.putExtra("date",date);
                intent.putExtra("img",img);
                intent.putExtra("code",code);
                intent.putExtra("name",Name);
                intent.putExtra("Address",etp_addr);

                v.getContext().startActivity(intent);
            }

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
