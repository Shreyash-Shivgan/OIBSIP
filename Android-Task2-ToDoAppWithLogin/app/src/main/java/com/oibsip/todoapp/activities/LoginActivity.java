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
import com.oibsip.todoapp.session.SessionManager;
import com.oibsip.todoapp.utils.HashUtil;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout emailInputLayout, passwordInputLayout;
    private TextInputEditText etEmail, etPassword;
    private MaterialButton btnLogin;
    private TextView tvSwitchToRegister;

    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        // Redirect if already logged in
        if (sessionManager.isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, MainTaskActivity.class));
            finish();
            return;
        }

        initViews();
        setupListeners();
    }

    private void initViews() {
        emailInputLayout = findViewById(R.id.emailInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSwitchToRegister = findViewById(R.id.tvSwitchToRegister);
    }

    private void setupListeners() {
        btnLogin.setOnClickListener(v -> performLogin());

        tvSwitchToRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
    }

    private void performLogin() {
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        boolean isValid = true;

        if (TextUtils.isEmpty(email)) {
            emailInputLayout.setError("Email is required");
            isValid = false;
        } else {
            emailInputLayout.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            passwordInputLayout.setError("Password is required");
            isValid = false;
        } else {
            passwordInputLayout.setError(null);
        }

        if (!isValid) return;

        // Hash input password
        String hashedPassword = HashUtil.hashPassword(password);

        // Validate user in DB
        User user = dbHelper.loginUser(email, hashedPassword);

        if (user != null) {
            // Save login session
            sessionManager.createLoginSession(user.getId(), user.getName(), user.getEmail());
            Toast.makeText(this, "Welcome " + user.getName(), Toast.LENGTH_SHORT).show();

            startActivity(new Intent(LoginActivity.this, MainTaskActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_LONG).show();
        }
    }
}
