package com.example.together.Activities.MyPetInfo;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;


import com.example.together.R;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyPetRegActivity extends AppCompatActivity {

    private TextView petBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pet_reg);

        petBirth = findViewById(R.id.petBirth);

        petBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(MyPetRegActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {

                    @Override

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        // TODO Auto-generated method stub

                        try {

                            Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);



                        } catch (Exception e) {

                            // TODO: handle exception

                            e.printStackTrace();

                        }

                    }

                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));



                datePickerDialog.getDatePicker().setCalendarViewShown(false);

                datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                datePickerDialog.show();


            }
        });

    }


}


