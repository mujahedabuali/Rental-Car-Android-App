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