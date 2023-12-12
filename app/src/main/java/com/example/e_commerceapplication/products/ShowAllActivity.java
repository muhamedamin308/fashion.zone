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
import com.example.e_commerceapplication.general.data.DataLayer;
import com.example.e_commerceapplication.models.product.ShowAllModel;

import java.util.ArrayList;
import java.util.List;

public class ShowAllActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ShowAllModel> showAllModelList;
    ShowAllAdapter showAllAdapter;
    TextView categoryName;
    DataLayer dataLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);
        dataLayer = new DataLayer(USERS);
        recyclerView = findViewById(R.id.show_all_rec);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        showAllModelList = new ArrayList<>();
        showAllAdapter = new ShowAllAdapter(this, showAllModelList);
        recyclerView.setAdapter(showAllAdapter);
        ImageView exit = findViewById(R.id.exit);
        categoryName = findViewById(R.id.category);

        String type = getIntent().getStringExtra("type");


        if (type == null) {
           dataLayer.showAllDatabase(false, categoryName, null, showAllModelList, showAllAdapter);
        }

        if (type != null) {
            dataLayer.showAllDatabase(true, categoryName, type, showAllModelList, showAllAdapter);
        }

        exit.setOnClickListener(v -> finish());
    }
}