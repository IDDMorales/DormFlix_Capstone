package com.example.dormflixv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLog = findViewById(R.id.btnLog);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainHome();
            }
        });

        TextView tv1 = findViewById(R.id.signTxt);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sMainHome();
            }
        });
        ImageButton ibLog = findViewById(R.id.ibLog);
        ibLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeLog();
            }
        });


        /*need one more for forgot password*/

    }

    private void closeLog() {
        finish();
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