package com.example.dormflixv2;

import static android.content.Intent.getIntent;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class bookmarkFragment extends Fragment {
    DatabaseReference reference, ref;
    ShapeableImageView img;
    TextView dormName, dormroom;
    CardView holders;
    String key;
    FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.bgdialog));

        MaterialButton yes = dialog.findViewById(R.id.btnYes);
        MaterialButton no = dialog.findViewById(R.id.btnNo);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert deletion of booking firebase part
                FirebaseDatabase.getInstance().getReference("bookings/" + FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(null);
                dialog.dismiss();
                holders.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Cancellation Succeed ", Toast.LENGTH_SHORT).show();

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"You did not cancel Booking", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


        Button btnCancel = (Button) view.findViewById(R.id.bookCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        dormName = view.findViewById(R.id.dormName);
        dormroom = view.findViewById(R.id.dormroom);
        img = view.findViewById(R.id.picD);
        holders = view.findViewById(R.id.holder);
        reference = FirebaseDatabase.getInstance().getReference("bookings/");
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String dormname = String.valueOf( dataSnapshot.child("dornname").getValue());;
                        String roomnum = String.valueOf(dataSnapshot.child("roomno").getValue());
                        String imgL = String.valueOf(dataSnapshot.child("purl").getValue());
                        dormName.setText(dormname);
                        dormroom.setText("Room " + roomnum);
                        Picasso.get().load(imgL).into(img);
                        holders.setVisibility(View.VISIBLE);
                    }
                    else{
                        Button btnCancel = (Button) view.findViewById(R.id.bookCancel);
                        btnCancel.setEnabled(false);
                        Toast.makeText(getActivity(), "No bookings yet", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
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