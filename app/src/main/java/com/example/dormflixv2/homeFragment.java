package com.example.dormflixv2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class homeFragment extends Fragment implements recyclerViewInterface{
    private FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference reference;
    private StorageReference storageReference;
    ImageView hProf;
    TextView pName;


    ArrayList<dorms> Dorms = new ArrayList<>();

    int[] dormPic = {R.drawable.dorm1, R.drawable.dorm2, R.drawable.dorm3, R.drawable.dorm4};
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated   (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //data base part
        mAuth = FirebaseAuth.getInstance();
        hProf = view.findViewById(R.id.pfp);
        pName = view.findViewById(R.id.pName);



       reference = FirebaseDatabase.getInstance().getReference("users/");
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String Fname = String.valueOf( dataSnapshot.child("name").getValue());;
                        String fProfile = String.valueOf(dataSnapshot.child("url").getValue());

                        pName.setText(Fname);
                        Picasso.get().load(fProfile).into(hProf);
                    }
                    else{

                    }
                }else{

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();

            }
        });


        //recycler part
        RecyclerView recyclerView = view.findViewById(R.id.mRecyclerView);

        setDorms();

        adaptDorm adapter = new adaptDorm(getActivity(), Dorms, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


    }

    private void setDorms(){
        String[] dormName = getResources().getStringArray(R.array.dorm_names);
        String[] dormPlace = getResources().getStringArray(R.array.dorm_place);
        String[] dormPrice = getResources().getStringArray(R.array.dorm_price);

        for(int i = 0; i<dormName.length; i++){
            Dorms.add(new dorms(dormName[i],dormPlace[i],dormPrice[i], dormPic[i]));
        }
    }



    @Override
    public void onItemClick(int position) {

    }
}