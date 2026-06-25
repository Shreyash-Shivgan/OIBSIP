package com.oibsip.todoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.oibsip.todoapp.R;
import com.oibsip.todoapp.database.DatabaseHelper;
import com.oibsip.todoapp.models.User;
import com.oibsip.todoapp.utils.HashUtil;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout nameInputLayout, emailInputLayout, passwordInputLayout;
    private TextInputEditText etName, etEmail, etPassword;
    private MaterialButton btnRegister;
    private TextView tvSwitchToLogin;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);

        initViews();
        setupListeners();
    }

    private void initViews() {
        nameInputLayout = findViewById(R.id.nameInputLayout);
        emailInputLayout = findViewById(R.id.emailInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvSwitchToLogin = findViewById(R.id.tvSwitchToLogin);
    }

    private void setupListeners() {
        btnRegister.setOnClickListener(v -> performRegistration());

        tvSwitchToLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void performRegistration() {
        String name = etName.getText() != null ? etName.getText().toString().trim() : "";
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        boolean isValid = true;

        if (TextUtils.isEmpty(name)) {
            nameInputLayout.setError("Name is required");
            isValid = false;
        } else {
            nameInputLayout.setError(null);
        }

        if (TextUtils.isEmpty(email)) {
            emailInputLayout.setError("Email is required");
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.setError("Please enter a valid email address");
            isValid = false;
        } else {
            emailInputLayout.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            passwordInputLayout.setError("Password is required");
            isValid = false;
        } else if (password.length() < 6) {
            passwordInputLayout.setError("Password must be at least 6 characters long");
            isValid = false;
        } else {
            passwordInputLayout.setError(null);
        }

        if (!isValid) return;

        // Check if email already registered
        if (dbHelper.checkEmailExists(email)) {
            emailInputLayout.setError("Email is already registered");
            Toast.makeText(this, "Email is already taken", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hash the password
        String hashedPassword = HashUtil.hashPassword(password);

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPasswordHash(hashedPassword);

        long rowId = dbHelper.registerUser(newUser);

        if (rowId != -1) {
            Toast.makeText(this, "Registration Successful! Please login.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Registration Failed. Try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
