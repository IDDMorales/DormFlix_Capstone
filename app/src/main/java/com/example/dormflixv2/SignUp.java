package com.example.dormflixv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText mFullName,mEmail,mPassword,mPhone, mConPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFullName = findViewById(R.id.editFName);
        mPhone = findViewById(R.id.editPNum);
        mEmail = findViewById(R.id.editEMail);
        mPassword = findViewById(R.id.editPass);
        mConPass = findViewById(R.id.editConPass);
        mAuth = FirebaseAuth.getInstance();
        TextView tv = findViewById(R.id.txtLogin);
        Button btnSign = findViewById(R.id.signBtn);

        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        btnSign.setOnClickListener(new View.OnClickListener() {
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
                if(password.length() <8 ){
                    mPassword.setError("Password Must be >= 8 characters");
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }else{
                            Toast.makeText(SignUp.this, "Error !" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


        tv.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
        ImageButton ibLog = findViewById(R.id.ibSign);
        ibLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Close();
            }
        });
           }



    private void Close() {
        finish();
    }

    private void sLogin() {
        Intent intSign = new Intent(this, Login.class);
        startActivity(intSign);
    }

    private void startLogin() {
        Intent intentSign = new Intent(this, Login.class);
        startActivity(intentSign);
    }
}