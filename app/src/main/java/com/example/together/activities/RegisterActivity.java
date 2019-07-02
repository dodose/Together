package com.example.together.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.together.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText username, email, password, password2, phNumber, birthday;
    TextView change_password, changeGender;
    Button register;

    FirebaseAuth auth;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.register);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.regPassword);
        password2 = findViewById(R.id.regPassword2);
        phNumber = findViewById(R.id.phNumber);
        birthday = findViewById(R.id.birthday);

        change_password = findViewById(R.id.change_password);
        changeGender = findViewById(R.id.changeGender);


        auth = FirebaseAuth.getInstance();


        //비밀번호 확인란이 다를때
        password2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
                if (password.getText().toString().equals(password2.getText().toString())){
                    change_password.setTextColor(Color.BLUE);
                    change_password.setText("비밀번호가 일치합니다");
                }else{
                    change_password.setTextColor(Color.RED);
                    change_password.setText("비밀번호가 일치하지 않습니다!");
                }

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
            }

        });


        //성별 체크
        birthday.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                if(birthday.getText().length() == 7) {

                    if(birthday.getText().toString().substring(6).equals("1") || birthday.getText().toString().substring(6).equals("3")){
                        changeGender.setTextColor(Color.BLACK);
                        changeGender.setText("남");
                    }else if(birthday.getText().toString().substring(6).equals("2") || birthday.getText().toString().substring(6).equals("4")){
                        changeGender.setTextColor(Color.BLACK);
                        changeGender.setText("여");
                    }else{
                        changeGender.setTextColor(Color.RED);
                        changeGender.setText("잘못됨");
                    }

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
            }

        });





        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String str_username = username.getText().toString();
                String str_email = email.getText().toString();
                String str_password = password.getText().toString();
                String str_password2 = password2.getText().toString();
                String str_phnumber = phNumber.getText().toString();
                String str_birthday = birthday.getText().toString();

                if(TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_phnumber)|| TextUtils.isEmpty(str_birthday) ||
                        TextUtils.isEmpty(str_password) || TextUtils.isEmpty(str_password2)||TextUtils.isEmpty(str_username)){

                    showMessage("빈곳이 없는지 확인해주세요");

                }else{

                    register(str_username,str_email, str_password,str_phnumber,str_birthday);

                    //오라클 디비가 들어갈 예정입니다...

                }

            }
        });



    }



    private void showMessage(String text) {

        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();

    }


    private void register(final String username, String email, String password, final String phnumber, final String birthday){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username);
                            hashMap.put("ph_no",phnumber);
                            hashMap.put("birth_dt",birthday);
                            hashMap.put("bio", "");
                            hashMap.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/blogapp-a9a56.appspot.com/o/users_photos%2Fprofile.png?alt=media&token=a112f73c-373f-41ba-bd0f-dfea8ac8d6a1");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });

                        } else{
                            showMessage("이메일과 비밀번호중 무엇이 잘못되었습니다.");
                        }
                    }
                });
    }



}
