package com.example.vortexcar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Bookings extends AppCompatActivity {

    private TextView carInfoEDT;
    private TextView carModelEDT;
    private TextView dropoffEDT;
    private TextView rateEDT;
    private TextView daysNumberEDT;
    private TextView totalEDT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bookings);

        carInfoEDT = findViewById(R.id.vehicleName);
        dropoffEDT = findViewById(R.id.dropoff);
        rateEDT = findViewById(R.id.rate);
        daysNumberEDT = findViewById(R.id.totalDays);
        totalEDT = findViewById(R.id.totalCost);

        // Retrieve data from the intent
        Intent intent = getIntent();
        String carInfo = intent.getStringExtra("carInfo");
        String carModel = intent.getStringExtra("carModel");
        String dropoff = intent.getStringExtra("dropoff");
        double rate = intent.getDoubleExtra("rate", 0);
        double daysNumber = intent.getDoubleExtra("daysNumber", 0);
        double total = intent.getDoubleExtra("total", 0);

        // Populate the EditText fields with the retrieved data
        carInfoEDT.setText(carInfo);
        dropoffEDT.setText(dropoff);
        rateEDT.setText(String.valueOf(rate) +"$/day");
        daysNumberEDT.setText(String.valueOf(daysNumber));
        totalEDT.setText(String.valueOf(total)+"$");
    }
}
//TODO: back arrow into main activity
// +get customer & the car that he booked info
// +return car button
