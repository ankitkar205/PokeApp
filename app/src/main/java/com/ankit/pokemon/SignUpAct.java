package com.ankit.pokemon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpAct extends AppCompatActivity {

    EditText etNewUsername, etNewPassword;
    Button btnRegister;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize UI elements
        etNewUsername = findViewById(R.id.etNewUsername);
        etNewPassword = findViewById(R.id.etNewPassword);
        btnRegister = findViewById(R.id.btnRegister);
        dbHelper = new DatabaseHelper(this);

        // Handle Registration Button Click
        btnRegister.setOnClickListener(view -> {
            String username = etNewUsername.getText().toString().trim();
            String password = etNewPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignUpAct.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean isInserted = dbHelper.insertUser(username, password);
                if (isInserted) {
                    Toast.makeText(SignUpAct.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpAct.this, MainActivity.class)); // Go back to Login
                    finish();
                } else {
                    Toast.makeText(SignUpAct.this, "Username already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
