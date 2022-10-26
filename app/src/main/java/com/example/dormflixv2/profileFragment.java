package com.example.dormflixv2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class profileFragment extends Fragment {




    private FirebaseAuth mAuth;
    private TextView editProfile, name, email;
    private ImageView imageView;
    StorageReference storageReference;
    DatabaseReference reference;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.fragment_profile, container, false);
        return fragView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        super.onViewCreated(view, savedInstanceState);
        editProfile = view.findViewById(R.id.editprofile);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.sEmail);
        imageView = view.findViewById(R.id.imageView);


        TextView notif = view.findViewById(R.id.notifications);
        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Notification.class));
            }
        });
        TextView feed = view.findViewById(R.id.reportfeedback);
        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), sFeedback.class));
            }
        });
        TextView pay = view.findViewById(R.id.paymentss);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), pay1.class));
            }
        });


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), rProfile.class));
            }
        });

        TextView txtlogout = view.findViewById(R.id.txtlogout);
        txtlogout.setOnClickListener(View -> {
            mAuth.signOut();
            Intent fragL= new Intent(getActivity(), Login.class);
            startActivity(fragL);
        });

        reference = FirebaseDatabase.getInstance().getReference("/users/");
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String Fname = String.valueOf( dataSnapshot.child("name").getValue());;
                        String Femail = String.valueOf(dataSnapshot.child("email").getValue());
                        String fProfile = String.valueOf(dataSnapshot.child("url").getValue());

                        name.setText(Fname);
                        email.setText(Femail);
                        Picasso.get().load(fProfile).into(imageView);
                    }
                    else{

                    }
                }else{

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Failed to get data", Toast.LENGTH_SHORT).show();
            }
        });





    }

}