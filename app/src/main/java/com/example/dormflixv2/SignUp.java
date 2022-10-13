package com.example.dormflixv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignUp extends AppCompatActivity {
    public static final String TAG = "TAG";
    private FirebaseAuth mAuth;
    EditText mFullName,mEmail,mPassword,mPhone, mConPass;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //firebase
        mFullName = findViewById(R.id.editFName);
        mPhone = findViewById(R.id.editPNum);
        mEmail = findViewById(R.id.editEMail);
        mPassword = findViewById(R.id.editPass);
        mConPass = findViewById(R.id.editConPass);
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        TextView tv = findViewById(R.id.txtLogin);
        Button btnSign = findViewById(R.id.signBtn);




        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String fullname = mFullName.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();
                String Confirmpass = mConPass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required");
                    return;
                }
                if(password.length() <8 || password.length() >12 ){
                    mPassword.setError("Password Must be 8-12 characters");
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            Toast.makeText(SignUp.this, "User Created", Toast.LENGTH_SHORT).show();
                            userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = db.collection("Users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fname",fullname);
                            user.put("email", email);
                            user.put("phone", phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"OnSuccess: user Profile is created for "+ userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), Login.class));


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