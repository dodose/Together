package com.example.together.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.together.R;
import com.example.together.activities.petching.PetchingActivity;
import com.example.together.model.Pet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class PetFriendsInfoEditFragment extends Fragment
{

    public static String petcode;

    public static ImageView myPetImage, gender_m, gender_w;
    public  static TextView myPetName, myPetBreed, myPetAge, petFriendIntro;

    Button regi_petching_friend;

    FirebaseUser firebaseUser;
    DatabaseReference reference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {


        final View view =  inflater.inflate(R.layout.fragment_pet_friends_info_edit, container, false);

        // Inflate the layout for this fragment

        // 텍스트 값들
        myPetName = view.findViewById(R.id.myPetName);
        myPetBreed = view.findViewById(R.id.myPetBreed);
        myPetAge = view.findViewById(R.id.petAge);
        //   petAge = view.findViewById(R.id.petAge);

        // 내 펫 이미지
        myPetImage = view.findViewById(R.id.myPetImage);


        // 성별 아이콘
        gender_m = view.findViewById(R.id.gender_m);
        gender_w = view.findViewById(R.id.gender_w);


        // 펫 소개
        petFriendIntro = view.findViewById(R.id.petFriendIntro);


        //펫프랜드 등록버튼
        regi_petching_friend = view.findViewById(R.id.regi_petching_friend);



        regi_petching_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                reference = FirebaseDatabase.getInstance().getReference("PetchingFriend").child(firebaseUser.getUid()).child(petcode);

                String pefriendid = reference.push().getKey();

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("intro_dog", petFriendIntro.getText().toString().trim());

                reference.child(pefriendid).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(getActivity(), PetchingActivity.class);
                            // 간단하게 Activity stack 처리하는 방법
                            // 참고 : http://mokiprogramming.blogspot.com/2014/01/activity-stack.html
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });

            }
        });


        return view;


    }


    public void myPetcode_petching(String selected_my_pet) {

        petcode = selected_my_pet;

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Pets").child(firebaseUser.getUid()).child(petcode);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Pet pet = dataSnapshot.getValue(Pet.class);
                    myPetName.setText(pet.getPetname());
                    myPetBreed.setText(pet.getPetbreed());


                    //만나이 계산
                    SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-M-dd");

                    Date time = new Date();
                    String time1 = format1.format(time);
                    System.out.println("시간아"+time1);
                    String splitTime[] = time1.split("-");
                    String year = splitTime[0]; //년도
                    String month = splitTime[1]; //월
                    String day = splitTime[2]; //일

                    int currentYear = Integer.parseInt(year);
                    int currentMonth = Integer.parseInt(month);
                    int currentDay = Integer.parseInt(day);


                    String splitPetBirthday[] = pet.getBirthday().split("-");
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

                    myPetAge.setText(age+"살");


                    if (pet.getGender()=="Female")
                    {
                        gender_w.setVisibility(View.VISIBLE);
                    }else
                    {
                        gender_m.setVisibility(View.VISIBLE);
                    }

                    Picasso.get().load(pet.getPetimageurl()).fit().into(myPetImage);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }







}
