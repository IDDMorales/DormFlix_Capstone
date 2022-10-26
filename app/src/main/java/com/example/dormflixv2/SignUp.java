package com.example.dormflixv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp extends AppCompatActivity {
    public static final String TAG = "TAG";
    private FirebaseAuth mAuth;
    EditText mFullName,mEmail,mPassword,mPhone, mConPass, vPass;
    String userID;
    public FirebaseDatabase database;
    long maxid = 0;
    private String url ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //firebase
        mFullName = findViewById(R.id.editFName);
        mPhone = findViewById(R.id.editPNum);
        mEmail = findViewById(R.id.editEMail);
        mPassword = findViewById(R.id.editPass);
        url = String.valueOf(maxid);
        mConPass = findViewById(R.id.editConPass);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        TextView tv = findViewById(R.id.txtLogin);
        Button btnSign = findViewById(R.id.signBtn);
        vPass = findViewById(R.id.editPass);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String fullname = mFullName.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();
                String confirmPass = mConPass.getText().toString().trim();

                if(email.isEmpty()) {
                    mEmail.setError("Field must not be empty");
                    return;
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mEmail.setError("Enter valid email");
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required");
                    return;
                }
                if(password.length() <8 || password.length() >12 ){
                    mPassword.setError("Password must be 8-12 characters");
                    return;
                }
                if(!confirmPass.equals(password)){
                    mConPass.setError("Password does not match");
                    return;
                }
                //registration process via Email and Password
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mAuth.getCurrentUser().sendEmailVerification();

                            FirebaseDatabase.getInstance().
                                    getReference("users/" + FirebaseAuth
                                            .getInstance()
                                            .getCurrentUser()
                                            .getUid())
                                    .setValue(new Users(mFullName.getText().toString(), mEmail.getText().toString(),mPhone.getText().toString(), url)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(SignUp.this, "Account creation successful!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(SignUp.this,  Login.class);
                                            startActivity(intent);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SignUp.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                    }

                }
            });
            }
        });


        tv.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View v) {
                sLogin();
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
        startActivity(intentSign);    }

}