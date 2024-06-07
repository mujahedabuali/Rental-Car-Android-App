package com.example.vortexcar;
import android.content.Intent;
import android.os.Bundle;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class carInfoForAdmin extends AppCompatActivity {


    private TextView carnameEDT;
    private EditText carmodelEDT;
    private EditText mileageEDT;
    private ImageView imageView;

    private int id =0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info_for_admin);


        carnameEDT = findViewById(R.id.textView);
        carmodelEDT = findViewById(R.id.modeledittext);
        mileageEDT = findViewById(R.id.mileageedittext);
        imageView = findViewById(R.id.carimage);
        Intent intent = getIntent();
        render(intent);
    }
    private void render(Intent intent) {
        String carName = intent.getStringExtra("carName");

        String model = intent.getStringExtra("carModel");
        int mileage = intent.getIntExtra("mileage",0);
        String url = intent.getStringExtra("imageUrl");
        id = intent.getIntExtra("id",1);



//        this.dailyPrice = Double.parseDouble(dailyPrice);
//        this.monthlyPrice = Double.parseDouble(monthlyPrice);

        carnameEDT.setText(carName);
        carmodelEDT.setText(model);
        mileageEDT.setText(mileage+" KM");

        if (url != null) {
            Glide.with(this)
                    .load(url)
                    .into(imageView);
        }
    }

   public void delete(View view){
       showDeleteConfirmationDialog();

   }
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(carInfoForAdmin.this);
        builder.setMessage("Are you sure you want to delete this?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                performDeleteOperation();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void performDeleteOperation() {

            String url = vars.BASE_URL+"/rental-car/car/delete.php";
            RequestQueue queue = Volley.newRequestQueue(carInfoForAdmin.this);
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject res = new JSONObject(response);
                        String status = res.getString("status");
                        if(status.equals("success")){
                            Intent intent = new Intent(carInfoForAdmin.this, admin_home.class);
                            startActivity(intent);
                        }
                        String message = res.getString("message");
                        Toast.makeText(carInfoForAdmin.this, message, Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(carInfoForAdmin.this, "Error: " + volleyError.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", id+"");

                    return params;
                }
            };

            queue.add(request);




    }
    public void edit(View view){
        Intent intent = new Intent(this, edit_car.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

}