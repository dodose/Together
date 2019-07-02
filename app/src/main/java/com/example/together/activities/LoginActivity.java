package com.example.together.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.together.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    ImageView mTogether_loading, login_photo;
    EditText email, password;
    Button signUp, loginBtn;


    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    public void onStart(){
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class );
            startActivity(intent);
            finish();
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = findViewById(R.id.login_mail);
        password = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.loginBtn);
        signUp = findViewById(R.id.register);
        login_photo  = findViewById(R.id.login_photo);

        auth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String str_email = email.getText().toString();
                String str_passowrd = password.getText().toString();

                if(TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_passowrd)){
                    showMessage("이메일과 패스워드 모두 기입해주세요");
                }else{
                    loading();
                    auth.signInWithEmailAndPassword(str_email, str_passowrd)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()){
                                        loading_disable();
                                    }

                                    if(task.isSuccessful()){
                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users")
                                                .child(auth.getCurrentUser().getUid());

                                        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }else {
                                        showMessage(task.getException().getMessage());
                                    }
                                }
                            });
                }
            }
        });



    }

    private  void loading(){
        mTogether_loading = findViewById(R.id.together_loading);
        mTogether_loading.setVisibility(View.VISIBLE);

        email.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);
        loginBtn.setVisibility(View.INVISIBLE);
        signUp.setVisibility(View.INVISIBLE);
        login_photo.setVisibility(View.INVISIBLE);

        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(mTogether_loading);
        Glide.with(this).load(R.raw.together_loading).into(imageViewTarget);

    }

    private void loading_disable(){

        mTogether_loading.setVisibility(View.INVISIBLE);
        email.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        loginBtn.setVisibility(View.VISIBLE);
        signUp.setVisibility(View.VISIBLE);
        login_photo.setVisibility(View.VISIBLE);
    }




    private void showMessage(String text) {

        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();

    }
}