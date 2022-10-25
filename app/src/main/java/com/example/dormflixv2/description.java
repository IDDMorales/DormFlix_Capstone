package com.example.dormflixv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class description extends AppCompatActivity {
    Button button;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    StorageReference storageReference;
    TextView dname;
    TextView dRoom;
    TextView dPrice;
    TextView Desc;
    ImageView dormPic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        button = findViewById(R.id.btnBck);


        //start of dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dbooking);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dbookingbg));

        EditText ePick = dialog.findViewById(R.id.dpEpick);
        EditText room = dialog.findViewById(R.id.dpEroom);
        EditText date = dialog.findViewById(R.id.dpEpick);
        reference = FirebaseDatabase.getInstance().getReference("bookings");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();


        Button book = dialog.findViewById(R.id.btnFinalB);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference = FirebaseDatabase.getInstance().getReference("users/");
                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                DataSnapshot dataSnapshot = task.getResult();
                                String fName = String.valueOf(dataSnapshot.child("name").getValue());
                                String pNO = String.valueOf(dataSnapshot.child("number").getValue());
                                String name = fName;
                                String number = pNO;
                                String roomno = room.getText().toString();
                                String day = date.getText().toString();

                                FirebaseDatabase.getInstance().getReference("bookings/" + FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(new uBook(name.trim(), number.trim(), roomno.trim(), day.trim())).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(description.this, "User database updated", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(description.this, "Failure in updating the database", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                            else{

                            }
                        }
                        else{

                        }
                    }
                });
            }
        });


                            ePick.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker()
                                            .setTitleText("Select Date").build();
                                    materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                                        @Override
                                        public void onPositiveButtonClick(Object selection) {
                                            ePick.setText("" + materialDatePicker.getHeaderText());
                                        }
                                    });
                                    materialDatePicker.show(getSupportFragmentManager(), "TAG");
                                }
                            });


                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (button.isPressed()) {

                                        dialog.show();

                                    }
                                }
                            });
                            //end of dialog

        dormPic = findViewById(R.id.DormPic);
        dname = findViewById(R.id.dName);
        dRoom = findViewById(R.id.dPlace);
        dPrice = findViewById(R.id.dPrice);
        Desc = findViewById(R.id.Desc);
        reference = FirebaseDatabase.getInstance().getReference("rooms/");

        String dormkey = getIntent().getStringExtra("dormKey");

        reference.child(dormkey).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String name = String.valueOf(dataSnapshot.child("dormname").getValue());
                        String dP = String.valueOf(dataSnapshot.child("purl").getValue());
                        String roomnum = String.valueOf(dataSnapshot.child("roomno").getValue());
                        String price = String.valueOf(dataSnapshot.child("price").getValue());
                        String desc = String.valueOf(dataSnapshot.child("desc").getValue());


                        Desc.setText(desc);
                        dRoom.setText(roomnum);
                        dname.setText(name);
                        dPrice.setText(price);
                        Picasso.get().load(dP).into(dormPic);


                    }
                }
            }
        });

    }
}

















