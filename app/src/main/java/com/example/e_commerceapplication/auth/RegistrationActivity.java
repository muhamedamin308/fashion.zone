package com.example.e_commerceapplication.auth;

import static com.example.e_commerceapplication.general.Constants.ADMIN_MODE;
import static com.example.e_commerceapplication.general.Constants.USERS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.databinding.ActivityRegistrationBinding;
import com.example.e_commerceapplication.general.data.DataLayer;
import com.example.e_commerceapplication.models.users.User;
import com.example.e_commerceapplication.start.MainActivity;
import com.example.e_commerceapplication.start.OnBoardingActivity;

public class RegistrationActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    DataLayer dataLayer;
    ActivityRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataLayer = new DataLayer(USERS);

        if (ADMIN_MODE) {
            dataLayer.getAuth().signOut();
        } else {
            if (dataLayer.getAuth().getCurrentUser() != null) {
                ADMIN_MODE = false;
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                finish();
            }
        }

        sharedPreferences = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("firstTime", true);
        if (isFirstTime){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime", false);
            editor.apply();
            Intent intent = new Intent(RegistrationActivity.this, OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }

        binding.signUpUser.setOnClickListener(v -> signUp());
    }

    public void signUp() {
        String username, userEmail, userPassword;

        binding.errorUsername.setVisibility(View.INVISIBLE);
        binding.errorEmail.setVisibility(View.INVISIBLE);
        binding.errorPassword.setVisibility(View.INVISIBLE);
        binding.errorPassword.setText(R.string.please_enter_password);

        username = binding.username.getText().toString();
        userEmail = binding.email.getText().toString();
        userPassword = binding.password.getText().toString();

        if (TextUtils.isEmpty(username)) {
            binding.errorUsername.setVisibility(View.VISIBLE);
            return;
        } else if (TextUtils.isEmpty(userEmail)) {
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
        User user = new User(username, userEmail, userPassword, 0.0);
        ADMIN_MODE = false;
        dataLayer.registerUser(userEmail, userPassword, this, user);
    }
    public void register(View view) {
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        finish();
    }
}