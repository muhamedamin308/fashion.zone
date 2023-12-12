package com.example.e_commerceapplication.general;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.e_commerceapplication.R;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        ImageView exit = findViewById(R.id.exit);
        exit.setOnClickListener(view -> finish());
    }
}