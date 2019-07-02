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

import com.example.together.activities.petHotel.ProductOrderActivity;
import com.example.together.model.Product;
import com.example.together.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    Context mContext;
    private ArrayList<Product> productList;
    String intent_first;
    String intent_last;
    String intent_phnumber;
    String intent_addr;


    public ProductAdapter(ArrayList<Product> productList, Context c, String pre_first, String pre_last, String detail_addr, String phNumber){
        this.mContext = c;
        this.productList = productList;
        this.intent_first = pre_first;
        this.intent_last = pre_last;
        this.intent_addr = detail_addr;
        this.intent_phnumber = phNumber;



    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView proimage;
        TextView pro_name;
        TextView pro_cont;
        TextView pro_price;

        MyViewHolder(View view){
            super(view);

            proimage = view.findViewById(R.id.productimage);
            pro_name = view.findViewById(R.id.productname);
            pro_cont = view.findViewById(R.id.productcont);
            pro_price= view.findViewById(R.id.productprice);

        }

    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int position) {

        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        final int Position = position;

        Log.e("result",productList.get(position).productimage);

        Picasso.get().load(productList.get(position).productimage).fit().into(myViewHolder.proimage);
        myViewHolder.pro_name.setText(productList.get(position).productname);
        myViewHolder.pro_cont.setText(productList.get(position).productcont);
        myViewHolder.pro_price.setText(productList.get(position).productprice + "원");



        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(),myViewHolder.text1.getText(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(v.getContext(),myViewHolder.etp_name.getText(), Toast.LENGTH_SHORT).show();
                String img = productList.get(Position).productimage.toString();
                String proname = (String) myViewHolder.pro_name.getText();
                String proprise = (String) myViewHolder.pro_price.getText();
                String procont = (String) myViewHolder.pro_cont.getText();



                Intent intent = new Intent(v.getContext(), ProductOrderActivity.class);
                intent.putExtra("img",img);
                intent.putExtra("proname", proname);
                intent.putExtra("proprise",proprise);
                intent.putExtra("procont", procont);
                intent.putExtra("first", intent_first);
                intent.putExtra("last", intent_last);
                intent.putExtra("ph", intent_phnumber);
                intent.putExtra("addr", intent_addr);





                v.getContext().startActivity(intent);
            }

        });


    }


    @Override
    public int getItemCount() {

        return productList.size();

    }






}



