package com.example.blogapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blogapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText username, fullname, email, password, password2;
    Button register;

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.register);
        username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.regPassword);
        password2 = findViewById(R.id.regPassword2);

        auth = FirebaseAuth.getInstance();





        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String str_username = username.getText().toString();
                String str_fullname = fullname.getText().toString();
                String str_email = email.getText().toString();
                String str_password = password.getText().toString();
                String str_password2 = password2.getText().toString();

                if(TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_fullname) || TextUtils.isEmpty(str_password)
                        || TextUtils.isEmpty(str_password2)||TextUtils.isEmpty(str_username)){

                    showMessage("빈곳이 없는지 확인해주세요");

                }else if(!str_password.equals(str_password2)){
                    showMessage("Password is not same.");
                }else if(str_password.length() < 6){
                    showMessage("비밀번호의 길이는 6자 이상이어야 합니다.");
                }else{

                    register(str_username, str_fullname, str_email, str_password);

                }

            }
        });



    }



    private void showMessage(String text) {

        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();

    }


    private void register(final String username, final String fullname, String email, String password){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username.toLowerCase());
                            hashMap.put("fullname", fullname);
                            hashMap.put("bio", "");
                            hashMap.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/blogapp-a9a56.appspot.com/o/users_photos%2Fimage%3A15?alt=media&token=014d5eac-d890-43b1-8d5b-ba5090d5a6db");

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
                            showMessage(task.getException().getMessage());
                        }
                    }
                });
    }



}
