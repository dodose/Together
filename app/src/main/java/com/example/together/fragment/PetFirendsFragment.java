package com.example.together.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.together.R;


public class PetFirendsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        final View view =  inflater.inflate(R.layout.fragment_pet_firends, container, false);


        return view;


    }

}
