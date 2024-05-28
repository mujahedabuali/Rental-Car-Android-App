package com.example.vortexcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
    public void login(View view) {
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
    }
    public void signup2(View view) {
        Intent intent = new Intent(this, SignUp2Activity.class);
        startActivity(intent);
        goToSignUp4(view);

    }
    public void goToSignUp4(View view) {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();

        Intent intent = new Intent(this, SignUp4Activity.class);
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
    }
}
//TODO:make sure the fisrt name and last name goes to SignUp4 & the next into SignUp2