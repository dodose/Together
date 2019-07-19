package com.example.together.activities.petching;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.together.R;

public class PetchingBunyangDetailInfo extends AppCompatActivity {

    private TextView petName, petBreed, petAge, petBunyangIntro, specail_note, petBreedCertificatin;
    private ImageView bunyangPetImage, gender_m, gender_w;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petching_bunyang_detail_info);


        petName = findViewById(R.id.petName);
        petBreed = findViewById(R.id.petBreed);
        petAge = findViewById(R.id.petAge);

        petBunyangIntro = findViewById(R.id.petBunyangIntro);
        specail_note = findViewById(R.id.specail_note);
        petBreedCertificatin = findViewById(R.id.petBreedCertificatin);

        bunyangPetImage = findViewById(R.id.bunyangPetImage);
        gender_m = findViewById(R.id.gender_m);
        gender_w = findViewById(R.id.gender_w);


    }
}
