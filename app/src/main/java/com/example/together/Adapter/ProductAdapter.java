package com.example.together.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.together.Activities.PetHotel.HotelDetailActivity;
import com.example.together.Model.Hotel;
import com.example.together.Model.Product;
import com.example.together.R;
import java.util.ArrayList;


public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    Context mContext;
    private ArrayList<Product> productList;


    public ProductAdapter(ArrayList<Product> productList, Context c ){
        this.mContext = c;
        this.productList = productList;


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

        myViewHolder.proimage.setImageResource(productList.get(position).productimage);
        myViewHolder.pro_name.setText(productList.get(position).productname);
        myViewHolder.pro_cont.setText(productList.get(position).productcont);
        myViewHolder.pro_price.setText(productList.get(position).productprice);



//        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(v.getContext(),myViewHolder.text1.getText(), Toast.LENGTH_SHORT).show();
////                Toast.makeText(v.getContext(),myViewHolder.etp_name.getText(), Toast.LENGTH_SHORT).show();
//                String addr = (String) myViewHolder.text1.getText();
//                String na = (String) myViewHolder.etp_name.getText();
//
//
//
//                Intent intent = new Intent(v.getContext(), HotelDetailActivity.class);
//                intent.putExtra("addr",addr);
//                intent.putExtra("name",na);
//                intent.putExtra("first",first.substring(5));
//                intent.putExtra("last",last.substring(5));
//
//
//                v.getContext().startActivity(intent);
//            }
//
//        });


    }


    @Override
    public int getItemCount() {

        return productList.size();

    }






}



