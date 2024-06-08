package com.example.vortexcar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText genderEditText;
    EditText emailEditText;
    EditText phoneEditText;
    EditText passwordEditText;
    CheckBox showPasswordCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        genderEditText = findViewById(R.id.genderEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);

        // Retrieve user data from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String firstName = preferences.getString("first_name", "");
        String lastName = preferences.getString("last_name", "");
        String gender = preferences.getString("gender", "");
        String email = preferences.getString("email", "");
        String phone = preferences.getString("phone", "");

        // Display data in the EditText fields
        firstNameEditText.setText(firstName);
        lastNameEditText.setText(lastName);
        genderEditText.setText(gender);
        emailEditText.setText(email);
        phoneEditText.setText(phone);

        // Make the EditText fields non-editable
        firstNameEditText.setInputType(InputType.TYPE_NULL);
        lastNameEditText.setInputType(InputType.TYPE_NULL);
        genderEditText.setInputType(InputType.TYPE_NULL);
        emailEditText.setInputType(InputType.TYPE_NULL);
        phoneEditText.setInputType(InputType.TYPE_NULL);
        passwordEditText.setInputType(InputType.TYPE_NULL);

        showPasswordCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(v);
            }
        });
    }

    public void gotomain(View view) {
        Intent intent = new Intent(this, homePage.class);
        startActivity(intent);
    }

    public void togglePasswordVisibility(View view) {
        if (showPasswordCheckBox.isChecked()) {
            passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        passwordEditText.setSelection(passwordEditText.length());
    }

    public void logout(View view) {
        Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, loginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, homePage.class);
        startActivity(intent);
    }
}
