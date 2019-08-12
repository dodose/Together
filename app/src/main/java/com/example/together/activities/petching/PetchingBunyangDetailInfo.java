package com.example.together.activities.petching;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.together.R;
import com.example.together.model.PetchingBunyang;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class PetchingBunyangDetailInfo extends AppCompatActivity {

    private TextView petName, petBreed, petAge, petBunyangIntro, specail_note, petBreedCertificatin;
    private ImageView bunyangPetImage, gender_m, gender_w;
    private Button bunyang_request, blood_certi_img_popup;
    public String owner;
    String petBunyangId;


    DatabaseReference reference;
    FirebaseUser firebaseUser;
    Dialog myDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        Intent intent = getIntent();
        petBunyangId = intent.getStringExtra("petBunyangId");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petching_bunyang_detail_info);
        myDialog = new Dialog(this);


        petName = findViewById(R.id.petName);
        petBreed = findViewById(R.id.petBreed);
        petAge = findViewById(R.id.petAge);

        petBunyangIntro = findViewById(R.id.petBunyangIntro);
        specail_note = findViewById(R.id.specail_note);
        petBreedCertificatin = findViewById(R.id.petBreedCertificatin);

        bunyangPetImage = findViewById(R.id.bunyangPetImage);
        gender_m = findViewById(R.id.gender_m);
        gender_w = findViewById(R.id.gender_w);

        bunyang_request = findViewById(R.id.bunyang_request);

        // 이미지 팝업 보기
        blood_certi_img_popup = findViewById(R.id.blood_certi_img_popup);



        reference = FirebaseDatabase.getInstance().getReference("PetchingBunyang").child(petBunyangId);
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PetchingBunyang petchingBunyang = dataSnapshot.getValue(PetchingBunyang.class);
                petName.setText(petchingBunyang.getPetName());
                petAge.setText(petchingBunyang.getAge()+"살");
                petBreed.setText(petchingBunyang.getPetBreed());
                Picasso.get().load(petchingBunyang.getPetImg()).fit().into(bunyangPetImage);

                owner = petchingBunyang.getOwner();


                if (petchingBunyang.getOwner().equals(firebaseUser.getUid()))
                {
                    bunyang_request.setVisibility(View.GONE);
                }




                if (petchingBunyang.getPetGender().equals("Female"))
                {
                    gender_w.setVisibility(View.VISIBLE);
                }else
                {
                    gender_m.setVisibility(View.VISIBLE);
                }

                petBreedCertificatin.setText(petchingBunyang.getHaving_certificate());
                if (petchingBunyang.getHaving_certificate().equals("예"))
                {
                    blood_certi_img_popup.setVisibility(View.VISIBLE);
                }



                petBunyangIntro.setText(petchingBunyang.getIntro_dog());
                specail_note.setText(petchingBunyang.getSpecail_note());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //분양신청 버튼 클릭시 정보 전달...

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        bunyang_request.setOnClickListener(v ->
        {

            Log.d("주인", "주인은: "+owner);
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Lounge").child("PetchingBunyang").child(owner).child("PetId").child(petBunyangId).child("Requestor");

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put(firebaseUser.getUid(),true);

            reference.setValue(hashMap);


            Intent lounge = new Intent(PetchingBunyangDetailInfo.this, PetchingActivity.class);
            startActivity(lounge);

        });


    }

    public void showPopup(View v)
    {

        TextView close;
        ImageView img_petching_blood_certification;
        myDialog.setContentView(R.layout.activity_petching_blood_certification_popup);

        close = myDialog.findViewById(R.id.close);
        img_petching_blood_certification = myDialog.findViewById(R.id.img_petching_blood_certification);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("PetchingBunyang").child(petBunyangId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PetchingBunyang petchingBunyang = dataSnapshot.getValue(PetchingBunyang.class);
                String imgBloodCertification = dataSnapshot.getValue(PetchingBunyang.class).getImage_blood_certification();


                Log.e("d",img_petching_blood_certification+"");
                Picasso.get().load(imgBloodCertification).fit().into(img_petching_blood_certification);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myDialog.show();


    }
}
