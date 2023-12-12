package com.example.e_commerceapplication.auth;

import static com.example.e_commerceapplication.general.Constants.ADMIN_MODE;
import static com.example.e_commerceapplication.general.Constants.USERS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.general.data.DataLayer;
import com.example.e_commerceapplication.models.users.User;
import com.example.e_commerceapplication.start.MainActivity;
import com.example.e_commerceapplication.start.OnBoardingActivity;

public class RegistrationActivity extends AppCompatActivity {

    EditText name, email, password;
    SharedPreferences sharedPreferences;
    DataLayer dataLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Button signUp = findViewById(R.id.add_new_product_btn);
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

        signUp.setOnClickListener(v -> {
            signUp();
        });
    }

    public void signUp() {
        String username, userEmail, userPassword;
        TextView errorUsername = findViewById(R.id.errorUsername);
        errorUsername.setVisibility(View.INVISIBLE);
        TextView errorUserEmail = findViewById(R.id.errorEmail);
        errorUserEmail.setVisibility(View.INVISIBLE);
        TextView errorUserPassword = findViewById(R.id.errorPassword);
        errorUserPassword.setVisibility(View.INVISIBLE);
        errorUserPassword.setText(R.string.please_enter_password);
        username = name.getText().toString();
        userEmail = email.getText().toString();
        userPassword = password.getText().toString();

        if (TextUtils.isEmpty(username)) {
            errorUsername.setVisibility(View.VISIBLE);
            return;
        } else if (TextUtils.isEmpty(userEmail)) {
            errorUserEmail.setVisibility(View.VISIBLE);
            return;
        } else if (TextUtils.isEmpty(userPassword)) {
            errorUserPassword.setVisibility(View.VISIBLE);
            return;
        }
        if (userPassword.length() < 8) {
            errorUserPassword.setVisibility(View.VISIBLE);
            errorUserPassword.setText(R.string.password_requirements);
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