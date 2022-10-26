package com.example.dormflixv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class sFeedback extends AppCompatActivity {
    Button submit;
    TextView comment;
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
                DatabaseReference myRef = database.getReference("feedbacks");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Object value =  snapshot.getValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(sFeedback.this, "Failed to send", Toast.LENGTH_LONG).show();

                    }
                });
                myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("comment").setValue(comment.getText().toString());
                Toast.makeText(sFeedback.this,"Thanks for your feedback!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplication(), eFeedback.class));
            }
        });

    }
}