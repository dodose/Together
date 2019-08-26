package com.example.together.activities.my_petInfo;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.together.R;
import com.example.together.activities.HomeActivity;
import com.example.together.activities.petHospital.PetHospitalPetConditionActivity;
import com.example.together.model.Pet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class MyPetInfoCheckActivity extends AppCompatActivity {
    //Extras 변수 선언
    String petUid;
    String petname;
    String intro;
    String petimage;
    String birthday;
    String gender;
    String breed;
    String weight;

    Toolbar toolbar;
    ImageView petImage;
    ImageButton petching,petclander,editpet;
    TextView Petname,petbreed,petGender,petweight,petintro,petbirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pet_info_check);

        Bundle Bx = getIntent().getExtras();

        if(Bx != null){

            petUid = Bx.getString("petUid");
//            petname = Bx.getString("petname");
//            intro = Bx.getString("intro");
//            petimage = Bx.getString("petimage");
//            birthday = Bx.getString("birthday");
//            gender = Bx.getString("gender");
//            breed = Bx.getString("breed");
//            weight = Bx.getString("weight");

        }

        //이미지 및 텍스트뷰
        petImage = findViewById(R.id.petImage);
        Petname = findViewById(R.id.Petname);
        petbreed = findViewById(R.id.petbreed);
        petGender = findViewById(R.id.petGender);
        petweight = findViewById(R.id.petweight);
        petintro = findViewById(R.id.intro);
        petbirthday = findViewById(R.id.birthday);


        //각종 버튼 SET
        petching = findViewById(R.id.petching);
        petclander = findViewById(R.id.petclander);
        editpet = findViewById(R.id.editpet);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_back);
        getSupportActionBar().setTitle("");

        FirebaseUser firebaseUser;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Pets").child(firebaseUser.getUid()).child(petUid);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("snapshot",dataSnapshot.getValue()+"");
                Pet pet = dataSnapshot.getValue(Pet.class);

                if (pet.getPetimageurl() == null)
                {
                    Intent intent = new Intent(MyPetInfoCheckActivity.this, MyPetListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
                Picasso.get().load(pet.getPetimageurl()).transform(new CircleTransform()).into(petImage);
                Petname.setText(pet.getPetname());
                petbirthday.setTypeface(null, Typeface.BOLD);
                petbirthday.setText(pet.getBirthday());

                if(pet.getGender().equals("Female")){
                    petGender.setTextColor(Color.RED);
                    petGender.setText("암컷");
                }else{
                    petGender.setTextColor(Color.BLUE);
                    petGender.setText("수컷");
                }

                if(Integer.parseInt(pet.getPetweight()) <= 10){
                    petweight.setText(pet.getPetweight()+" KG" + " (소형견)");
                }else if(Integer.parseInt(pet.getPetweight()) <= 20 && Integer.parseInt(pet.getPetweight()) > 10){
                    petweight.setText(pet.getPetweight()+" KG" + " (중형견)");
                }else{
                    petweight.setText(pet.getPetweight()+" KG" + " (대형견)");
                }

                petintro.setText(pet.getIntro());

                if (pet.getPetching_status().equals("yes"))
                {
                    petching.setBackgroundResource(R.drawable.petching_condition_not_null);
                }else
                {
                    petching.setBackgroundResource(R.drawable.petching_condition_null);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







        //Petching imageButton 클릭
        petching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPetInfoCheckActivity.this, MyPetchingCondition.class);
                intent.putExtra("petUid",petUid);
                intent.putExtra("petName",Petname.getText());
                startActivity(intent);
            }
        });




        //버튼 클릭 이벤트
        petclander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPetInfoCheckActivity.this, MypetCalendarActivity.class);
                intent.putExtra("petUid",petUid);
                intent.putExtra("petName",Petname.getText());
                startActivity(intent);
            }
        });

        editpet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPetInfoCheckActivity.this, MypetEditInfoActivity.class);
                intent.putExtra("petUid",petUid);
                startActivityForResult(intent,1);
            }

        });






    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_FIRST_USER){

            switch (requestCode){
                // MainActivity 에서 요청할 때 보낸 요청 코드 (3000)
                case 1:

                    Toast.makeText(MyPetInfoCheckActivity.this, "수정완료!", Toast.LENGTH_SHORT).show();
                    break;
            }

        }

    }

    class CircleTransform implements Transformation {

        boolean mCircleSeparator = false;

        public CircleTransform() {
        }

        public CircleTransform(boolean circleSeparator) {
            mCircleSeparator = circleSeparator;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }
            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
            Canvas canvas = new Canvas(bitmap);
            BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG);
            paint.setShader(shader);
            float r = size / 2f;
            canvas.drawCircle(r, r, r - 1, paint);
            // Make the thin border:
            Paint paintBorder = new Paint();
            paintBorder.setStyle(Paint.Style.STROKE);
            paintBorder.setColor(Color.argb(84, 0, 0, 0));
            paintBorder.setAntiAlias(true);
            paintBorder.setStrokeWidth(1);
            canvas.drawCircle(r, r, r - 1, paintBorder);

            // Optional separator for stacking:
            if (mCircleSeparator) {
                Paint paintBorderSeparator = new Paint();
                paintBorderSeparator.setStyle(Paint.Style.STROKE);
                paintBorderSeparator.setColor(Color.parseColor("#ffffff"));
                paintBorderSeparator.setAntiAlias(true);
                paintBorderSeparator.setStrokeWidth(4);
                canvas.drawCircle(r, r, r + 1, paintBorderSeparator);
            }
            squaredBitmap.recycle();
            return bitmap;
        }


        @Override
        public String key() {
            return "circle";
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true ;
    }


    //액션바 클릭 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(MyPetInfoCheckActivity.this, MyPetListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
