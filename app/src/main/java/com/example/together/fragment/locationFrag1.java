package com.example.together.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.together.R;
import com.example.together.activities.petHotel.HotelLocationSelect;

public class locationFrag1 extends Fragment {

    locationFrag2 fragment2;

    TextView Seoul,Gyeonggi,Gangwon,Jeonbuk,Jeonnam,Daegu,Kyungbuk,Kyungnam,Busan,Ulsan,Pohang,Jeaju;


    public static locationFrag1 newInstance() {
            return new locationFrag1();
    }

    // Fragment 의 layout 을 설정하기 위해서는 onCreateView() 메소드를 사용한다.
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.locationfragment1, container, false);

        //text뷰에대한 클릭이벤트 구성
        TextView.OnClickListener onClickListener = new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                String AddressName;
                switch (view.getId()) {
                    case R.id.Seoul :
                        AddressName = "서울";
                        ((HotelLocationSelect)getActivity()).replaceFragment(locationFrag2.newInstance(),AddressName);
                        break ;

                    case R.id.Gyeonggi :
                        AddressName = "경기도";
                        ((HotelLocationSelect)getActivity()).replaceFragment(locationFrag2.newInstance(),AddressName);
                        break ;

                    case R.id.Gangwon :
                        AddressName = "강원도";
                        ((HotelLocationSelect)getActivity()).replaceFragment(locationFrag2.newInstance(),AddressName);
                        break ;

                    case R.id.Jeonbuk:
                        AddressName = "전북";
                        ((HotelLocationSelect)getActivity()).replaceFragment(locationFrag2.newInstance(),AddressName);
                        break;

                    case R.id.Jeonnam:
                        AddressName = "전남";
                        ((HotelLocationSelect)getActivity()).replaceFragment(locationFrag2.newInstance(),AddressName);
                        break;

                    case R.id.Daegu:
                        AddressName = "대구";
                        ((HotelLocationSelect)getActivity()).replaceFragment(locationFrag2.newInstance(),AddressName);
                        break;

                    case R.id.Kyungbuk:
                        AddressName = "경북";
                        ((HotelLocationSelect)getActivity()).replaceFragment(locationFrag2.newInstance(),AddressName);
                        break;

                    case R.id.Kyungnam:
                        AddressName = "경남";
                        ((HotelLocationSelect)getActivity()).replaceFragment(locationFrag2.newInstance(),AddressName);
                        break;

                    case R.id.Busan:
                        AddressName = "부산";
                        ((HotelLocationSelect)getActivity()).replaceFragment(locationFrag2.newInstance(),AddressName);
                        break;

                    case R.id.Ulsan:
                        AddressName = "울산";
                        ((HotelLocationSelect)getActivity()).replaceFragment(locationFrag2.newInstance(),AddressName);
                        break;

                    case R.id.Pohang:
                        AddressName = "포항";
                        ((HotelLocationSelect)getActivity()).replaceFragment(locationFrag2.newInstance(),AddressName);
                        break;

                    case R.id.Jeaju:
                        AddressName = "제주";
                        ((HotelLocationSelect)getActivity()).replaceFragment(locationFrag2.newInstance(),AddressName);
                        break;
                }
            }
        } ;

        //시군구를위한 정리
        Seoul = v.findViewById(R.id.Seoul);
        Seoul.setOnClickListener(onClickListener) ;

        Gyeonggi = v.findViewById(R.id.Gyeonggi);
        Gyeonggi.setOnClickListener(onClickListener) ;

        Gangwon = v.findViewById(R.id.Gangwon);
        Gangwon.setOnClickListener(onClickListener) ;

        Jeonbuk = v.findViewById(R.id.Jeonbuk);
        Jeonbuk.setOnClickListener(onClickListener) ;

        Jeonnam = v.findViewById(R.id.Jeonnam);
        Jeonnam.setOnClickListener(onClickListener) ;

        Daegu = v.findViewById(R.id.Daegu);
        Daegu.setOnClickListener(onClickListener) ;

        Kyungbuk = v.findViewById(R.id.Kyungbuk);
        Kyungbuk.setOnClickListener(onClickListener) ;

        Kyungnam = v.findViewById(R.id.Kyungnam);
        Kyungnam.setOnClickListener(onClickListener) ;

        Busan = v.findViewById(R.id.Busan);
        Busan.setOnClickListener(onClickListener) ;

        Ulsan = v.findViewById(R.id.Ulsan);
        Ulsan.setOnClickListener(onClickListener) ;

        Pohang = v.findViewById(R.id.Pohang);
        Pohang.setOnClickListener(onClickListener) ;

        Jeaju = v.findViewById(R.id.Jeaju);
        Jeaju.setOnClickListener(onClickListener) ;


        return v;
    }



}
