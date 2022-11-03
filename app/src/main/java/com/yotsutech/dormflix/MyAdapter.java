package com.yotsutech.dormflix;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class MyAdapter extends RecyclerView.ViewHolder {
    ImageView img;
    TextView dormname, roomno, price, desc, availbed;
    View v;
    public MyAdapter(@NonNull View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.imgHold);

        desc = (TextView) itemView.findViewById(R.id.Desc);
        dormname = (TextView) itemView.findViewById(R.id.dName);
        roomno = (TextView) itemView.findViewById(R.id.dPlace);
        price = (TextView) itemView.findViewById(R.id.dPrice);
        availbed = (TextView) itemView.findViewById(R.id.availBed);
        v = itemView;

    }
}
