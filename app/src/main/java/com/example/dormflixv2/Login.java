package com.example.dormflixv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText mPassword,mEmail;
    TextView forgotPass;
    Boolean passwordVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPassword = findViewById(R.id.password);
        mEmail = findViewById(R.id.emailAdd);
        mAuth = FirebaseAuth.getInstance();
        forgotPass = findViewById(R.id.forgot);



        Button btnLog = findViewById(R.id.btnLog);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required");
                    return;
                }
                if(password.length() <8 || password.length() >12){
                    mPassword.setError("Password Must be 8 -12 characters");
                    return;
                }
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Login.this, "Login Successfully !", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), mainHome.class));

                        }else{
                            Toast.makeText(Login.this, "Error !" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        TextView tv1 = findViewById(R.id.signTxt);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    sMainHome();
            }
        });

        //first try of linking
        ImageButton ibLog = findViewById(R.id.ibLog);
        ibLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeLog();
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForgot();
            }
        });

    }

    private void openForgot() {
        Intent openFor = new Intent(this, forgot.class);
        startActivity(openFor);
    }
    private void closeLog() {
        Intent close = new Intent(this, splash.class);
        startActivity(close);
    }

    private void sMainHome(){
        Intent intL = new Intent(this, SignUp.class);
        startActivity(intL);
    }

    private void startMainHome() {
        Intent intentL = new Intent(this, mainHome.class);
            startActivity(intentL);
    }
}