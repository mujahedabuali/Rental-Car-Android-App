package com.example.vortexcar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class theRentCarInfo extends AppCompatActivity {

    private TextView carInfoEDT;
    private TextView phoneNumberEDT , emailEDT ,nameEDT;
    private TextView dropoffEDT , pickupEDT;
    private TextView bookingID;
    private TextView daysNumberEDT;
    private TextView totalEDT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_the_rent_car_info);

        carInfoEDT = findViewById(R.id.vehicleName);
        dropoffEDT = findViewById(R.id.dropoff);
        pickupEDT = findViewById(R.id.pickup);
        daysNumberEDT = findViewById(R.id.totalDays);
        totalEDT = findViewById(R.id.totalCost);
        phoneNumberEDT = findViewById(R.id.phoneNumber);
        nameEDT = findViewById(R.id.nameedt);
        emailEDT = findViewById(R.id.emailedt);
        bookingID = findViewById(R.id.bookingID);

        // Retrieve data from the intent
        Intent intent = getIntent();
        String carInfo = intent.getStringExtra("carName");
        String carModel = intent.getStringExtra("carModel");
        String carId = intent.getStringExtra("id");

        carInfoEDT.setText(carInfo+" , "+carModel);
        loadItem();

    }

    private void loadItem() {
        String url= vars.BASE_URL+"/rental-car/rentInfo.php?carID=2";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);

                                int TotalPrice = object.getInt("TotalPrice");
                                String startDate = object.getString("startDate");
                                String endDate = object.getString("endDate");
                                String phone = object.getString("Phone");
                                String name = object.getString("name");
                                String email = object.getString("email");
                                String totalDays = object.getString("totalDays");

                            pickupEDT.setText(startDate);
                                dropoffEDT.setText(endDate);
                                daysNumberEDT.setText(totalDays+" Days");
                                totalEDT.setText(TotalPrice+" $");
                                nameEDT.setText(name);
                                phoneNumberEDT.setText(phone);
                                emailEDT.setText(email);
                                bookingID.setText("Rent ID : "+object.getString("rentID"));


                        }catch (Exception e){
                            Log.d("TAG", "onResponse: "+e.toString());
                            Toast.makeText(theRentCarInfo.this, e.toString(),Toast.LENGTH_LONG).show();
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onResponse: "+error.toString());

                Toast.makeText(theRentCarInfo.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(theRentCarInfo.this).add(stringRequest);

    }
}