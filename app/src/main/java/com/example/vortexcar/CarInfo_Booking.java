package com.example.vortexcar;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class CarInfo_Booking extends AppCompatActivity {

    private EditText carnameEDT;
    private EditText startDateText;
    private EditText endDateText;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info_booking);

        carnameEDT = findViewById(R.id.nameText1);
        startDateText = findViewById(R.id.startDateText);
        endDateText = findViewById(R.id.endDateText);
        imageView = findViewById(R.id.carimage);
        Intent intent = getIntent();
        render(intent);

    }



    private void render(Intent intent) {
        String carName = intent.getStringExtra("carName");
        String start = intent.getStringExtra("carStrartDate");
        String end = intent.getStringExtra("carEndDate");
        String url = intent.getStringExtra("imageUrl");

        carnameEDT.setText(carName);
        startDateText.setText(start);
        endDateText.setText(end);

        if (url != null) {
            Glide.with(this)
                    .load(url)
                    .into(imageView);
        }
    }

}
