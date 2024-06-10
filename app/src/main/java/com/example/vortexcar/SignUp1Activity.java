package com.example.vortexcar;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp1Activity extends AppCompatActivity {
    EditText lastNameEditText;
    EditText firstNameEditText;
    private Spinner genderSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up1);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        genderSpinner = findViewById(R.id.genderSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);
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

    public void login(View view) {
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
    }

    public void signup2(View view) {
        String selectedItem = genderSpinner.getSelectedItem().toString();
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();

        if (firstName.isEmpty()) {
            Toast.makeText(SignUp1Activity.this, "Please enter the first name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (lastName.isEmpty()) {
            Toast.makeText(SignUp1Activity.this, "Please enter the last name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedItem.isEmpty() || selectedItem.equals("Select an item")) {
            Toast.makeText(SignUp1Activity.this, "Please select a gender", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, SignUp2Activity.class);
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        intent.putExtra("gender", selectedItem);
        startActivity(intent);
    }
}
