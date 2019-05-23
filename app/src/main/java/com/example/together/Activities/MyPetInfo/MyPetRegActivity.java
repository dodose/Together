package com.example.together.Activities.MyPetInfo;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.together.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;

public class MyPetRegActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;

    private static final String TAG = "MyPetRegActivity";

    TextView mDisplayDate;
    EditText mPetName, mIntro, mPetBreed, mPetWeight;
    RadioGroup mGenderGroup;
    RadioButton mMale, mFemale;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    Button mAdd_mypet;

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pet_reg);

        mAdd_mypet = findViewById(R.id.add_mypet);
        mDisplayDate = findViewById(R.id.dog_birthday);
        mPetName = findViewById(R.id.petName);
        mPetBreed = findViewById(R.id.petBreed);
        mPetWeight = findViewById(R.id.petWeight);
        mIntro = findViewById(R.id.intro);
        mMale = findViewById(R.id.male);
        mFemale = findViewById(R.id.female);
        mGenderGroup =  findViewById(R.id.genderGroup);



        mAdd_mypet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addPetInfo();

            }
        });




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






        mGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override

            public void onCheckedChanged(RadioGroup genderGroup, int checkedId) {

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






    private void addPetInfo() {

        String petName = mPetName.getText().toString();
        String intro = mIntro.getText().toString();
        String petBreed = mPetBreed.getText().toString();
        int petWeight = new Integer(mPetWeight.getText().toString());

    }


}
