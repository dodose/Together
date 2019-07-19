package com.example.together.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.example.together.model.Pet;
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


public class PetBunYangInfoEditFragment extends Fragment {

    private static final String TAG = "PetBunYangInfoEditFragment";
    public static String petcode;

    public static ImageView myPetImage, gender_m, gender_w;
    public  static TextView myPetName, myPetBreed, myPetAge;
    RadioGroup having;
    RadioButton yes, no;
//    String beforecode;

    FirebaseUser firebaseUser;
    DatabaseReference reference;



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
        myPetAge = view.findViewById(R.id.petAge);
     //   petAge = view.findViewById(R.id.petAge);

        // 내 펫 이미지
        myPetImage = view.findViewById(R.id.myPetImage);


        // 성별 아이콘
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
