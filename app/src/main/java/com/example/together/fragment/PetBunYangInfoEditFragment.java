package com.example.together.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.together.R;
import com.example.together.activities.petching.PetchingActivity;
import com.example.together.activities.petching.PetchingSelectPetActivity;
import com.example.together.model.Pet;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;


public class PetBunYangInfoEditFragment extends Fragment {


    private static final String TAG = "PetBunYangInfoEditFragment";
    public static String petcode;


    public static ImageView myPetImage, gender_m, gender_w, image_blood_certification;
    public static TextView myPetName, myPetBreed, myPetAge, petBunyangIntro, specail_note, blood_certi_img_upload;
    RadioGroup having;
    RadioButton yes, no;
    Button regi_petching_bunyang;

    public static String petName;
    public static String petBreed;
    public static String petImg;
    public static String petGender;
    public static int age;


    //이미지 업로드
    private Uri mImageUri;
    private StorageTask uploadTask;
    StorageReference storageReference;
    private String myUrl;

    private final int PICK_IMAGE_REQUEST = 71;



//    String beforecode;

    FirebaseUser firebaseUser;
    DatabaseReference reference;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        storageReference = FirebaseStorage.getInstance().getReference("blood");

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_pet_bun_yang_info_edit, container, false);


        // 개별 버튼
        yes = view.findViewById(R.id.yes);
        no = view.findViewById(R.id.no);

        // 텍스트 값들
        myPetName = view.findViewById(R.id.myPetName);
        myPetBreed = view.findViewById(R.id.myPetBreed);
        myPetAge = view.findViewById(R.id.petAge);
        blood_certi_img_upload = view.findViewById(R.id.blood_certi_img_upload);
        //   petAge = view.findViewById(R.id.petAge);

        // 내 펫 이미지
        myPetImage = view.findViewById(R.id.myPetImage);


        // 성별 아이콘
        gender_m = view.findViewById(R.id.gender_m);
        gender_w = view.findViewById(R.id.gender_w);


        // 분양 내용
        petBunyangIntro = view.findViewById(R.id.petBunyangIntro);

        // 분양 특이사항
        specail_note = view.findViewById(R.id.specail_note);

        // 종 보유여부
        having = view.findViewById(R.id.having);


        // 등록버튼
        regi_petching_bunyang = view.findViewById(R.id.regi_petching_bunyang);

        // 혈통증명서 업로드
        blood_certi_img_upload = view.findViewById(R.id.blood_certi_img_upload);

        // 혈통증명서 담을 이미지
        image_blood_certification = view.findViewById(R.id.image_blood_certification);



        blood_certi_img_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });


        // 라디오 참고 https://bitsoul.tistory.com/47

        having.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                final RadioButton radio_btn = (RadioButton) view.findViewById(checkedId);

                Toast.makeText(getActivity(), radio_btn.getText() + "체크", Toast.LENGTH_SHORT).show();

                switch (checkedId) {

                    case R.id.yes:
                        blood_certi_img_upload.setVisibility(View.VISIBLE);
                        break;

                    case R.id.no:
                        blood_certi_img_upload.setVisibility(View.GONE);
                        break;
                }
            }
        });


        // 버튼 클릭시 값을 firebase에 담고 화면전환
        regi_petching_bunyang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                reference = FirebaseDatabase.getInstance().getReference("PetchingBunyang");

                String petbunyangid = reference.push().getKey();


                // 라디오 버튼 (혈통 증명서 보유여부)
                having = view.findViewById(R.id.having);
                RadioButton seletedRdo = view.findViewById(having.getCheckedRadioButtonId());
                final String selectedValue = seletedRdo.getText().toString();

                Log.d("test", "onClick: "+petName);



                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("having_certificate", selectedValue);
                hashMap.put("intro_dog", petBunyangIntro.getText().toString().trim());
                hashMap.put("specail_note", specail_note.getText().toString().trim());
                hashMap.put("petbunyangid", petbunyangid);
                hashMap.put("petName", petName);
                hashMap.put("petGender", petGender);
                hashMap.put("petBreed", petBreed);
                hashMap.put("petImg",petImg);
                hashMap.put("petcode", petcode);
                hashMap.put("owner", firebaseUser.getUid());
                Log.d("나이", "나이: "+age);
                hashMap.put("age", String.valueOf(age));
                hashMap.put("image_blood_certification",myUrl);



                reference.child(petbunyangid).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
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

                    petName = pet.getPetname();
                    petBreed = pet.getPetbreed();
                    petGender = pet.getGender();
                    petImg = pet.getPetimageurl();


                    //만나이 계산
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


                    String splitPetBirthday[] = pet.getBirthday().split("-");
                    String Birthday_Year = splitPetBirthday[0];
                    String Birthday_Month = splitPetBirthday[1];
                    String Birthday_Day = splitPetBirthday[2];

                    int petBirthYear = Integer.parseInt(Birthday_Year);
                    int petBirthMonth = Integer.parseInt(Birthday_Month);
                    int petBirthday_Day = Integer.parseInt(Birthday_Day);

                    age = currentYear - petBirthYear;
                    if (petBirthMonth * 100 + petBirthday_Day > currentMonth * 100 + currentDay) {
                        age--;
                    }

                    myPetAge.setText(age + "살");


                    if (pet.getGender().equals("Female")) {

                        gender_m.setVisibility(View.GONE);
                        gender_w.setVisibility(View.VISIBLE);

                    } else {

                        gender_w.setVisibility(View.GONE);
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


    //이미지 업로드
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getActivity().getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void uploadImage(){
        // Progress .xml에서 Visible 하는거 구현

        //To show ProgressBar

        if (mImageUri != null){
            final StorageReference filereference = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(mImageUri));

            uploadTask = filereference.putFile(mImageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return filereference.getDownloadUrl();
                }

            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        myUrl = downloadUri.toString();

                        Log.d("혈통주소", "onComplete: "+myUrl+"주소");
                        Log.d("빅뱅", ""+petcode);

                    } else {

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println(e.getMessage()+"이미지 분양 업로드 에러");
                }
            });

        }else {
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("장난감", "여기오나" );
        Log.d("공기", "onActivityResult: "+requestCode);
        Log.d("공기밥", "onActivityResult: "+resultCode);
        Log.d("공기돌", "onActivityResult: "+data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {

            mImageUri = data.getData();
            Log.d("업로드", ""+mImageUri);
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mImageUri);
                image_blood_certification.setImageBitmap(bitmap);
            }catch (IOException e)
            {
                e.printStackTrace();
            }


            uploadImage();


        }else {

        }
    }


}