package com.example.together.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.together.Model.Review;
import com.example.together.R;

import java.util.ArrayList;

public class ReviewAdapter extends BaseAdapter {

    private ArrayList<Review> review;

    public ReviewAdapter(ArrayList<Review> reviewlist, Context context){
        super();
        this.review = reviewlist;
    }


    @Override
    public int getCount() {
        return review.size();
    }

    @Override
    public Object getItem(int position) {
        return review.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_review, parent, false);
        }

        TextView tv_name = (TextView) convertView.findViewById(R.id.user_name); //유저아이디
        TextView tv_cont = (TextView) convertView.findViewById(R.id.reviewcontents); //리뷰내용
//        TextView tv_pro = convertView.findViewById(R.id.product_name); //상품이름

        Review getRevieList = review.get(position);

        tv_name.setText(getRevieList.getReviewid());
        tv_cont.setText(getRevieList.getReviewcont());

        return convertView;
    }


}
