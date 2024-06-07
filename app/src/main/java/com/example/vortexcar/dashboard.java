package com.example.vortexcar;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;

public class dashboard extends AppCompatActivity {


    private TextView totalCars, totalCarsNotRent, totalCarsRentNow;
    private TextView totalRevenue;
    private TextView totalCustomers, newCustomers, customersRentOneCar;
    private TextView topCar;
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        totalCars = findViewById(R.id.totalCars);
        totalCarsNotRent = findViewById(R.id.totalCarsNotRent);
        totalCarsRentNow = findViewById(R.id.totalCarsRentNow);
        totalRevenue = findViewById(R.id.totalRevenue);
        totalCustomers = findViewById(R.id.totalCustomers);
        newCustomers = findViewById(R.id.newCustomers);
        customersRentOneCar = findViewById(R.id.customersRentOneCar);
        topCar = findViewById(R.id.topCar);

        setDashboardData();
    }

    private void setDashboardData() {
        queue = Volley.newRequestQueue(this);
        String url = vars.BASE_URL+"/rental-car/report.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject obj) {

                        try {
                            totalCars.setText("Number of all cars: "+obj.getString("TotalCars"));
                            totalCarsNotRent.setText("Total cars not rent: "+obj.getString("TotalCarsNotRented"));
                            totalCarsRentNow.setText("Number of rent cars now: "+obj.getString("RentedCarsNow"));
                            totalRevenue.setText("Total revenue: $"+obj.getString("TotalRevenue"));
                            totalCustomers.setText("Total Customers: "+obj.getString("TotalCustomers"));
                            newCustomers.setText("New Customers: "+obj.getString("NewCustomers"));
                            customersRentOneCar.setText("Customers rent at least one car: "+obj.getString("CustomersRentedOneCar"));
                            topCar.setText("Top Car: Toyota Camry");
                        } catch (JSONException ep) {
                            Log.e("err", "onResponse: ", ep);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG", "onErrorResponse: " + error.toString());
                    }
                }
        );

        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest);

    }
}