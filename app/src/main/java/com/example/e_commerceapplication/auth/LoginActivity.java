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
import com.example.e_commerceapplication.general.data.DataLayer;
import com.example.e_commerceapplication.models.users.Admin;
import com.example.e_commerceapplication.start.MainActivity;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    DataLayer dataLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Button signIn = findViewById(R.id.signin);
        dataLayer = new DataLayer(USERS);

        signIn.setOnClickListener(v -> signIn());
    }

    public void signIn() {
        String userEmail, userPassword;
        TextView errorUserEmail = findViewById(R.id.errorEmail);
        errorUserEmail.setVisibility(View.INVISIBLE);
        TextView errorUserPassword = findViewById(R.id.errorPassword);
        errorUserPassword.setText(R.string.please_enter_password);
        errorUserPassword.setVisibility(View.INVISIBLE);
        userEmail = email.getText().toString();
        userPassword = password.getText().toString();

        if (TextUtils.isEmpty(userEmail)) {
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