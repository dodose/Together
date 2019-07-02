package com.example.together.activities.my_petInfo;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.together.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;


import java.util.Calendar;
import java.util.HashMap;

public class MyPetRegActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;
    private Uri mImageUri;
    private StorageTask uploadTask;
    StorageReference storageReference;
    private FirebaseAuth firebaseAuth;

    private String petimageUrl;

    private static final String TAG = "MyPetRegActivity";



    ImageView mImage_profile;
    TextView mDisplayDate, mTv_change;
    EditText mPetName, mIntro, mPetBreed, mPetWeight;
    RadioGroup mGenderGroup;
    RadioButton mMale, mFemale;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    Button mAdd_mypet, cancel;

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pet_reg);

        mTv_change = findViewById(R.id.tv_change);
        mImage_profile = findViewById(R.id.image_profile);
        mAdd_mypet = findViewById(R.id.add_mypet);
        mDisplayDate = findViewById(R.id.dog_birthday);
        mPetName = findViewById(R.id.petName);
        mPetBreed = findViewById(R.id.petBreed);
        mPetWeight = findViewById(R.id.petWeight);
        mIntro = findViewById(R.id.intro);
        mMale = findViewById(R.id.male);
        mFemale = findViewById(R.id.female);
        mGenderGroup = findViewById(R.id.genderGroup);
        cancel = findViewById(R.id.cancel);



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

                String date = year + "-"+month+1 +"-"+dayOfMonth;
                final int birthday = year+month+dayOfMonth;
                mDisplayDate.setText(date);
            }
        };


        //취소버튼
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyPetRegActivity.this, MyPetListActivity.class));
                finish();
            }
        });


        //등록하기 버튼
        mAdd_mypet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                reference = FirebaseDatabase.getInstance().getReference("Pets").child(firebaseUser.getUid());

                String petid = reference.push().getKey();



                RadioGroup rg = (RadioGroup)findViewById(R.id.genderGroup);
                RadioButton seletedRdo = (RadioButton)findViewById(rg.getCheckedRadioButtonId());
                final String selectedValue = seletedRdo.getText().toString();


                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("petname", mPetName.getText().toString().trim());
                hashMap.put("petbreed", mPetBreed.getText().toString().trim());
                hashMap.put("petweight", mPetWeight.getText().toString());
                hashMap.put("birthday", mDisplayDate.getText().toString());
                hashMap.put("intro", mIntro.getText().toString());
                hashMap.put("gender", selectedValue);
                hashMap.put("petimageurl", petimageUrl);
                hashMap.put("petid", petid);


                reference.child(petid).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(MyPetRegActivity.this, MyPetListActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });

                startActivity(new Intent(MyPetRegActivity.this, MyPetListActivity.class));
                finish();

            }
        });



        mGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override

            public void onCheckedChanged(RadioGroup genderGroup, int checkedId) {

                final RadioButton radio_btn = (RadioButton) findViewById(checkedId);

                Toast.makeText(MyPetRegActivity.this, radio_btn.getText() + "체크", Toast.LENGTH_SHORT).show();

                switch (checkedId) {

                    case R.id.male:
                        break;

                    case R.id.female:
                        break;
                }

            }
        });


        // 편집
        mTv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(1, 1)
                        .start(MyPetRegActivity.this);
            }


        });


        mImage_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(1, 1)
                        .start(MyPetRegActivity.this);
            }
        });


    }



    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    private void uploadImage(){
        // Progress .xml에서 Visible 하는거 구현

        storageReference = FirebaseStorage.getInstance().getReference().child("myPet_image");


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
                         petimageUrl = downloadUri.toString();
                        Log.wtf(TAG, "onComplete: 이미지 뭘까"+petimageUrl );


                    } else {
                        showMessage("Failed");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    showMessage(e.getMessage());
                }
            });

        }else {
            showMessage("이미지를 선택해주세요");
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            mImageUri = result.getUri();
            mImage_profile.setImageURI(mImageUri);



            Log.wtf(TAG, "onActivityResult 되느냥?"+mImageUri );
            uploadImage();


        }else {
            showMessage("무엇인가 잘못되었습니다.");
        }

    }



    private void showMessage(String text) {

        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();

    }





}
