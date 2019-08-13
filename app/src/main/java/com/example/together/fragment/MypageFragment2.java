package com.example.together.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.together.R;

public class MypageFragment2 extends Fragment {

    // Fragment 의 layout 을 설정하기 위해서는 onCreateView() 메소드를 사용한다.
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mypage_two, container, false);

        return view;
    }

}
