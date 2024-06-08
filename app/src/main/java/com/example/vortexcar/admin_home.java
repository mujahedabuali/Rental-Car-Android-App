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

public class admin_home extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Car> carList = new ArrayList<>();;
    private carAdapterForAdmin carAdapterForAdmin;
    private static  final String BASE_URL ="http://10.0.2.2/rental-car/getAllCars.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home);

        recyclerView = findViewById(R.id.view1);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        carAdapterForAdmin = new carAdapterForAdmin(carList, this);
        recyclerView.setAdapter(carAdapterForAdmin);
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
                                String imageF = "http://10.0.2.2/"+image;

                                Car car = new Car(id,company, model_year, mileage, seats_number,  monthlyPrice, dailyPrice,  price,  color,  status,  imageF);
                                carList.add(car);
                            }
                        }catch (Exception e){
                            Toast.makeText(admin_home.this, e.toString(),Toast.LENGTH_LONG).show();
                        }


                        carAdapterForAdmin adapter = new carAdapterForAdmin( carList,admin_home.this);
                        recyclerView.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(admin_home.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(admin_home.this).add(stringRequest);

    }

    public void addnew(View view) {
        Intent intent = new Intent(this, AddCar.class);
        startActivity(intent);
    }  public void report(View view) {
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
    }

}
