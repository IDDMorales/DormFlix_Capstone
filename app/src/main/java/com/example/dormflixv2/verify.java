package com.example.dormflixv2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

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