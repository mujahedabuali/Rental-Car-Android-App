package com.example.vortexcar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBookings extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<RentedCar> rentedCars = new ArrayList<>();
    ;
    private carAdapter_Booking carAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_bookings);
        FirebaseApp.initializeApp(this);

        recyclerView = findViewById(R.id.view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadItems();
    }

    private void loadItems() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", null);

        if (email != null) {
            getUserIdByEmail(email);
        } else {
            Toast.makeText(MyBookings.this, "Email not found in SharedPreferences", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUserIdByEmail(String email) {
        String URL_GET_USER_ID = "http://10.0.2.2/rental-car/getUserID.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_GET_USER_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");

                            if (status.equals("success")) {
                                int userId = jsonObject.getInt("user_id");
                                fetchCarsByUserId(userId);
                            } else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(MyBookings.this, message, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MyBookings.this, "JSON error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(MyBookings.this, "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    private void fetchCarsByUserId(int userId) {
        String BASE_URL = "http://10.0.2.2/rental-car/getCarsByUser.php?AccountID=" + userId;
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");

                            if (status.equals("success")) {
                                JSONArray data = jsonObject.getJSONArray("data");
                                rentedCars.clear();

                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject carObject = data.getJSONObject(i);
                                    Car car = new Car();
                                    car.setId(carObject.getInt("ID"));
                                    car.setCompany(carObject.getString("company"));
                                    car.setModel_year(carObject.getString("Model_year"));
                                    car.setColor(carObject.getString("color"));
                                    car.setDailyPrice(carObject.getInt("DailyPrice"));
                                    car.setMonthlyPrice(carObject.getInt("MonthlyPrice"));
                                    car.setImage("http://10.0.2.2/"+carObject.getString("image"));
                                    car.setPrice(carObject.getInt("TotalPrice"));
                                    car.setStatus(carObject.getString("status"));

                                    String startDate = carObject.getString("startDate");
                                    String endDate = carObject.getString("endDate");

                                    RentedCar rentedCar = new RentedCar(car, startDate, endDate);
                                    rentedCars.add(rentedCar);
                                }


                            } else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(MyBookings.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MyBookings.this, "JSON parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        carAdapter = new carAdapter_Booking(rentedCars, MyBookings.this);
                        recyclerView.setAdapter(carAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(MyBookings.this, "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(stringRequest);
    }
}
