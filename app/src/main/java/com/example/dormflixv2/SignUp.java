package com.example.dormflixv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

      EditText fullname = findViewById(R.id.editFName);
      EditText email = findViewById(R.id.editEMail);
      EditText phone = findViewById(R.id.editPNum);
      EditText password = findViewById(R.id.editPass);
      EditText conPassword = findViewById(R.id.editConPass) ;
      mAuth = FirebaseAuth.getInstance();



        Button btnSign = findViewById(R.id.signBtn);
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });


        TextView tv = findViewById(R.id.txtLogin);
        tv.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View v) {sLogin();}
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