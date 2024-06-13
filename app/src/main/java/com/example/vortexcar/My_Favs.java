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

public class My_Favs extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Car> rentedCars = new ArrayList<>();
    ;
    private carAdapter_fav carAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_favs);

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
            Toast.makeText(My_Favs.this, "Email not found in SharedPreferences", Toast.LENGTH_SHORT).show();
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
                                fetchFavoriteCars(userId);
                            } else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(My_Favs.this, message, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(My_Favs.this, "JSON error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(My_Favs.this, "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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


    private void fetchFavoriteCars(int userId) {
        String BASE_URL = "http://10.0.2.2/rental-car/get_fav.php?AccountID=" + userId;
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
                                    car.setId(carObject.getInt("ID")); // Assuming the JSON key for car_id
                                    car.setCompany(carObject.getString("company"));
                                    car.setModel_year(carObject.getString("Model_year"));
                                    car.setColor(carObject.getString("color"));
                                    car.setDailyPrice(carObject.getInt("DailyPrice"));
                                    car.setMonthlyPrice(carObject.getInt("MonthlyPrice"));
                                    car.setImage("http://10.0.2.2/" + carObject.getString("image"));
                                    car.setStatus(carObject.getString("status"));
                                    rentedCars.add(car);
                                }

                                // Update the RecyclerView adapter
                                carAdapter = new carAdapter_fav(rentedCars, My_Favs.this);
                                recyclerView.setAdapter(carAdapter);

                            } else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(My_Favs.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(My_Favs.this, "JSON parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(My_Favs.this, "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(stringRequest);
    }
}