package com.example.together.activities.my_petInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.together.R;
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

import java.util.Calendar;
import java.util.HashMap;

public class MypetEditInfoActivity extends AppCompatActivity {

    ImageView petImage;
    Toolbar mToolbar;
    EditText petName,petBreed,petWeight,intro;
    TextView petBirthday,tv_change;
    RadioGroup genderGroup;
    RadioButton male,female;
    String inpetUid;

    String myUrl;

    FirebaseUser firebaseUser;

    DatePickerDialog.OnDateSetListener mDateSetListener;

    private Uri mImageUri;
    private StorageTask uploadTask;
    StorageReference storageRef;

    public static String TAG = "EditProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypet_edit_info);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Bundle Bx = getIntent().getExtras();

        if(Bx != null){
            inpetUid = Bx.getString("petUid");
        }

        petImage = findViewById(R.id.image_profile);
        petName = findViewById(R.id.petName);
        petBreed = findViewById(R.id.petBreed);
        petWeight = findViewById(R.id.petWeight);
        intro = findViewById(R.id.intro);
        petBirthday = findViewById(R.id.petBirthday);
        tv_change = findViewById(R.id.tv_change);

        genderGroup = findViewById(R.id.genderGroup);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Pets").child(firebaseUser.getUid()).child(inpetUid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("snapshot",dataSnapshot.getValue()+"");

                Pet pet = dataSnapshot.getValue(Pet.class);
                Glide.with(getApplicationContext()).load(pet.getPetimageurl()).into(petImage);
                petName.setText(pet.getPetname());
                petBreed.setText(pet.getPetbreed());
                petWeight.setText(pet.getPetweight());
                intro.setText(pet.getIntro());

                if(pet.getBirthday().equals("생년월일")){
                    petBirthday.setHint("아직 지정된 값이 없습니다 설정해주세요!");
                }else {
                    petBirthday.setText(pet.getBirthday());
                }

                if(pet.getGender().equals("Male")){
                    male.setChecked(true);
                }else{
                    female.setChecked(true);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //툴바 선언
        mToolbar = findViewById(R.id.edit_toolbar);
        setSupportActionBar(mToolbar);


        //툴바 액션바 왼쪽에 버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_back);
        getSupportActionBar().setTitle("");


        petImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(1, 1)
                        .start(MypetEditInfoActivity.this);
            }
        });


        tv_change.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(1, 1)
                        .start(MypetEditInfoActivity.this);
            }


        });

        petBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(
                                MypetEditInfoActivity.this,
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
                int mm = month+1;
                String date = year + "-"+ mm +"-"+dayOfMonth;

                petBirthday.setText(date);
            }
        };

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override

            public void onCheckedChanged(RadioGroup genderGroup, int checkedId) {

                final RadioButton radio_btn = (RadioButton) findViewById(checkedId);

//                Toast.makeText(MypetEditInfoActivity.this, radio_btn.getText() + "체크", Toast.LENGTH_SHORT).show();

                switch (checkedId) {

                    case R.id.male:
                        break;

                    case R.id.female:
                        break;
                }

            }
        });



    }

    //액션바 등록
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu) ;

        return true ;
    }


    //액션바 클릭 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                //                Intent intent = new Intent(ProductOrderActivity.this,HotelDetailActivity.class);
                //                startActivity(intent);
                return true;
            case R.id.edit:


                updatePetinfo(petName.getText().toString(),
                        petBirthday.getText().toString(),
                        petBreed.getText().toString(),
                        petWeight.getText().toString(),
                intro.getText().toString());

                Intent resultIntent = new Intent();
                setResult(1,resultIntent);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }



    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void updatePetinfo(String petname, String petbirthday, String petbreed, String petweight, String intro) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Pets").child(firebaseUser.getUid()).child(inpetUid);

        RadioGroup rg = (RadioGroup)findViewById(R.id.genderGroup);
        RadioButton seletedRdo = (RadioButton)findViewById(rg.getCheckedRadioButtonId());
        final String selectedValue = seletedRdo.getText().toString();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("petname", petname);
        hashMap.put("petbreed", petbreed);
        hashMap.put("intro", intro);
        hashMap.put("petweight",petweight);
        hashMap.put("birthday",petbirthday);
        hashMap.put("gender",selectedValue);

        reference.updateChildren(hashMap);

    }

    private void uploadImage(){

        storageRef = FirebaseStorage.getInstance().getReference().child("myPet_image");

        //To show ProgressBar

        if (mImageUri != null){
            final StorageReference filereference = storageRef.child(System.currentTimeMillis()
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

                        Log.d(TAG, "onComplete: "+myUrl+"주소");

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Pets").child(firebaseUser.getUid()).child(inpetUid);

                                    reference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            Glide.with(getApplicationContext()).load(myUrl).into(petImage);

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("petimageurl", ""+myUrl);

                        reference.updateChildren(hashMap);


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

            Log.e(TAG, "onActivityResult: TAG 실행된 결과값오나요? "+mImageUri+"ImageUri" );

            uploadImage();




        }else {
            showMessage("Somethings wrong");
        }
    }

    private void showMessage(String text) {

        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();

    }

}
