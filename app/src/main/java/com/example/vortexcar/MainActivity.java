package com.example.vortexcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void myfavs(View view) {
        Intent intent = new Intent(this, MyFavourites.class);
        startActivity(intent);
    }  public void mybookings(View view) {
        Intent intent = new Intent(this, MyBookings.class);
        startActivity(intent);
    }
    //public void myprofile(View view) {
//    Intent intent = new Intent(this, .class);
//    startActivity(intent);
//}
}

//TODO:
// 1-search for car name
// 2-explore
// 3-favorites
// 4-bookings
// 5-profile
// 6-add cars pictures in the listview with the name
// 7-when the user click on a car pic go to carinfo

