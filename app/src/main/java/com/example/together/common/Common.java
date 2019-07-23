package com.example.together.common;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;

import com.example.together.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Common {
    public static String IS_LOGIN = "IsLogin";
    public static User currentUser;


    //만나이 계산
    public String ageCalculator(String petBirthday) {

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-M-dd");

        Date time = new Date();
        String time1 = format1.format(time);
        System.out.println("시간아" + time1);
        String splitTime[] = time1.split("-");
        String year = splitTime[0]; //년도
        String month = splitTime[1]; //월
        String day = splitTime[2]; //일

        int currentYear = Integer.parseInt(year);
        int currentMonth = Integer.parseInt(month);
        int currentDay = Integer.parseInt(day);


        String splitPetBirthday[] = petBirthday.split("-");
        String Birthday_Year = splitPetBirthday[0];
        String Birthday_Month = splitPetBirthday[1];
        String Birthday_Day = splitPetBirthday[2];

        int petBirthYear = Integer.parseInt(Birthday_Year);
        int petBirthMonth = Integer.parseInt(Birthday_Month);
        int petBirthday_Day = Integer.parseInt(Birthday_Day);

        int age = currentYear - petBirthYear;
        if (petBirthMonth * 100 + petBirthday_Day > currentMonth * 100 + currentDay) {
            age--;

        }

        return String.valueOf(age);

    }


    // 성별판별
    public void distinctionGender(ImageView gender_m, ImageView gender_w, String gender)
    {
        if (gender.equals("Female"))
        {
            gender_w.setVisibility(View.VISIBLE);
            gender_m.setVisibility(View.GONE);
        }
        else
        {
            gender_w.setVisibility(View.GONE);
            gender_m.setVisibility(View.VISIBLE);
        }

        return;
    }


}


