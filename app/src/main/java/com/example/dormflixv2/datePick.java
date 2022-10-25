package com.example.dormflixv2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.Calendar;

public class datePick extends AppCompatActivity{
    EditText datePickers;
    TextView pickDate;
    Button btnShow;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_pick);

        datePickers = findViewById(R.id.dpEpick);
        pickDate = findViewById(R.id.dpPick);
        btnShow = findViewById(R.id.btnFinalB);

        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date").build();
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                datePickers.setText(""+materialDatePicker.getHeaderText());
            }
        });
        materialDatePicker.show(getSupportFragmentManager(),"TAG");


    }

}