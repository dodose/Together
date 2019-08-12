package com.example.together.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.R;
import com.example.together.activities.petHotel.HotelLocationSelect;
import com.example.together.adapter.SearchAdapter;

import java.util.ArrayList;

public class locationFrag2 extends Fragment {

    static String local;


    public String[] addList;

    RecyclerView mRecycleView;
    RecyclerView.LayoutManager mLayoutManager;

    public static locationFrag2 newInstance() {
        return new locationFrag2();
    }

    public static void localreplace(String addressName) {
        local = addressName;

    }

    // Fragment 의 layout 을 설정하기 위해서는 onCreateView() 메소드를 사용한다.
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.locationfragment2, container, false);

        //recyclerview 선언
        mRecycleView = v.findViewById(R.id.location_recycler);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(mLayoutManager);

        if(local.equals("서울")){
            addList = new String[]{"강동구","강서구","강남구","강북구","관악구","광진구","구로구","금천구","동대문구","서초구","송파구","영등포구","은평구","용산구","종로","마포구","도봉구","노원구"};
        }else if(local.equals("대구")){
            addList = new String[]{"동구","서구","대덕구","북구","남구","중구","유성구"};
        }else if(local.equals("강원도")){
            addList = new String[]{"강릉시","고성군","동해시","삼척시","속초시","양구군","양양군","영월군","원주시","철원군","춘천시"};
        }else if(local.equals("경북")){
            addList = new String[]{"경산시","경주시","고령군","구미시","군위군","김천시","문경시","봉화군","상주시","칠곡군","청도군","영천시","의성군","울진군"};
        }else if(local.equals("경기도")){
            addList = new String[]{"가평군","구리시","광주시","광명시","남양주시","부천시","수원시","과천시","동두천시","안양시","양주시","양평군","여주시","연천군"};
        }else{
            addList = new String[]{"미기입지역"};
        }

        SearchAdapter myadapter = new SearchAdapter(addList,local,getActivity());

        mRecycleView.setAdapter(myadapter);

        return v;


    }

}
