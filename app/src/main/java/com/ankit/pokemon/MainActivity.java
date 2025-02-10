package com.ankit.pokemon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declare UI elements
    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvSignUp;
    DatabaseHelper dbHelper; // Database handler

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);
        dbHelper = new DatabaseHelper(this); // Initialize DatabaseHelper

        // Handle Login Button Click
        btnLogin.setOnClickListener(view -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean userExists = dbHelper.checkUser(username, password);
                if (userExists) {
                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                    // Navigate to Pokefinder Activity
                    Intent intent = new Intent(MainActivity.this, pokefinder.class);
                    intent.putExtra("Username", username);
                    startActivity(intent);

                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Handle SignUp Button Click
        tvSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SignUpAct.class);
            startActivity(intent);
        });
    }
}
