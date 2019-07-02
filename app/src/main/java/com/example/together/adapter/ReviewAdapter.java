package com.example.together.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.together.model.Review;
import com.example.together.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends BaseAdapter {

    private List<Review> review;

    public ReviewAdapter(ArrayList<Review> reviewlist){
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
        TextView tv_star = convertView.findViewById(R.id.star_avg); //리뷰 평점
        TextView tv_dt = convertView.findViewById(R.id.review_dt); //리뷰 작성일시
//        TextView tv_pro = convertView.findViewById(R.id.product_name); //상품이름

        Review getRevieList = review.get(position);

        tv_name.setText(getRevieList.getReviewnm());
        tv_cont.setText(getRevieList.getReviewcont());
        tv_star.setText(getRevieList.getReviewstar().toString());
        tv_dt.setText(getRevieList.getReviewdt());

        return convertView;
    }


}
