package com.example.vortexcar;

import static android.content.ContentValues.TAG;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class homePage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView t ;
    private List<Car> carList = new ArrayList<>();;
    private carAdapter carAdapter;
    private static  final String BASE_URL = "http://172.19.18.162/rental-car/getAllCars.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.view1);



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        carAdapter = new carAdapter(carList, this);
        recyclerView.setAdapter(carAdapter);
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
                                int seats_number = object.getInt("Seats number");
                                int monthlyPrice = object.getInt("MonthlyPrice");
                                int dailyPrice = object.getInt("DailyPrice");
                                int price = object.getInt("price");
                                String model_year = object.getString("Model , year");
                                String color = object.getString("color");
                                String status = object.getString("status");
                                String image = object.getString("image");
                                String imageF = "http://172.19.18.162/"+image;

                                Car car = new Car(id,company, model_year, mileage, seats_number,  monthlyPrice, dailyPrice,  price,  color,  status,  imageF);
                                carList.add(car);
                            }
                        }catch (Exception e){
                            Toast.makeText(homePage.this, e.toString(),Toast.LENGTH_LONG).show();
                        }


                        carAdapter adapter = new carAdapter( carList,homePage.this);
                        recyclerView.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(homePage.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(homePage.this).add(stringRequest);

    }

    public void myfavs(View view) {
        Intent intent = new Intent(this, MyFavourites.class);
        startActivity(intent);
    }  public void mybookings(View view) {
        Intent intent = new Intent(this, MyBookings.class);
        startActivity(intent);
    }
    public void myprofile(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}
