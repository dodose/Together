package com.example.together.activities.petHospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.together.R;
import com.example.together.activities.my_petInfo.MypetEditInfoActivity;

public class PetHospitalSelectBar extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton dpt_all, dpt_internal, dpt_surgery, dpt_skin, dpt_eyes, dpt_dentist, dpt_birth,dpt_etc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_hospital_select_bar);


        //radio group
        radioGroup = findViewById(R.id.radioGroup);

        //종합
        dpt_all = findViewById(R.id.dpt_all);
        //내과
        dpt_internal = findViewById(R.id.dpt_internal);
        //외과
        dpt_surgery = findViewById(R.id.dpt_surgery);
        //피부과
        dpt_skin = findViewById(R.id.dpt_skin);
        //안과
        dpt_eyes = findViewById(R.id.dpt_eyes);
        //치과
        dpt_dentist = findViewById(R.id.dpt_dentist);
        //산과
        dpt_birth = findViewById(R.id.dpt_birth);

        dpt_etc = findViewById(R.id.dpt_etc);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override

            public void onCheckedChanged(RadioGroup genderGroup, int checkedId) {

                final RadioButton radio_btn = (RadioButton) findViewById(checkedId);

                Toast.makeText(PetHospitalSelectBar.this, radio_btn.getText() + "체크", Toast.LENGTH_SHORT).show();

                Intent resultIntent = new Intent();

                switch (checkedId) {

                    case R.id.dpt_all:

                        resultIntent.putExtra("result",radio_btn.getText());
                        setResult(2,resultIntent);
                        finish();
                        break;
                    case R.id.dpt_internal:

                        resultIntent.putExtra("result",radio_btn.getText());
                        setResult(2,resultIntent);
                        finish();
                        break;

                    case R.id.dpt_birth:
                        resultIntent.putExtra("result",radio_btn.getText());
                        setResult(2,resultIntent);
                        finish();
                        break;

                    case R.id.dpt_dentist:
                        resultIntent.putExtra("result",radio_btn.getText());
                        setResult(2,resultIntent);
                        finish();
                        break;
                    case R.id.dpt_etc:
                        resultIntent.putExtra("result",radio_btn.getText());
                        setResult(2,resultIntent);
                        finish();
                        break;

                    case R.id.dpt_skin:
                        resultIntent.putExtra("result",radio_btn.getText());
                        setResult(2,resultIntent);
                        finish();
                        break;
                    case R.id.dpt_eyes:
                        resultIntent.putExtra("result",radio_btn.getText());
                        setResult(2,resultIntent);
                        finish();
                        break;

                    case R.id.dpt_surgery:
                        resultIntent.putExtra("result",radio_btn.getText());
                        setResult(2,resultIntent);
                        finish();
                        break;

                }

            }
        });

    }
}
