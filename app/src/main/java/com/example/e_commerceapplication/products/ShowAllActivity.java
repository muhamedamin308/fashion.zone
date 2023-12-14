package com.example.e_commerceapplication.products;

import static com.example.e_commerceapplication.general.Constants.USERS;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.adapter.ShowAllAdapter;
import com.example.e_commerceapplication.databinding.ActivityShowAllBinding;
import com.example.e_commerceapplication.general.data.DataLayer;
import com.example.e_commerceapplication.models.product.ShowAllModel;

import java.util.ArrayList;
import java.util.List;

public class ShowAllActivity extends AppCompatActivity {
    List<ShowAllModel> showAllModelList;
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
        showAllModelList = new ArrayList<>();
        showAllAdapter = new ShowAllAdapter(this, showAllModelList);
        binding.showAllRec.setAdapter(showAllAdapter);


        String type = getIntent().getStringExtra("type");


        if (type == null) {
           dataLayer.showAllDatabase(false, binding.category, null, showAllModelList, showAllAdapter);
        }

        if (type != null) {
            dataLayer.showAllDatabase(true, binding.category, type, showAllModelList, showAllAdapter);
        }

        binding.exit.setOnClickListener(v -> finish());
    }
}