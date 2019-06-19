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
import android.widget.TextView;

import com.example.together.Model.FuneralProdcutOrder;
import com.example.together.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class f_productAdapter extends BaseAdapter {


    Button Btn;

    private List<FuneralProdcutOrder> list;

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
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {



        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_funeralorder, parent, false);

        }


        TextView tv_name = convertView.findViewById(R.id.productnm); //상품이름
        TextView tv_price = convertView.findViewById(R.id.productpri); //상품 가격
        ImageView tv_img = convertView.findViewById(R.id.pro_img);
        Button tv_delete = convertView.findViewById(R.id.deleteBtn);



        final FuneralProdcutOrder getProlist = list.get(position);

        tv_name.setText(getProlist.getName());
        tv_price.setText(getProlist.getPrice());
        Picasso.get().load(getProlist.getImg()).fit().into(tv_img);

//        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        View view = inflater.inflate(R.layout.activity_goodbyepet_reservation_result, Context, false);

//        Button b = view.findViewById(R.id.f_orderBtn);

//        b.setText("테스트");

//        Log.e("view",b+"");

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                list.remove(position);

                notifyDataSetChanged();

            }
        });

        return convertView;
    }
}
