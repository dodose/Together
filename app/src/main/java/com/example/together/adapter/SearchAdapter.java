package com.example.together.adapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.R;
import com.example.together.activities.petHotel.HotelLocationSelect;

public class SearchAdapter extends  RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private final FragmentActivity activity;
    private String[] list;
    private String local;
    private String Addr;


    public SearchAdapter(String[] addList, String local, FragmentActivity activity) {
        this.list = addList;
        this.local = local;
        this.activity = activity;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView location;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            location = itemView.findViewById(R.id.AddressName);

        }
    }

    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item_locationview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {

        holder.location.setText(list[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Addr = local+" "+list[position];
                Intent intent = new Intent();
                intent.putExtra("result",Addr);
                activity.setResult(2,intent);
                activity.finish();


            }
        });



    }

    @Override
    public int getItemCount() {
        return list.length;
    }



}

