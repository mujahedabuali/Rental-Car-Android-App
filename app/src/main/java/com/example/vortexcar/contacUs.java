package com.example.vortexcar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class contacUs extends AppCompatActivity {
    private Button sendEmailButton;
    private Button chatWithAdminButton;
    private Button callCenterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contac_us);

        sendEmailButton = findViewById(R.id.sendEmailButton);
        chatWithAdminButton = findViewById(R.id.chatWithAdminButton);
        callCenterButton = findViewById(R.id.callCenterButton);

        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contacUs.this, SendEmailActivity.class);
                startActivity(intent);
            }
        });

        chatWithAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contacUs.this, ChatWithAdminActivity.class);
                startActivity(intent);
            }
        });

        callCenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0598109016"));
                startActivity(intent);
            }
        });
    }
}