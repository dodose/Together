package com.example.together.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.together.R;
import com.example.together.activities.petching.PetchingSelectPetActivity;


public class PetBunYangInfoEditFragment extends Fragment {

    ImageView myPetImage, gender_m, gender_w;
    TextView myPetName, myPetBreed,petAge;
    RadioGroup having;
    RadioButton yes, no;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_pet_bun_yang_info_edit, container, false);

        // 라디오 그룹
        having = view.findViewById(R.id.having);

        // 개별 버튼
        yes = view.findViewById(R.id.yes);
        no = view.findViewById(R.id.no);

        // 텍스트 값들
        myPetName = view.findViewById(R.id.myPetName);
        myPetBreed = view.findViewById(R.id.myPetBreed);
        petAge = view.findViewById(R.id.petAge);

        // 이미지들
        myPetImage = view.findViewById(R.id.myPetImage);
        gender_m = view.findViewById(R.id.gender_m);
        gender_w = view.findViewById(R.id.gender_w);



        // 라디오 참고 https://bitsoul.tistory.com/47

        having.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId)
                {
                    case R.id.yes:
                        // String 값 담을거 리스트
                    case R.id.no:
                        // String 값 담을거 리스트
                        default:

                            break;
                }

            }
        });




        return view;

    }

}
