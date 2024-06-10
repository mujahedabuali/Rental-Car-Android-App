package com.example.vortexcar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Bookings extends AppCompatActivity {

    private TextView carInfoEDT;
    private TextView carModelEDT;
    private TextView dropoffEDT;
    private TextView rateEDT;
    private TextView daysNumberEDT;
    private TextView totalEDT;

    private TextView nametv;
    private TextView emailtv;
    private TextView phoneNumbertv;
    private Button confirm;
    private String name;

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

        confirm = findViewById(R.id.confirm);

        nametv = findViewById(R.id.name);
        emailtv = findViewById(R.id.email);
        phoneNumbertv = findViewById(R.id.phoneNumber);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        name = sharedPreferences.getString("name", "Guest");
        String email = sharedPreferences.getString("email", "noemail");
        String phone = sharedPreferences.getString("phone", "**********");

        this.nametv.setText(name);
        this.emailtv.setText(email);
        this.phoneNumbertv.setText(phone);

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
        rateEDT.setText(String.valueOf(rate) + "$/day");
        daysNumberEDT.setText(String.valueOf(daysNumber));
        totalEDT.setText(String.valueOf(total) + "$");

        confirm.setOnClickListener(e -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDate = today.format(formatter);
                rent(sharedPreferences, total, formattedDate, dropoff);
            }
        });
    }

    private void rent(SharedPreferences data, double totalPrice, String startDate, String endDate) {
        if (!this.name.equals("Guest")) {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://192.168.56.1/rentalcar/makeRent.php"; // replace with your server URL

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Handle response from the server
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                String message = jsonResponse.getString("message");

                                if (success) {
                                    Toast.makeText(Bookings.this, "Booking successful: " + message, Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(Bookings.this, "Booking failed: " + message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Bookings.this, "JSON parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                    Toast.makeText(Bookings.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("carID", "1"); // Replace with actual carID
                    params.put("accountID", "1"); // Replace with actual accountID
                    params.put("totalPrice", String.valueOf(totalPrice));
                    params.put("status", "confirmed"); // or any other status
                    params.put("startDate", startDate);
                    params.put("endDate", endDate);
                    return params;
                }
            };

            queue.add(stringRequest);
        } else {
            Toast.makeText(Bookings.this, "Please log in to complete the booking.", Toast.LENGTH_LONG).show();
        }
    }
}
//TODO: back arrow into main activity
// +get customer & the car that he booked info
// +return car button
