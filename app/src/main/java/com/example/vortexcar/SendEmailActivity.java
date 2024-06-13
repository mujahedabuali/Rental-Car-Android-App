package com.example.vortexcar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SendEmailActivity extends AppCompatActivity {
    private EditText emailRecipient;
    private EditText emailSubject;
    private EditText emailMessage;
    private Button sendEmailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        emailRecipient = findViewById(R.id.emailRecipient);
        emailSubject = findViewById(R.id.emailSubject);
        emailMessage = findViewById(R.id.emailMessage);
        sendEmailButton = findViewById(R.id.sendEmailButton);

        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    private void sendEmail() {
        String recipient = emailRecipient.getText().toString().trim();
        String subject = emailSubject.getText().toString().trim();
        String message = emailMessage.getText().toString().trim();

        if (recipient.isEmpty() || subject.isEmpty() || message.isEmpty()) {
            Toast.makeText(SendEmailActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", recipient, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SendEmailActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}