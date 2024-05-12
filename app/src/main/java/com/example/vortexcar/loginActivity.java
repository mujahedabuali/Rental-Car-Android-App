package com.example.vortexcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class loginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


    }
    public void registerNow(View view) {
        Intent intent = new Intent(this, SignUp1Activity.class);
        startActivity(intent);
    }
    public void Intro(View view) {
        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
    }
}
//TODO:
// 1-forget the pass
// 2-check user login validity
// 3-browse as a visitor
// 4-view password
// 5-when the user login and its a customer go to main activity (if admin another activity)
