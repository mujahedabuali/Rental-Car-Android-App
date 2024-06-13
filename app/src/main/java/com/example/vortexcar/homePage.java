package com.example.vortexcar;

import android.content.Intent;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class homePage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Car> carList = new ArrayList<>();
    private carAdapter carAdapter;
    private RequestQueue queue;

    BottomNavigationView bottomNavigationView;
    private static final String BASE_URL = "http://10.0.2.2/rental-car/getAllCars.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        FirebaseApp.initializeApp(this);

        recyclerView = findViewById(R.id.view1);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        carAdapter = new carAdapter(carList, this);
        recyclerView.setAdapter(carAdapter);

        queue = Volley.newRequestQueue(this);

        if (savedInstanceState == null) {
            loadItems();
        } else {
            carList = (List<Car>) savedInstanceState.getSerializable("carList");
            carAdapter.notifyDataSetChanged();
        }
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        int itemId = item.getItemId();
        if (itemId == R.id.navigation_home) {
            startActivity(new Intent(homePage.this, homePage.class));
            return true;
        } else if (itemId == R.id.navigation_cart) {
            startActivity(new Intent(homePage.this, MyBookings.class));
            return true;
        } else if (itemId == R.id.navigation_profile) {
            startActivity(new Intent(homePage.this, Profile.class));
            return true;
        }
        return false;
    };

    private void loadItems() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray array = new JSONArray(response);
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject object = array.getJSONObject(i);

                                        int id = object.getInt("ID");
                                        String company = object.getString("company");
                                        int mileage = object.getInt("Mileage");
                                        int seats_number = object.getInt("SeatsNumber");
                                        int monthlyPrice = object.getInt("MonthlyPrice");
                                        int dailyPrice = object.getInt("DailyPrice");
                                        int price = object.getInt("price");
                                        String model_year = object.getString("Model_year");
                                        String color = object.getString("color");
                                        String status = object.getString("status");
                                        String image = object.getString("image");
                                        String imageF = "http://10.0.2.2/" + image;

                                        Car car = new Car(id, company, model_year, mileage, seats_number, monthlyPrice, dailyPrice, price, color, status, imageF);
                                        carList.add(car);
                                    }

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            carAdapter.notifyDataSetChanged();
                                        }
                                    });

                                } catch (Exception e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(homePage.this, e.toString(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(homePage.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                queue.add(stringRequest);
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        // Remove the task associated with this activity from the back stack
        super.onBackPressed();
        moveTaskToBack(true);
    }
}
