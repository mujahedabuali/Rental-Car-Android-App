package com.example.vortexcar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CarInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_car_info);

//        ImageView imageViewCar = findViewById(R.id.imageViewCarDetail);
//        TextView textViewCarName = findViewById(R.id.textViewCarNameDetail);
//        TextView textViewCarModel = findViewById(R.id.textViewCarModelDetail);
//        TextView textViewCarColor = findViewById(R.id.textViewCarColorDetail);
//        TextView textViewCarDailyPrice = findViewById(R.id.textViewCarDailyPriceDetail);
//        TextView textViewCarMonthlyPrice = findViewById(R.id.textViewCarMonthlyPriceDetail);

        Intent intent = getIntent();
        if (intent != null) {
            String imageUrl = intent.getStringExtra("imageUrl");
            String carName = intent.getStringExtra("carName");
            String carModel = intent.getStringExtra("carModel");
            String carColor = intent.getStringExtra("carColor");
            int dailyPrice = intent.getIntExtra("dailyPrice", 0);
            int monthlyPrice = intent.getIntExtra("monthlyPrice", 0);

//            Glide.with(this).load(imageUrl).into(imageViewCar);
//            textViewCarName.setText(carName);
//            textViewCarModel.setText(carModel);
//            textViewCarColor.setText(carColor);
//            textViewCarDailyPrice.setText(String.valueOf(dailyPrice));
//            textViewCarMonthlyPrice.setText(String.valueOf(monthlyPrice));
        }

    }
}
//TODO:
// 1- the back button return into the main activity
// 2- when the user click on the heart the car goes into favorites
// 3- user can pick 3 types of car insurance :none,basic,premium
// 4-when the user click on book car button it goes into the booking
// 5- if the car is available only the available textview will appear if not the not available will

