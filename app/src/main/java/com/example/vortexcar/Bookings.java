package com.example.vortexcar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
    private TextView pickup;

    private TextView nametv;
    private TextView emailtv;
    private TextView phoneNumbertv;
    private Button confirm;
    private String name;

    private String carID;
    private String accID;

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
        pickup = findViewById(R.id.pickup);

        nametv = findViewById(R.id.name);
        emailtv = findViewById(R.id.email);
        phoneNumbertv = findViewById(R.id.phoneNumber);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        name = sharedPreferences.getString("first_name", "Guest");
        name = name+" "+ sharedPreferences.getString("last_name","");
        String email = sharedPreferences.getString("email", "noemail");
        String phone = sharedPreferences.getString("phone", "nophone");
        accID = sharedPreferences.getString("accID","1");

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
        carID = intent.getStringExtra("id");
        boolean isMonthly = intent.getBooleanExtra("isMonthly",false);



        // Populate the EditText fields with the retrieved data
        carInfoEDT.setText(carInfo);
        dropoffEDT.setText(dropoff);
        rateEDT.setText(String.valueOf(rate) + (isMonthly ? " $/Month": "$/Day"));
        daysNumberEDT.setText(String.valueOf(daysNumber));
        totalEDT.setText(String.valueOf(total) + " $");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = today.format(formatter);
            pickup.setText(formattedDate);
        }

        confirm.setOnClickListener(e -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDate = today.format(formatter);
                if (!email.equals("noemail") && !phone.equals("nophone"))
                    rent(sharedPreferences, total, formattedDate, dropoff);
                else
                    Toast.makeText(Bookings.this, "Please log in to complete the booking.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void rent(SharedPreferences data, double totalPrice, String startDate, String endDate) {
        if (!this.name.equals("Guest")) {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = vars.BASE_URL + "/rental-car/makeRent.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("ServerResponse", response);
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                String message = jsonResponse.getString("message");

                                if (success) {
                                    Toast.makeText(Bookings.this, "Booking successful: " + message, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Bookings.this, homePage.class);
                                    startActivity(intent);
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
                    Toast.makeText(Bookings.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("carID", carID);
                    params.put("accountID", accID);
                    params.put("totalPrice", String.valueOf(totalPrice));
                    params.put("status", "now");
                    params.put("startDate", Helper.convertDateFormat(startDate));
                    params.put("endDate", Helper.convertDateFormat(endDate));
                    return params;
                }
            };

            queue.add(stringRequest);
        } else {
            Toast.makeText(Bookings.this, "Please log in to complete the booking.", Toast.LENGTH_LONG).show();
        }
    }
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}

