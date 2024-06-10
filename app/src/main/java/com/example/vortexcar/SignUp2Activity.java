package com.example.vortexcar;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp2Activity extends AppCompatActivity {

    private TextView phoneEditText;
    private TextView emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        EdgeToEdge.enable(this);
        phoneEditText = findViewById(R.id.phoneNumberEditText);
        emailEditText = findViewById(R.id.emailEditText);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save relevant data to the bundle here
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore saved state from the bundle here
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Handle configuration changes here, such as adjusting layout
    }

    public void signup1(View view) {
        Intent intent = new Intent(this, SignUp1Activity.class);
        startActivity(intent);
    }

    public void signup3(View view) {
        Intent intent1 = getIntent();
        String firstName = intent1.getStringExtra("firstName");
        String lastName = intent1.getStringExtra("lastName");
        String selectedItem = intent1.getStringExtra("gender");

        String email = emailEditText.getText().toString();
        String phone = phoneEditText.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(SignUp2Activity.this, "Please enter the Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone.isEmpty()) {
            Toast.makeText(SignUp2Activity.this, "Please enter the Phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, SignUp3Activity.class);
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        intent.putExtra("gender", selectedItem);
        intent.putExtra("phone", phone);
        intent.putExtra("email", email);
        startActivity(intent);
    }
}
