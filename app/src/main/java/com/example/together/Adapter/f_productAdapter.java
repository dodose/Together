package com.example.together.Adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.together.Model.FuneralProdcutOrder;
import com.example.together.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class f_productAdapter extends BaseAdapter {

    Button b;


    LayoutInflater inflater = null;

    private ArrayList<FuneralProdcutOrder> list;

    public f_productAdapter(ArrayList<FuneralProdcutOrder> f_order) {
        super();
        list = f_order;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).hashCode();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

            if(convertView == null) {
                inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_funeralorder, parent, false);
            }
        TextView tv_name = convertView.findViewById(R.id.productnm); //상품이름
        TextView tv_price = convertView.findViewById(R.id.productpri); //상품 가격
        ImageView tv_img = convertView.findViewById(R.id.pro_img);
        Button tv_delete = convertView.findViewById(R.id.deleteBtn);

        FuneralProdcutOrder getProlist = list.get(position);

        tv_name.setText(getProlist.getName());
        tv_price.setText(getProlist.getPrice());
        Picasso.get().load(getProlist.getImg()).fit().into(tv_img);


        LayoutInflater inflaters = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View Toplayout =  inflaters.inflate(R.layout.activity_goodbyepet_reservation_result, null );
//
        b = Toplayout.findViewById(R.id.f_orderBtn);
        TextView test = Toplayout.findViewById(R.id.toolbar_title);


        Log.e("test",test.getText()+"");

        test.setText("바뀌어라 이이이잉 앗쌀람말라잉쿵");

//        Log.e("view context", parent.getContext()+"");
//        Log.e("view position", position+"");
//        Log.e("view count", list.size()+"");
//        Log.e("view",b.getText()+"");
//        b.setText("테스트");


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("view alert" ,"확실히 버튼 객체를 찾았습니다 눌림");
            }
        });


        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
//                b.setText("테스트");
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
