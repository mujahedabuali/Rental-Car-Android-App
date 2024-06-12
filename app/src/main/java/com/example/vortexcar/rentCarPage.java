package com.example.vortexcar;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class rentCarPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Car> carList = new ArrayList<>();;
    private renrCarAdapter renrCarAdapter;
    private static  final String BASE_URL =vars.BASE_URL+"/rental-car/getAllRentCar.php";
    BottomNavigationView bottomNavigationView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rent_car_page);

        recyclerView = findViewById(R.id.view1);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        renrCarAdapter = new renrCarAdapter(carList, this);
        recyclerView.setAdapter(renrCarAdapter);
        loadItems();
    }

    private void loadItems() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){
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
                                String imageF = vars.BASE_URL+"/"+image;

                                Car car = new Car(id,company, model_year, mileage, seats_number,  monthlyPrice, dailyPrice,  price,  color,  status,  imageF);
                                carList.add(car);
                            }
                        }catch (Exception e){
                            Toast.makeText(rentCarPage.this, e.toString(),Toast.LENGTH_LONG).show();
                        }


                        renrCarAdapter adapter = new renrCarAdapter( carList,rentCarPage.this);
                        recyclerView.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(rentCarPage.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(rentCarPage.this).add(stringRequest);

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        int itemId = item.getItemId();
        if (itemId == R.id.navigation_home) {
            startActivity(new Intent(rentCarPage.this, admin_home.class));
            return true;
        } else if (itemId == R.id.navigation_rentCar) {
//            startActivity(new Intent(rentCarPage.this, rentCarPage.class));
            return true;
        } else if (itemId == R.id.navigation_add) {
            startActivity(new Intent(rentCarPage.this, AddCar.class));
            return true;
        }else if (itemId == R.id.navigation_report) {
            startActivity(new Intent(rentCarPage.this, dashboard.class));
            return true;
        }
        return false;
    };

}
