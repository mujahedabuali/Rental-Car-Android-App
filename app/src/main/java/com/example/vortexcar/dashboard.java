package com.example.vortexcar;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class dashboard extends AppCompatActivity {


    private TextView totalCars, totalCarsNotRent, totalCarsRentNow;
    private TextView totalRevenue;
    private TextView totalCustomers, newCustomers, customersRentOneCar;
    private RequestQueue queue;
    private ListView lstTopCar ,lstTopCustomer;



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
        lstTopCar = findViewById(R.id.topcar);
        lstTopCustomer = findViewById(R.id.topCustomer);



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


                            JSONArray tops = obj.getJSONArray("TopCar");

                            ArrayList<String> allcars = new ArrayList<>();
                                    for (int i = 0; i < 20; i++) {
                                        try {
                                            JSONObject car = tops.getJSONObject(i);
                                            allcars.add(car.getString("Company") +" , "+ car.getString("model") +" , rent count : "+car.getString("RentCount"));
                                        }catch(JSONException exception){
                                            Log.d("volley_error", exception.toString());
                                        }
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(dashboard.this,
                                            android.R.layout.simple_list_item_1, allcars);
                                    lstTopCar.setAdapter(adapter);

                            JSONArray topsCustomer = obj.getJSONArray("TopCustomer");

                            ArrayList<String> allcustomer = new ArrayList<>();
                            for (int i = 0; i < 20; i++) {
                                try {
                                    JSONObject customer = topsCustomer.getJSONObject(i);
                                    allcustomer.add(customer.getString("Name") +" , "+ customer.getString("Email") +" , rent count : "+customer.getString("RentCount"));
                                }catch(JSONException exception){
                                    Log.d("volley_error", exception.toString());
                                }
                            }
                            ArrayAdapter<String> adapterCustomer = new ArrayAdapter<String>(dashboard.this,
                                    android.R.layout.simple_list_item_1, allcustomer);
                            lstTopCustomer.setAdapter(adapterCustomer);

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

        queue.add(jsonObjectRequest);

    }
}