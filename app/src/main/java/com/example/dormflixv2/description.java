package com.example.dormflixv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class description extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        String name = getIntent().getStringExtra("Name");
        String place = getIntent().getStringExtra("Place");
        String price = getIntent().getStringExtra("Price");
        String description = getIntent().getStringExtra("Description");
        int image = getIntent().getIntExtra("Image", 0);

        TextView doName = findViewById(R.id.dName);
        TextView doPlace = findViewById(R.id.dPlace);
        TextView doPrice = findViewById(R.id.dPrice);
        TextView desCri = findViewById(R.id.Desc);
        ImageView imgView = findViewById(R.id.DormPic);

        doName.setText(name);
        doPlace.setText(place);
        doPrice.setText(price);
        desCri.setText(description);
        imgView.setImageResource(image);

    }
}