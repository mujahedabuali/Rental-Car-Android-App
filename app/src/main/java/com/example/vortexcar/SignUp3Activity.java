package com.example.vortexcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUp3Activity extends AppCompatActivity {

    private EditText passwordEditText;
    private EditText ConfirmpasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3);
        EdgeToEdge.enable(this);
        passwordEditText = findViewById(R.id.passwordEditText);
        ConfirmpasswordEditText = findViewById(R.id.confirmPasswordEditText);
        ImageView passwordToggle = findViewById(R.id.imageView15);

        passwordToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });
    }


    public void signup2(View view) {
        Intent intent = new Intent(this, SignUp2Activity.class);
        startActivity(intent);
    }
    public void signup4(View view) {
        Intent intent = new Intent(this, SignUp4Activity.class);
        startActivity(intent);
    }
    private void togglePasswordVisibility() {
        if (passwordEditText.getInputType() == (android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            passwordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            passwordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        passwordEditText.setSelection(passwordEditText.length());

        if (ConfirmpasswordEditText.getInputType() == (android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            ConfirmpasswordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            ConfirmpasswordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        ConfirmpasswordEditText.setSelection(ConfirmpasswordEditText.length());
    }

}
//TODO:  make sure password match the confirm password if not tell the user to check it again
// + make the password visible or not for the user