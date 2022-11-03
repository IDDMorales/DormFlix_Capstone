package com.yotsutech.dormflix;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class verify extends AppCompatActivity {
    EditText edit;
    Button upload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        edit = findViewById(R.id.fileupload);
        upload = findViewById(R.id.btnUp);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateEmail();
            }
        });


    }

    private void validateEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = edit.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            edit.setError("Email is required");
            return;
        }

        if(user.isEmailVerified()){
            startActivity(new Intent(verify.this, mainHome.class));

        }else{
            user.sendEmailVerification();
            Toast.makeText(verify.this, "Check your email to verify", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplication(), mainHome.class));
        }

    }
}