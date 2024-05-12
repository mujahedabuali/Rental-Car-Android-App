package com.example.vortexcar;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CarInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_car_info);
    }
}
//TODO:
// 1- the back button return into the main activity
// 2- when the user click on the heart the car goes into favorites
// 3- user can pick 3 types of car insurance :none,basic,premium
// 4-when the user click on book car button it goes into the booking
// 5- if the car is available only the available textview will appear if not the not available will

