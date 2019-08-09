package com.example.together.activities.petHotel;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.together.R;

public class HotelFilterDialog extends Dialog {

    private MyDialogListener dialogListener;


    Button btn; //적용 버튼
    RadioGroup fillter_group;
    RadioButton one,two,three;
    ImageButton closefillter;

    RadioButton radio_btn;

    public HotelFilterDialog(Context context) {

        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
        setContentView(R.layout.activity_hotel_filter_dialog);     //다이얼로그에서 사용할 레이아웃입니다.


        fillter_group = findViewById(R.id.fillter_group);
        one = findViewById(R.id.one); //거리
        two = findViewById(R.id.two); // 가격
        three = findViewById(R.id.three); // 별점

        closefillter = findViewById(R.id.closefillter);

        closefillter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fillter = (String) radio_btn.getText();
                dialogListener.onPositiveClicked(fillter);

                Log.e("check된값",radio_btn.getText()+"");

                dismiss();   //다이얼로그를 닫는 메소드입니다.
            }
        });

        fillter_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override

            public void onCheckedChanged(RadioGroup genderGroup, int checkedId) {

                radio_btn = (RadioButton) findViewById(checkedId);

                Log.e("checkedBtn",radio_btn.getText()+"");

                switch (checkedId) {

                    case R.id.one:
                        break;
                    case R.id.two:
                        break;
                    case R.id.three:
                        break;

                }

            }
        });

    }

    public void setDialogListener(MyDialogListener dialogListener){
        this.dialogListener = dialogListener;
    }

    public interface MyDialogListener {
        public void onPositiveClicked(String fillter);

    }

}


