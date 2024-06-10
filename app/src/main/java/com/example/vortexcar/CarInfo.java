package com.example.vortexcar;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class CarInfo extends AppCompatActivity {

    private EditText dueDateEditText;
    private TextView carnameEDT;
    private EditText carmodelEDT;
    private EditText dailypriceEDT;
    private EditText monthlypriceEDT;
    private EditText mileageEDT;
    private EditText yearEDT;
    private ImageView imageView;
    private Button daybtn;
    private Button monthbtn;
    private String dueDate ="";
    String id ;

    private double dailyPrice;
    private double monthlyPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);

        dueDateEditText = findViewById(R.id.dueDateEditText);
        daybtn = findViewById(R.id.daybutton);
        monthbtn = findViewById(R.id.monthbutton);

        carnameEDT = findViewById(R.id.textView);

        carmodelEDT = findViewById(R.id.modeledittext);
        mileageEDT = findViewById(R.id.mileageedittext);
        yearEDT = findViewById(R.id.yearedittext);
        dailypriceEDT = findViewById(R.id.priceperdayedittext);
        monthlypriceEDT = findViewById(R.id.pricepermonthedittext);
        imageView = findViewById(R.id.carimage);
        Intent intent = getIntent();
        render(intent);



        Button dueDateButton = findViewById(R.id.dueDateButton);

        dueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        dueDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        daybtn.setOnClickListener(e->{
            startNext(true);
        });
        monthbtn.setOnClickListener(e->{
            startNext(false);
        });
    }

    private void startNext(boolean dayrent) {
        if (!dueDate.equals("")){
            if (dayrent){
                double days = getDays(dueDate);
                Intent intent = new Intent(this, Bookings.class);
                intent.putExtra("carInfo", String.valueOf(carnameEDT.getText())+" "+carmodelEDT.getText());
                intent.putExtra("carModel",carmodelEDT.getText()+"");
                intent.putExtra("dropoff",dueDate);
                intent.putExtra("rate",dailyPrice);
                intent.putExtra("daysNumber",days);
                intent.putExtra("total",days*dailyPrice);
                intent.putExtra("id",id);
                this.startActivity(intent);
            }
            else {
                double days = getDays(dueDate);
                Intent intent = new Intent(this, Bookings.class);
                intent.putExtra("carInfo", String.valueOf(carnameEDT.getText())+" "+carmodelEDT.getText());
                intent.putExtra("carModel",carmodelEDT.getText()+"");
                intent.putExtra("dropoff",dueDate);
                intent.putExtra("rate",monthlyPrice);
                intent.putExtra("daysNumber",days);
                intent.putExtra("total",days*monthlyPrice);
                intent.putExtra("id",id);
                this.startActivity(intent);
            }
        }
        else {
            Toast.makeText(CarInfo.this, "Booking failed: select a date", Toast.LENGTH_LONG).show();
        }

    }

    private void render(Intent intent) {
        String carName = intent.getStringExtra("carName");
        String year = intent.getStringExtra("carModel");// year
        String color = intent.getStringExtra("carColor");
        String mileage = intent.getStringExtra("mileage");
        String dailyPrice = intent.getStringExtra("dailyPrice");
        String monthlyPrice = intent.getStringExtra("monthlyPrice");
        String url = intent.getStringExtra("imageUrl");
        id = intent.getStringExtra("id");

        try {
            this.dailyPrice = Double.parseDouble(dailyPrice);
            this.monthlyPrice = Double.parseDouble(monthlyPrice);
        }
        catch (Exception ex){
            this.dailyPrice = 100;
            this.monthlyPrice = 2000;
        }


        carnameEDT.setText(carName);
        yearEDT.setText(year);
        carmodelEDT.setText(color);
        mileageEDT.setText(mileage+" KM");
        Log.d("mileage is"," traa"+mileage);
        dailypriceEDT.setText(String.valueOf(dailyPrice)+" $");
        monthlypriceEDT.setText(String.valueOf(monthlyPrice)+" $");

        if (url != null) {
            Glide.with(this)
                    .load(url)
                    .into(imageView);
        }
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.DAY_OF_YEAR, 1);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                CarInfo.this,
                (view, year1, month1, dayOfMonth) -> {
                    dueDate = dayOfMonth + "/" + (month1 + 1 < 10 ? "0" + (month1 + 1) : (month1 + 1)) + "/" + year1;
                    dueDateEditText.setText(dueDate);
                },
                year, month, day
        );

        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePicker.setMinDate(calendar.getTimeInMillis());

        datePickerDialog.show();
    }
    private static double getDays(String date){
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate givenDate = LocalDate.parse(date, formatter);
            LocalDate today = LocalDate.now();
            long daysBetween = ChronoUnit.DAYS.between(today, givenDate);
            return daysBetween;
        }
        return 0;
    }
}
