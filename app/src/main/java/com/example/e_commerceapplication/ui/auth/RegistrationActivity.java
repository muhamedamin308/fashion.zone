package com.example.e_commerceapplication.ui.auth;

import static com.example.e_commerceapplication.general.Constants.ADMIN_MODE;
import static com.example.e_commerceapplication.general.Constants.MONTH;
import static com.example.e_commerceapplication.general.Constants.USERS;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.databinding.ActivityRegistrationBinding;
import com.example.e_commerceapplication.database.DataLayer;
import com.example.e_commerceapplication.classes.users.User;
import com.example.e_commerceapplication.general.Constants;
import com.example.e_commerceapplication.ui.activities.MainActivity;

import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    DataLayer dataLayer;
    ActivityRegistrationBinding binding;
    private DatePickerDialog datePickerDialog;

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
        if (isFirstTime) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime", false);
            editor.apply();
            Intent intent = new Intent(RegistrationActivity.this, OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }

        initDatePicker();

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


    private String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = makeDateString(day, month, year);
            binding.birthdate.setText(date);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        return MONTH.get(month);
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}