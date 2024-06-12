package com.example.vortexcar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.WindowCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class edit_car extends AppCompatActivity {
    private static final String TAG = "edit_carActivity";
    private RequestQueue queue;
    private EditText companyEditText, seatsEditText, dailyPriceEditText, monthlyPriceEditText, mileageEditText, colorEditText, modelEditText, imageURLEditText;
    private AppCompatButton saveButton;
    public int id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_edit_car);

        companyEditText = findViewById(R.id.company);
        seatsEditText = findViewById(R.id.seats);
        dailyPriceEditText = findViewById(R.id.Dailyprice);
        monthlyPriceEditText = findViewById(R.id.Monthlyprice);
        mileageEditText = findViewById(R.id.mileage);
        colorEditText = findViewById(R.id.color);
        modelEditText = findViewById(R.id.model);
        imageURLEditText = findViewById(R.id.imageURL);

        saveButton = findViewById(R.id.add);

        queue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 1);

        String url = vars.BASE_URL + "/rental-car/car/car.php?id=" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject e) {
                        try {
                            JSONObject obj = e.getJSONObject("data");
                            companyEditText.setText(obj.getString("company"));
                            seatsEditText.setText(obj.getString("SeatsNumber"));
                            dailyPriceEditText.setText(obj.getString("DailyPrice"));
                            monthlyPriceEditText.setText(obj.getString("MonthlyPrice"));
                            mileageEditText.setText(obj.getString("Mileage"));
                            colorEditText.setText(obj.getString("color"));
                            modelEditText.setText(obj.getString("Model_year"));
                            imageURLEditText.setText(obj.getString("image"));
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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCar();
            }
        });

        if (savedInstanceState != null) {
            companyEditText.setText(savedInstanceState.getString("company"));
            seatsEditText.setText(savedInstanceState.getString("seats"));
            dailyPriceEditText.setText(savedInstanceState.getString("dailyPrice"));
            monthlyPriceEditText.setText(savedInstanceState.getString("monthlyPrice"));
            mileageEditText.setText(savedInstanceState.getString("mileage"));
            colorEditText.setText(savedInstanceState.getString("color"));
            modelEditText.setText(savedInstanceState.getString("model"));
            imageURLEditText.setText(savedInstanceState.getString("imageURL"));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("company", companyEditText.getText().toString());
        outState.putString("seats", seatsEditText.getText().toString());
        outState.putString("dailyPrice", dailyPriceEditText.getText().toString());
        outState.putString("monthlyPrice", monthlyPriceEditText.getText().toString());
        outState.putString("mileage", mileageEditText.getText().toString());
        outState.putString("color", colorEditText.getText().toString());
        outState.putString("model", modelEditText.getText().toString());
        outState.putString("imageURL", imageURLEditText.getText().toString());
        Log.d(TAG, "onSaveInstanceState called");
    }

    private void saveCar() {
        String company = companyEditText.getText().toString().trim();
        String seats = seatsEditText.getText().toString().trim();
        String dailyPrice = dailyPriceEditText.getText().toString().trim();
        String monthlyPrice = monthlyPriceEditText.getText().toString().trim();
        String mileage = mileageEditText.getText().toString().trim();
        String color = colorEditText.getText().toString().trim();
        String model = modelEditText.getText().toString().trim();
        String imageURL = imageURLEditText.getText().toString().trim();

        if (company.isEmpty() || seats.isEmpty() || dailyPrice.isEmpty() || monthlyPrice.isEmpty() || mileage.isEmpty() || color.isEmpty() || model.isEmpty() || imageURL.isEmpty()) {
            Toast.makeText(edit_car.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = vars.BASE_URL + "/rental-car/car/edit.php";
        RequestQueue queue = Volley.newRequestQueue(edit_car.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    String message = res.getString("message");
                    Toast.makeText(edit_car.this, message, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(edit_car.this, "Error: " + volleyError.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("company", company);
                params.put("SeatsNumber", seats);
                params.put("Model_year", model);
                params.put("MonthlyPrice", monthlyPrice);
                params.put("Mileage", mileage);
                params.put("DailyPrice", dailyPrice);
                params.put("color", color);
                params.put("id", String.valueOf(id));
                params.put("image", imageURL);
                return params;
            }
        };

        queue.add(request);
    }
}
