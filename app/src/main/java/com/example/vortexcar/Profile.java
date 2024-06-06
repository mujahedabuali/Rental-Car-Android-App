package com.example.vortexcar;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Profile extends AppCompatActivity {

    EditText passwordEditText;
    CheckBox showPasswordCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        passwordEditText = findViewById(R.id.passwordEditText);
        showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);

    }
    public void gotomain(View view) {
        Intent intent = new Intent(this, homePage.class);
        startActivity(intent);
    }
    public void togglePasswordVisibility(View view) {
        if (showPasswordCheckBox.isChecked()) {
            // Show password
            passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            // Hide password
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    public void logout(View view) {
        // Display a logout message
        Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show();

        // Navigate to the login activity
        Intent intent = new Intent(this, loginActivity.class);
        // Clear the back stack to prevent going back to the profile
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // Override onBackPressed to prevent going back to the profile activity
        super.onBackPressed();
        Intent intent = new Intent(this, homePage.class);
        startActivity(intent);
    }
}
//TODO:
// +bring user info +can change photo
