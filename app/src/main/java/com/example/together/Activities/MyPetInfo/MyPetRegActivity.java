package com.example.together.Activities.MyPetInfo;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.together.R;

import java.util.Calendar;

public class MyPetRegActivity extends AppCompatActivity {

    private static final String TAG = "MyPetRegActivity";

    private TextView mDisplayDate;
    private EditText petName, intro;
    private RadioGroup genderGroup;
    private RadioButton male, female;
    private DatePickerDialog.OnDateSetListener mDateSetListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pet_reg);


        mDisplayDate = findViewById(R.id.dog_birthday);
        petName = findViewById(R.id.petName);
        intro = findViewById(R.id.intro);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        genderGroup =  findViewById(R.id.genderGroup);





        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MyPetRegActivity.this,
                        android.R.style.Theme_NoTitleBar,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG, "onDateSet: "+year+"/" +month+"/"+dayOfMonth);

                String date = year + "/"+month +"/"+dayOfMonth;
                mDisplayDate.setText(date);
            }
        };




        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override

            public void onCheckedChanged(RadioGroup gender, int checkedId) {

                RadioButton radio_btn = (RadioButton) findViewById(checkedId);

				Toast.makeText(MyPetRegActivity.this, radio_btn.getText() + "체크", Toast.LENGTH_SHORT).show();

				switch (checkedId) {

				case R.id.male:
					break;

				case R.id.female:
					break;

				}
            }
        });




    }


}
