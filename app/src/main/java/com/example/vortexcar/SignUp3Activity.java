package com.example.vortexcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUp3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
    }

    public void signup2(View view) {
        Intent intent = new Intent(this, SignUp2Activity.class);
        startActivity(intent);
    }
    public void signup4(View view) {
        Intent intent = new Intent(this, SignUp4Activity.class);
        startActivity(intent);
    }


}