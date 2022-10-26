package com.example.dormflixv2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import android.app.Activity;

public class rProfile extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText nameEditText, emailEditText, numberEditText, password;
    TextView header, prof;
    ImageButton bckBtn;
    Button saveButton, cancel, feedback;
    ImageView imageView;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference reference;
    private DatabaseReference userRef;
    private StorageReference storageReference;
    public String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rprofile);

        reference = FirebaseDatabase.getInstance().getReference("Users");
        nameEditText = findViewById(R.id.username);
        numberEditText = findViewById(R.id.usernumber);
        saveButton = findViewById(R.id.save);
        cancel = findViewById(R.id.btnCancel);
        imageView = findViewById(R.id.imageView);
        bckBtn = findViewById(R.id.editBck);
        prof = findViewById(R.id.profilename);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadToUserDatabase();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                udpateProfilePicture();
            }
        });


    }



    private void udpateProfilePicture() {

        Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openGallery, 1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                imageView.setImageURI(imageUri);

                upload(imageUri);
            }

        }

    }


    //uploading profile picture of user to storage
    private void upload(Uri imageUri) {

        StorageReference fileRef = storageReference.child("users/profilepic/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "profilepic.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(imageUri).into(imageView);
                        url = uri.toString();
                        Toast.makeText(rProfile.this, "Upload success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(rProfile.this, "Upload failed", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(rProfile.this, "Upload failed", Toast.LENGTH_SHORT).show();

            }
        });

    }
//date update
    private void uploadToUserDatabase() {
        String name = nameEditText.getText().toString();
        String number = numberEditText.getText().toString();
        String email  = user.getEmail();

        if (name.isEmpty() && number.isEmpty()) {
            nameEditText.setError("Full name is required");
            nameEditText.requestFocus();
            numberEditText.setError("Phone number is required");
            numberEditText.requestFocus();
            return;
        }
        else if (name.isEmpty()) {
            nameEditText.setError("Full name is required");
            nameEditText.requestFocus();
            return;
        }
        else if (number.isEmpty()) {
            numberEditText.setError("Phone number is required");
            numberEditText.requestFocus();
            return;
        }
        else if (url == null){
            Toast.makeText(rProfile.this, "Need to upload Picture", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseDatabase.getInstance().getReference("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(new Users(name.trim(), email, number.trim(), url)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(rProfile.this, "User database updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(rProfile.this, "Failure in updating the database", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}