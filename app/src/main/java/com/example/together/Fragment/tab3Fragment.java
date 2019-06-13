package com.example.together.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.together.R;


public class tab3Fragment extends Fragment {
    private static  final String TAG = "table3fr";

    private Button btnTest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.table3fr,container,false);

        btnTest = (Button) view.findViewById(R.id.button3);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Test CLiCK3",Toast.LENGTH_SHORT).show();
            }
        });


        return  view;
    }
}
