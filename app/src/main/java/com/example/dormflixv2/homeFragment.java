package com.example.dormflixv2;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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


public class homeFragment extends Fragment{

    private FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference reference;
    private StorageReference storageReference;
    ImageView hProf;
    TextView pName;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<MainModel> options;
    FirebaseRecyclerAdapter<MainModel, MyAdapter> adapter;


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
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        String Fname = String.valueOf(dataSnapshot.child("name").getValue());
                        String fProfile = String.valueOf(dataSnapshot.child("url").getValue());
                        pName.setText(Fname);
                        Picasso.get().load(fProfile).into(hProf);
                    } else {

                    }
                } else {

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();

            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("rooms");
        recyclerView = (RecyclerView) view.findViewById(R.id.mRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        loadData();
        //recycler part
        RecyclerView recyclerView;
        FirebaseRecyclerOptions<MainModel> options;
        FirebaseRecyclerAdapter<MainModel, MyAdapter> adapter;
        
    }

    private void loadData() {
        options = new FirebaseRecyclerOptions.Builder<MainModel>().setQuery(reference, MainModel.class).build();
        adapter = new FirebaseRecyclerAdapter<MainModel, MyAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyAdapter holder, @SuppressLint("RecyclerView") int position, @NonNull MainModel model) {
                holder.dormname.setText(model.getDormname());
                holder.roomno.setText("Room " +model.getRoomno());
                holder.price.setText("₱ "+model.getPrice());

                Glide.with(holder.img.getContext())
                        .load(model.getPurl())
                        .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                        .circleCrop()
                        .error(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark_normal)
                        .into(holder.img);
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), description.class);
                        intent.putExtra("dormKey", getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent,false);
                return new MyAdapter(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }


}