package com.yotsutech.dormflix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sFeedback extends AppCompatActivity {
    Button submit;
    TextView comment;
    DatabaseReference myRef, reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sfeedback);
        submit = findViewById(R.id.btnSubmit);
        comment = findViewById(R.id.feedbox);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(comment.getText().toString())){
                    Toast.makeText(sFeedback.this,"Field must not be empty.", Toast.LENGTH_LONG).show();
                }
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                myRef = database.getReference("feedbacks");
                reference = FirebaseDatabase.getInstance().getReference("users/");
                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                DataSnapshot dataSnapshot = task.getResult();
                                String Fname = String.valueOf(dataSnapshot.child("name").getValue());
                                myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("comment").setValue(comment.getText().toString());
                                myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").setValue(Fname);
                            } else {
                                Toast.makeText(getApplication(), "Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplication(), "Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplication(), "Failed", Toast.LENGTH_SHORT).show();

                    }
                });
                Toast.makeText(sFeedback.this,"Thanks for your feedback!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplication(), eFeedback.class));
            }
        });


        ImageButton bck = findViewById(R.id.ibSfeed);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}