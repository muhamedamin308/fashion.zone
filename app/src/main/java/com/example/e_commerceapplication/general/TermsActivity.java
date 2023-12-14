package com.example.e_commerceapplication.general;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.databinding.ActivityTermsBinding;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTermsBinding binding = ActivityTermsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.exit.setOnClickListener(view -> finish());
    }
}