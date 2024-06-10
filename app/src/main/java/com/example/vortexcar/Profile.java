package com.example.vortexcar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Profile extends AppCompatActivity {

    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText genderEditText;
    EditText emailEditText;
    EditText phoneEditText;
    EditText passwordEditText;
    CheckBox showPasswordCheckBox;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        genderEditText = findViewById(R.id.genderEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);
        logoutButton = findViewById(R.id.logoutButton);


        // Retrieve user data from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String firstName = preferences.getString("first_name", "");
        String lastName = preferences.getString("last_name", "");
        String gender = preferences.getString("gender", "");
        String email = preferences.getString("email", "");
        String phone = preferences.getString("phone", "");
        String password = preferences.getString("password", ""); // Retrieve password

        // Display data in the EditText fields
        firstNameEditText.setText(firstName);
        lastNameEditText.setText(lastName);
        genderEditText.setText(gender);
        emailEditText.setText(email);
        phoneEditText.setText(phone);
        passwordEditText.setText(password); // Set password

        // Make the EditText fields non-editable
        setFieldsEditable(false);

        showPasswordCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(v);
            }
        });
    }

    public void gotomain(View view) {
        Intent intent = new Intent(this, homePage.class);
        startActivity(intent);
    }

    public void togglePasswordVisibility(View view) {
        if (showPasswordCheckBox.isChecked()) {
            passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        passwordEditText.setSelection(passwordEditText.length());
    }

    public void logout(View view) {
        Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, loginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, homePage.class);
        startActivity(intent);
    }

    public void enableEditing(View view) {
        setFieldsEditable(true);

        // Debug logging
        View saveButton = findViewById(R.id.saveButton);
        View editButton = findViewById(R.id.editButton);

        if (saveButton != null) {
            saveButton.setVisibility(View.VISIBLE); // Change visibility to VISIBLE
        } else {
            Toast.makeText(this, "Save button not found", Toast.LENGTH_SHORT).show();
        }

        if (editButton != null) {
            editButton.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "Edit button not found", Toast.LENGTH_SHORT).show();
        }
    }


    public void saveChanges(View view) {
        // Save changes to SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("first_name", firstNameEditText.getText().toString());
        editor.putString("last_name", lastNameEditText.getText().toString());
        editor.putString("gender", genderEditText.getText().toString());
        editor.putString("email", emailEditText.getText().toString());
        editor.putString("phone", phoneEditText.getText().toString());
        editor.putString("password", passwordEditText.getText().toString());

        editor.apply();

        setFieldsEditable(false);

        View saveButton = findViewById(R.id.saveButton);
        View editButton = findViewById(R.id.editButton);

        if (saveButton != null) {
            saveButton.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "Save button not found", Toast.LENGTH_SHORT).show();
        }

        if (editButton != null) {
            editButton.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "Edit button not found", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

        // Send updated data to the backend server
        new UpdateProfileTask().execute(
                firstNameEditText.getText().toString(),
                lastNameEditText.getText().toString(),
                genderEditText.getText().toString(),
                emailEditText.getText().toString(),
                phoneEditText.getText().toString(),
                passwordEditText.getText().toString()
        );
    }

    private void setFieldsEditable(boolean isEditable) {
        int inputType = isEditable ? InputType.TYPE_CLASS_TEXT : InputType.TYPE_NULL;

        firstNameEditText.setInputType(inputType);
        lastNameEditText.setInputType(inputType);
        genderEditText.setInputType(inputType);
        emailEditText.setInputType(inputType);
        phoneEditText.setInputType(inputType);
        passwordEditText.setInputType(isEditable ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_NULL);

        // Re-apply the cursor position at the end of the text if the fields are editable
        if (isEditable) {
            firstNameEditText.setSelection(firstNameEditText.length());
            lastNameEditText.setSelection(lastNameEditText.length());
            genderEditText.setSelection(genderEditText.length());
            emailEditText.setSelection(emailEditText.length());
            phoneEditText.setSelection(phoneEditText.length());
            passwordEditText.setSelection(passwordEditText.length());
        }
    }

    private class UpdateProfileTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String firstName = params[0];
            String lastName = params[1];
            String gender = params[2];
            String email = params[3];
            String phone = params[4];
            String password = params[5];

            try {
                URL url = new URL("http://192.168.50.88/VortexCar/update_profile.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestProperty("Content-Type", "application/json");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("first_name", firstName);
                jsonObject.put("last_name", lastName);
                jsonObject.put("gender", gender);
                jsonObject.put("email", email);
                jsonObject.put("phone", phone);
                jsonObject.put("password", password);

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                writer.write(jsonObject.toString());
                writer.flush();
                writer.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    JSONObject responseObject = new JSONObject(response.toString());
                    return responseObject.getBoolean("success");
                } else {
                    return false;
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(Profile.this, "Profile updated on server", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Profile.this, "Failed to update profile on server", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
