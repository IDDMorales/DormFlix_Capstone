package com.yotsutech.dormflix;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import android.app.Activity;

public class rProfile extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText nameEditText, emailEditText, numberEditText, password;
    TextView header, prof;
    ImageButton bckBtn, upload;
    String holder;
    Button saveButton, cancel, feedback;
    ImageView imageView;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference reference, myRef;
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
        upload = findViewById(R.id.uploadPFP);
        bckBtn = findViewById(R.id.editBck);
        prof = findViewById(R.id.profilename);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        reference = FirebaseDatabase.getInstance().getReference("users/");
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();

                        String pFp = String.valueOf(dataSnapshot.child("url").getValue());
                        Picasso.get()
                                .load(pFp)
                                .placeholder(R.drawable.ic_baseline_person_24)
                                .error(R.drawable.ic_baseline_person_24)
                                .into(imageView);
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

        upload.setOnClickListener(new View.OnClickListener() {
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
                        Picasso.get()
                                .load(imageUri)
                                .placeholder(R.drawable.ic_baseline_person_24)
                                .error(R.drawable.ic_baseline_person_24)
                                .into(imageView);
                        url = uri.toString();
                        Toast.makeText(rProfile.this, "Upload success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(rProfile.this, "Upload failed", Toast.LENGTH_SHORT).show();
                        Picasso.get()
                                .load(R.drawable.ic_baseline_person_24)
                                .error(R.drawable.ic_baseline_person_24)
                                .into(imageView);
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
            myRef = FirebaseDatabase.getInstance().getReference("users/");
            myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            String Numb = String.valueOf(dataSnapshot.child("number").getValue());
                            String Fname = String.valueOf(dataSnapshot.child("name").getValue());
                            numberEditText.setText(Numb);
                            nameEditText.setText(Fname);
                        } else {
                            Toast.makeText(getApplication(), "Failed to get Value", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplication(), "Failed to get Value", Toast.LENGTH_SHORT).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplication(), "Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        else if (name.isEmpty()) {
            myRef = FirebaseDatabase.getInstance().getReference("users/");
            myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            String Fname = String.valueOf(dataSnapshot.child("name").getValue());
                            nameEditText.setText(Fname);
                        } else {
                            Toast.makeText(getApplication(), "Failed to get Value", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplication(), "Failed to get Value", Toast.LENGTH_SHORT).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplication(), "Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }



        else if (number.isEmpty()) {
            myRef = FirebaseDatabase.getInstance().getReference("users/");
            myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            String Numb = String.valueOf(dataSnapshot.child("number").getValue());
                            numberEditText.setText(Numb);
                        } else {
                            Toast.makeText(getApplication(), "Failed to get Value", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplication(), "Failed to get Value", Toast.LENGTH_SHORT).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplication(), "Failed", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }


        FirebaseDatabase.getInstance().getReference("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(new Users(name.trim(), email, number.trim(), url)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(rProfile.this, "User Details Update", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(rProfile.this, "Failed to Update User Details", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}