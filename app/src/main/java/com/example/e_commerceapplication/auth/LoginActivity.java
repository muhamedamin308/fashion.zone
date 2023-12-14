package com.example.e_commerceapplication.auth;

import static com.example.e_commerceapplication.general.Constants.ADMIN_MODE;
import static com.example.e_commerceapplication.general.Constants.USERS;
import static com.example.e_commerceapplication.models.users.Admin.adminEmail;
import static com.example.e_commerceapplication.models.users.Admin.adminPassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.databinding.ActivityLoginBinding;
import com.example.e_commerceapplication.general.data.DataLayer;
import com.example.e_commerceapplication.models.users.Admin;
import com.example.e_commerceapplication.start.MainActivity;

public class LoginActivity extends AppCompatActivity {
    DataLayer dataLayer;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataLayer = new DataLayer(USERS);

        binding.signin.setOnClickListener(v -> signIn());
    }

    public void signIn() {
        String userEmail, userPassword;

        binding.errorEmail.setVisibility(View.INVISIBLE);
        binding.errorPassword.setText(R.string.please_enter_password);
        binding.errorPassword.setVisibility(View.INVISIBLE);

        userEmail = binding.email.getText().toString();
        userPassword = binding.password.getText().toString();

        if (TextUtils.isEmpty(userEmail)) {
            binding.errorEmail.setVisibility(View.VISIBLE);
            return;
        } else if (TextUtils.isEmpty(userPassword)) {
            binding.errorPassword.setVisibility(View.VISIBLE);
            return;
        }
        if (userPassword.length() < 8) {
            binding.errorPassword.setVisibility(View.VISIBLE);
            binding.errorPassword.setText(R.string.password_requirements);
            return;
        }
        if (userEmail.equals(adminEmail) && userPassword.equals(adminPassword)) {
            ADMIN_MODE = true;
            dataLayer.loginUser(adminEmail, adminPassword, this);
        } else {
            ADMIN_MODE = false;
            dataLayer.loginUser(userEmail, userPassword, this);
        }
    }

    public void signUp(View view) {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        finish();
    }
}