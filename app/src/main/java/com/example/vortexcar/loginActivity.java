package com.example.vortexcar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


import java.util.HashMap;
import java.util.Map;

public class loginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.TextView11);
        passwordEditText = findViewById(R.id.TextView111);
        ImageView passwordToggle = findViewById(R.id.imageView15);

        passwordToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    public void registerNow(View view) {
        Intent intent = new Intent(this, SignUp1Activity.class);
        startActivity(intent);
    }

    public void Intro(View view) {
        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
    }

    public void mainn(View view) {
        Intent intent = new Intent(this, homePage.class);
        startActivity(intent);
    }

    public void browseAsVisitor(View view) {
        Intent intent = new Intent(this, homePage.class);
        startActivity(intent);
    }

    public void forgetPassword(View view) {
        Intent intent = new Intent(this, ForgetPassword.class);
        startActivity(intent);
    }

    private void togglePasswordVisibility() {
        if (passwordEditText.getInputType() == (android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            passwordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            passwordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        passwordEditText.setSelection(passwordEditText.length());
    }

    private void attemptLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
            return;
        }

        loginUser(email, password);
    }

    private void loginUser(final String email, final String password) {
        String url = "http://192.168.88.6/rental-car/login.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            if (jsonResponse.has("status") && jsonResponse.has("data")) {
                                String status = jsonResponse.getString("status");

                                if ("success".equals(status)) {
                                    JSONArray dataArray = jsonResponse.getJSONArray("data");
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        JSONObject userObject = dataArray.getJSONObject(i);

                                        if (userObject.has("email") && userObject.getString("email").equals(email) &&
                                                userObject.has("password") && userObject.getString("password").equals(password)){ // Password check added

                                            Toast.makeText(loginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(loginActivity.this, homePage.class);
                                            startActivity(intent);
                                            finish();
                                            break;
                                        }
                                    }
                                } else {
                                    String message = jsonResponse.getString("message");
                                    Toast.makeText(loginActivity.this, message,  Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(loginActivity.this, "Unexpected server response format", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(loginActivity.this, "Error parsing server response", Toast.LENGTH_SHORT).show();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(loginActivity.this, "Error connecting to server", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        queue.add(stringRequest);
    }

}
//TODO: check user login