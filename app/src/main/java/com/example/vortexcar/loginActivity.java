package com.example.vortexcar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
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

import java.util.HashMap;
import java.util.Map;

public class loginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.TextView11);
        passwordEditText = findViewById(R.id.TextView111);
        ImageView passwordToggle = findViewById(R.id.imageView15);

//        passwordToggle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                togglePasswordVisibility();
//            }
//        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        queue = Volley.newRequestQueue(this);

        if (savedInstanceState != null) {
            emailEditText.setText(savedInstanceState.getString("email"));
            passwordEditText.setText(savedInstanceState.getString("password"));
        }
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
        Intent intent = new Intent(this, homePage_visitor.class);
        startActivity(intent);
    }

    public void forgetPassword(View view) {
        Intent intent = new Intent(this, ForgetPassword.class);
        startActivity(intent);
    }

//    private void togglePasswordVisibility() {
//        if (passwordEditText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
//            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//        } else {
//            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        }
//        passwordEditText.setSelection(passwordEditText.length());
//    }

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
        String url = "http://10.0.2.2/rental-car/login.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");

                            if (status.equals("success")) {
                                String message = jsonObject.getString("message");
                                String accountId = jsonObject.getString("Account_id");
                                String accountType = jsonObject.getString("Account_type");
                                String name = jsonObject.optString("name", "");
                                String gender = jsonObject.optString("gender", "");
                                String phone = jsonObject.optString("phone", "");

                                String[] nameParts = name.split(" ", 2);
                                String firstName = nameParts.length > 0 ? nameParts[0] : "";
                                String lastName = nameParts.length > 1 ? nameParts[1] : "";

                                saveUserData(email, firstName, lastName, gender, phone);

                                if (accountType.equals("user")) {
                                    Intent intent = new Intent(loginActivity.this, homePage.class);
                                    startActivity(intent);
                                } else if (accountType.equals("admin")) {
                                    Intent intent = new Intent(loginActivity.this, admin_home.class);
                                    startActivity(intent);
                                }
                            } else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(loginActivity.this, message, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(loginActivity.this, "JSON error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(loginActivity.this, "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "login");
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    private void saveUserData(String email, String firstName, String lastName, String gender, String phone) {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", email);
        editor.putString("first_name", firstName);
        editor.putString("last_name", lastName);
        editor.putString("gender", gender);
        editor.putString("phone", phone);
        editor.apply();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("email", emailEditText.getText().toString().trim());
        outState.putString("password", passwordEditText.getText().toString().trim());
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveCredentials();
    }

    private void saveCredentials() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", emailEditText.getText().toString().trim());
        editor.putString("password", passwordEditText.getText().toString().trim());
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
