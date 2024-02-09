package com.example.e_commerceapplication.ui.products;

import static com.example.e_commerceapplication.general.Constants.USERS;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.e_commerceapplication.adapter.ShowAllAdapter;
import com.example.e_commerceapplication.databinding.ActivityShowAllBinding;
import com.example.e_commerceapplication.database.DataLayer;
import com.example.e_commerceapplication.classes.product.AllProducts;

import java.util.ArrayList;
import java.util.List;

public class ShowAllActivity extends AppCompatActivity {
    List<AllProducts> allProductsList;
    ShowAllAdapter showAllAdapter;
    DataLayer dataLayer;
    ActivityShowAllBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowAllBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataLayer = new DataLayer(USERS);

        binding.showAllRec.setLayoutManager(new GridLayoutManager(this, 2));
        allProductsList = new ArrayList<>();
        showAllAdapter = new ShowAllAdapter(this, allProductsList);
        binding.showAllRec.setAdapter(showAllAdapter);


        String type = getIntent().getStringExtra("type");


        if (type == null) {
           dataLayer.showAllDatabase(false, binding.category, null, allProductsList, showAllAdapter);
        }

        if (type != null) {
            dataLayer.showAllDatabase(true, binding.category, type, allProductsList, showAllAdapter);
        }

        binding.exit.setOnClickListener(v -> finish());
    }
}