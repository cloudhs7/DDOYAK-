package com.example.caucse.ddoyak_g;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.StringTokenizer;

public class Authentication extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonSignup;
    TextView textviewSingin;
    TextView textviewMessage;
    ProgressDialog progressDialog;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    public static String useremail;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        firebaseAuth = FirebaseAuth.getInstance();

        //이미 로그인되어있는 경우
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textviewSingin= (TextView) findViewById(R.id.textViewSignin);
        textviewMessage = (TextView) findViewById(R.id.textviewMessage);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        progressDialog = new ProgressDialog(this);

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        textviewSingin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    //create new user
    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("등록중입니다. 기다려주세요...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            StringTokenizer stringTokenizer = new StringTokenizer(email,"@");
                            useremail = stringTokenizer.nextToken();

                            Log.d("TAG", "useremail "+ useremail);
                            databaseReference = firebaseDatabase.getReference(useremail);
                            databaseReference.setValue("0");

                            Log.d("TAG", "onComplete: db");
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                        } else {
                            textviewMessage.setText("에러유형\n - 이미 등록된 이메일 \n - 암호 최소 6자리 이상 \n - 서버에러");
                            Toast.makeText(Authentication.this, "등록 에러!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
}
