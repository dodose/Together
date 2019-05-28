package com.example.together.Activities.MyPetInfo;

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

import com.bumptech.glide.Glide;
import com.example.together.Activities.EditProfileActivity;
import com.example.together.Model.Pet;
import com.example.together.Model.User;
import com.example.together.R;
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
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;
import java.util.HashMap;

public class MyPetRegActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;
    private Uri mImageUri;
    private StorageTask uploadTask;
    StorageReference storageReference;
    Uri imageUri;
    String myUrl = "";

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
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pet_reg);

        storageReference = FirebaseStorage.getInstance().getReference("pets");

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

                String date = year + "년"+month +"월"+dayOfMonth+"일";
                int birthday = year+month+dayOfMonth;
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
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Pets");

                Pet pet = new Pet();

                pet.setPetname(mPetName.getText().toString().trim());
                pet.setPetbreed(mPetBreed.getText().toString().trim());
                pet.setPetweight(Integer.valueOf(mPetWeight.toString().trim()));
                pet.setBirthday(Integer.valueOf(mDisplayDate.toString().trim()));
                pet.setIntro(mIntro.getText().toString().trim());
                pet.setImageurl(myUrl);

                // 성별 채크


                RadioGroup rg = (RadioGroup)findViewById(R.id.genderGroup);
                RadioButton seletedRdo = (RadioButton)findViewById(rg.getCheckedRadioButtonId());
                final String selectedValue = seletedRdo.getText().toString();


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

                pet.setGender(selectedValue);



                reference.push().setValue(pet);

                startActivity(new Intent(MyPetRegActivity.this, MyPetListActivity.class));
                finish();
            }
        });



        mTv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(1, 1)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .start(MyPetRegActivity.this);
            }
        });


        mImage_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(1, 1)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .start(MyPetRegActivity.this);
            }
        });


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference().child("my_pets");


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Pets").child(firebaseUser.getUid());

    }







    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    private void uploadImage(){
        if(imageUri!= null){
            final StorageReference ref = storageReference.child(System.currentTimeMillis() + "."+getFileExtension(imageUri));
            uploadTask = ref.putFile(imageUri);



            Task urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()  {
                @Override
                public  Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }

                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        myUrl = downloadUri.toString();



                    }else {
                        showMessage("업로드에 실패하였습니다!");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    showMessage(e.getMessage());
                }
            });
        } else{
            showMessage("이미지를 골라주세요!");
        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            mImageUri = result.getUri();


        }else {
            showMessage("무엇인가 잘못되었습니다.");
        }

    }




    private void showMessage(String text) {

        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();

    }


}
