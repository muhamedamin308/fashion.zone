package com.example.e_commerceapplication.products;

import static com.example.e_commerceapplication.general.Constants.SHOW_ALL;
import static com.example.e_commerceapplication.general.Constants.USERS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.adapter.ShowAllAdapter;
import com.example.e_commerceapplication.databinding.ActivitySearchBinding;
import com.example.e_commerceapplication.general.data.DataLayer;
import com.example.e_commerceapplication.models.product.ShowAllModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding binding;
    DataLayer dataLayer;
    ShowAllAdapter adapter;
    List<ShowAllModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataLayer = new DataLayer(USERS);

        binding.searchRec.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();

        adapter = new ShowAllAdapter(this, list);

        binding.searchRec.setAdapter(adapter);

        binding.searchBtn.setOnClickListener(v -> searchData(binding.searchBar.getText().toString()));

        binding.exit.setOnClickListener(v -> finish());
    }

    @SuppressLint("NotifyDataSetChanged")
    private void searchData(String query){
        dataLayer.getFireStore().collection(SHOW_ALL)
                .whereEqualTo("name", query)
                .get()
                .addOnCompleteListener(task -> {
                    list.clear();
                   task.getResult().getDocuments().forEach(documentSnapshot -> {
                       ShowAllModel showAllModel = documentSnapshot.toObject(ShowAllModel.class);
                       list.add(showAllModel);
                       adapter.notifyDataSetChanged();
                   });
                });
    }
}