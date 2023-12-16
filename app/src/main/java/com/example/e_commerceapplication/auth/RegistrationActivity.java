package com.example.e_commerceapplication.auth;

import static com.example.e_commerceapplication.general.Constants.ADMIN_MODE;
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
import com.example.e_commerceapplication.general.data.DataLayer;
import com.example.e_commerceapplication.models.users.User;
import com.example.e_commerceapplication.start.MainActivity;

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
        if (isFirstTime){
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


    private String getTodayDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
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
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }
    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}