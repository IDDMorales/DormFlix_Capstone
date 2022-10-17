package com.example.dormflixv2;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class profile extends AppCompatActivity {
    public static final String TAG = "TAG";
    String DISPLAY_NAME = null;
    String PROFILE_IMAGE_URL = null;
    EditText nameEditText, emailEditText, numberEditText;
    Button saveButton;
    ImageView imageView;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.useremail);
        numberEditText = findViewById(R.id.usernumber);
        saveButton = findViewById(R.id.save);
        imageView = findViewById(R.id.imageView);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        Intent data = getIntent();
        String fname = data.getStringExtra("fname");
        String email = data.getStringExtra("email");
        String phone = data.getStringExtra("phone");

        nameEditText.setText(fname);
        emailEditText.setText(email);
        numberEditText.setText(phone);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(profile.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(nameEditText.getText().toString().isEmpty()||emailEditText.getText().toString().isEmpty()||numberEditText.getText().toString().isEmpty()){
                Toast.makeText(profile.this, "One or many fields are empty.", Toast.LENGTH_SHORT).show();
                return;
            }
            final String email = emailEditText.getText().toString();
            user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    DocumentReference docRef = db.collection("users").document(user.getUid());
                    Map<String,Object> edited = new HashMap<>();
                    edited.put("email",email);
                    edited.put("fname", nameEditText.getText().toString());
                    edited.put("phone", numberEditText.getText().toString());
                    docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(profile.this, "Profile is updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }
                    });
                }
            });

            }
        });
        Log.d(TAG, "onCreate: " + fname + "" + email + "" + phone);



    }
}

