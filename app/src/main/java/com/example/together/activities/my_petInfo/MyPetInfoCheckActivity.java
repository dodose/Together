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
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.together.R;
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
            petname = Bx.getString("petname");
            intro = Bx.getString("intro");
            petimage = Bx.getString("petimage");
            birthday = Bx.getString("birthday");
            gender = Bx.getString("gender");
            breed = Bx.getString("breed");
            weight = Bx.getString("weight");

        }

        //이미지 및 텍스트뷰
        petImage = findViewById(R.id.petImage);
        Petname = findViewById(R.id.Petname);
        petbreed = findViewById(R.id.petbreed);
        petGender = findViewById(R.id.petGender);
        petweight = findViewById(R.id.petweight);
        petintro = findViewById(R.id.intro);
        petbirthday = findViewById(R.id.birthday);

        Log.e("img",petimage);

        Picasso.get().load(petimage).transform(new CircleTransform()).into(petImage);



        Petname.setText(petname);



        if (birthday.equals("생년월일")){
            petbirthday.setTypeface(null, Typeface.BOLD);
            petbirthday.setText("(생년월일을 설정해주세요)");
        }else{
            petbirthday.setText(birthday+ "출생");
        }



        if(gender.equals("Female")){
            petGender.setTextColor(Color.RED);
            petGender.setText("암컷");
        }else{
            petGender.setTextColor(Color.BLUE);
            petGender.setText("수컷");
        }

        if(Integer.parseInt(weight) <= 10){
            petweight.setText(weight+" KG" + " (소형견)");
        }else if(Integer.parseInt(weight) <= 20 && Integer.parseInt(weight) > 10){
            petweight.setText(weight+" KG" + " (중형견)");
        }else{
            petweight.setText(weight+" KG" + " (대형견)");
        }

        petintro.setText(intro);

        //각종 버튼 SET
        petching = findViewById(R.id.petching);
        petclander = findViewById(R.id.petclander);
        editpet = findViewById(R.id.editpet);




        //버튼 클릭 이벤트
        petclander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPetInfoCheckActivity.this, MypetCalendarActivity.class);
                intent.putExtra("petUid",petUid);
                intent.putExtra("petName",petname);
                startActivity(intent);
            }
        });

        editpet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(MyPetInfoCheckActivity.this, MypetEditInfoActivity.class);
                    intent.putExtra("petUid",petUid);
                intent.putExtra("petbreed",petbreed.getText());
                intent.putExtra("petgender",gender);
                intent.putExtra("petweight",weight);
                intent.putExtra("petbirthday",petbirthday.getText());
                intent.putExtra("petintro",petintro.getText());
                intent.putExtra("petname",petname);
                intent.putExtra("petimage",petimage);
                startActivity(intent);
            }

        });




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

}
