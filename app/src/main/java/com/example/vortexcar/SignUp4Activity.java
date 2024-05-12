package com.example.vortexcar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp4Activity extends AppCompatActivity {

    TextView nameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up4);

        nameTextView = findViewById(R.id.nameTextView);

        Intent intent = getIntent();
        if (intent != null) {
            String firstName = intent.getStringExtra("firstName");
            String lastName = intent.getStringExtra("lastName");

            if (firstName != null && lastName != null) {
                String fullName = firstName + " " + lastName;
                nameTextView.setText(fullName);
            }
        }
    }
    public void signup3(View view) {
        Intent intent = new Intent(this, SignUp3Activity.class);
        startActivity(intent);
    }

}
//TODO:
// 1-get firstname and lastname from SignUp1
// 2-change the pic
// 3-allow the user to choose a pic for his profile
// 4-when the user click on start create user profile & fo to main activity