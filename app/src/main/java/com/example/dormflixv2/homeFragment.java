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

import java.util.ArrayList;


public class homeFragment extends Fragment implements recyclerViewInterface{

    ArrayList<dorms> Dorms = new ArrayList<>();

    int[] dormPic = {R.drawable.dorm1, R.drawable.dorm2, R.drawable.dorm3, R.drawable.dorm4};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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