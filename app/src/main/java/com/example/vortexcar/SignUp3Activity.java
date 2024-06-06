package com.example.vortexcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp3Activity extends AppCompatActivity {

    private EditText passwordEditText;
    private EditText ConfirmpasswordEditText;
    private static final String REGISTER_URL = "http://10.0.2.2/rental-car/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3);
        EdgeToEdge.enable(this);
        passwordEditText = findViewById(R.id.passwordEditText);
        ConfirmpasswordEditText = findViewById(R.id.confirmPasswordEditText);
        ImageView passwordToggle = findViewById(R.id.imageView15);

        passwordToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });
    }


    public void signup2(View view) {
        Intent intent = new Intent(this, SignUp2Activity.class);
        startActivity(intent);
    }
    public void signup(View view) {
        Intent intent1 = getIntent();
        String firstName = intent1.getStringExtra("firstName");
        String lastName = intent1.getStringExtra("lastName");
        String gender = intent1.getStringExtra("gender");
        String email = intent1.getStringExtra("email");
        String phone = intent1.getStringExtra("phone");

        String pas = passwordEditText.getText().toString();
        String pas2 = ConfirmpasswordEditText.getText().toString();

        if (pas.isEmpty()) {
            Toast.makeText(SignUp3Activity.this, "Please enter the password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pas2.isEmpty()) {
            Toast.makeText(SignUp3Activity.this, "Please enter the password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pas.equals(pas2)) {
            Toast.makeText(SignUp3Activity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String status = jsonResponse.getString("status");
                            String message = jsonResponse.getString("message");

                            Toast.makeText(SignUp3Activity.this, message, Toast.LENGTH_SHORT).show();

                            if (status.equals("success")) {
                                Toast.makeText(SignUp3Activity.this, "Done!!!", Toast.LENGTH_SHORT).show();
                                go();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SignUp3Activity.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUp3Activity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", firstName +" "+ lastName);
                params.put("type", "user");
                params.put("phone", phone);
                params.put("password", pas2);
                params.put("email", email);
                params.put("gender", gender);
                return params;
            }
        };

        Volley.newRequestQueue(SignUp3Activity.this).add(stringRequest);
    }
    private void togglePasswordVisibility() {
        if (passwordEditText.getInputType() == (android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            passwordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            passwordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        passwordEditText.setSelection(passwordEditText.length());

        if (ConfirmpasswordEditText.getInputType() == (android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            ConfirmpasswordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            ConfirmpasswordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        ConfirmpasswordEditText.setSelection(ConfirmpasswordEditText.length());
    }

    private void go() {
        Intent intent = new Intent(this, SignUp4Activity.class);
        startActivity(intent);
    }

}
