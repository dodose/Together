package com.example.together.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;


import androidx.fragment.app.Fragment;

import com.example.together.R;
import com.example.together.activities.petHospital.PetHospitalCheckReservationInfoActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class Hos_or_fragment1 extends Fragment {

    private CheckBox canser_heat,canser_prevent,canser_eczema,canser_skin,
                canser_convul,canser_reduction,canser_vomiting,canser_mites,
                canser_body,canser_appetite,canser_born,canser_missing;

    static EditText detail_canser_text;

    static ArrayList<String> pet_canser;



    public static ArrayList<String> CanserLIst() {
        return pet_canser;
    }

    public static String editCanser() {
        return detail_canser_text.getText().toString();
    }


    // Fragment 의 layout 을 설정하기 위해서는 onCreateView() 메소드를 사용한다.
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.hos_or_fragment1, container, false);
        pet_canser = new ArrayList<String>();

        canser_heat =  v.findViewById(R.id.canser_heat);
        canser_prevent = v.findViewById(R.id.canser_prevent);
        canser_eczema= v.findViewById(R.id.canser_eczema);
        canser_skin= v.findViewById(R.id.canser_skin);
        canser_convul= v.findViewById(R.id.canser_convul);
        canser_reduction= v.findViewById(R.id.canser_reduction);
        canser_vomiting= v.findViewById(R.id.canser_vomiting);
        canser_mites= v.findViewById(R.id.canser_mites);
        canser_body= v.findViewById(R.id.canser_body);
        canser_appetite= v.findViewById(R.id.canser_appetite);
        canser_born= v.findViewById(R.id.canser_born);
        canser_missing= v.findViewById(R.id.canser_missing);

        detail_canser_text = v.findViewById(R.id.detail_canser_text);


        canser_heat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()){
                    pet_canser.add("발열");
                    Log.e("체크",pet_canser+"");

                }else{
                    pet_canser.remove("발열");
                    Log.e("체크",pet_canser+"");
//
                }
            }
        });

        canser_prevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()){
                    pet_canser.add("예방접종");
                    Log.e("체크",pet_canser+"");
//
                }else{
                    pet_canser.remove("예방접종");
                    Log.e("체크",pet_canser+"");
//
                }
            }
        });

        canser_eczema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()){
                    pet_canser.add("습진");
                    Log.e("체크",pet_canser+"");
//
                }else{
                    pet_canser.remove("습진");
                    Log.e("체크",pet_canser+"");
//
                }
            }
        });

        canser_skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()){
                    pet_canser.add("피부병");
                    Log.e("체크",pet_canser+"");
//
                }else{
                    pet_canser.remove("피부병");
                    Log.e("체크",pet_canser+"");

                }
            }
        });

        canser_convul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()){
                    pet_canser.add("경련");
                    Log.e("체크",pet_canser+"");

                }else{
                    pet_canser.remove("경련");
                    Log.e("체크",pet_canser+"");

                }
            }
        });

        canser_reduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()){
                    pet_canser.add("체중감소");
                    Log.e("체크",pet_canser+"");

                }else{
                    pet_canser.remove("체중감소");
                    Log.e("체크",pet_canser+"");

                }
            }
        });

        canser_mites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()){
                    pet_canser.add("진드기");
                    Log.e("체크",pet_canser+"");

                }else{
                    pet_canser.remove("진드기");
                    Log.e("체크",pet_canser+"");

                }
            }
        });

        canser_vomiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()){
                    pet_canser.add("구토");
                    Log.e("체크",pet_canser+"");

                }else{
                    pet_canser.remove("구토");
                    Log.e("체크",pet_canser+"");

                }
            }
        });

        canser_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()){
                    pet_canser.add("발작");
                    Log.e("체크",pet_canser+"");

                }else{
                    pet_canser.remove("발작");
                    Log.e("체크",pet_canser+"");

                }
            }
        });

        canser_appetite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()){
                    pet_canser.add("식욕변화");
                    Log.e("체크",pet_canser+"");

                }else{
                    pet_canser.remove("식욕변화");
                    Log.e("체크",pet_canser+"");

                }
            }
        });

        canser_born.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()){
                    pet_canser.add("탈골");
                    Log.e("체크",pet_canser+"");

                }else{
                    pet_canser.remove("탈골");
                    Log.e("체크",pet_canser+"");

                }
            }
        });

        canser_missing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()){
                    pet_canser.add("털빠짐");
                    Log.e("체크",pet_canser+"");

                }else{
                    pet_canser.remove("털빠짐");
                    Log.e("체크",pet_canser+"");

                }
            }
        });




        return v;

    }



}
