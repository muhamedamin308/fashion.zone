package com.example.e_commerceapplication.admin.activities;

import static com.example.e_commerceapplication.general.Constants.NEW_PRODUCTS;
import static com.example.e_commerceapplication.general.Constants.POPULAR_PRODUCTS;
import static com.example.e_commerceapplication.general.Constants.SHOW_ALL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.admin.adapter.AdminNewProductsAdapter;
import com.example.e_commerceapplication.admin.adapter.AdminPopularProductsAdapter;
import com.example.e_commerceapplication.admin.adapter.AdminShowAllProductsAdapter;
import com.example.e_commerceapplication.general.Constants;
import com.example.e_commerceapplication.general.data.DataLayer;
import com.example.e_commerceapplication.models.product.NewProductsModel;
import com.example.e_commerceapplication.models.product.PopularProductsModel;
import com.example.e_commerceapplication.models.product.Product;
import com.example.e_commerceapplication.models.product.ShowAllModel;

import java.util.ArrayList;
import java.util.List;

public class AdminModificationActivity extends AppCompatActivity {

    RecyclerView newRecycler, popularRecycler, allRecycler;
    AdminNewProductsAdapter newAdapter;
    AdminPopularProductsAdapter popularAdapter;
    AdminShowAllProductsAdapter allAdapter;
    List<NewProductsModel> newProducts;
    List<PopularProductsModel> popularProducts;
    List<ShowAllModel> allProducts;
    DataLayer dataLayer;
    TextView addNew, addAll, addPopular;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_modification);
        ImageView exit = findViewById(R.id.exit);
        newRecycler = findViewById(R.id.rec_new);
        popularRecycler = findViewById(R.id.rec_popular);
        allRecycler = findViewById(R.id.rec_show_all);
        addNew = findViewById(R.id.add_new_product);
        addPopular = findViewById(R.id.add_new_popular);
        addAll = findViewById(R.id.add_new_all);

        newRecycler.setLayoutManager(new LinearLayoutManager(this));
        popularRecycler.setLayoutManager(new LinearLayoutManager(this));
        allRecycler.setLayoutManager(new LinearLayoutManager(this));


        newProducts = new ArrayList<>();
        popularProducts = new ArrayList<>();
        allProducts = new ArrayList<>();

        newAdapter = new AdminNewProductsAdapter(newProducts, this);
        popularAdapter = new AdminPopularProductsAdapter(popularProducts, this);
        allAdapter = new AdminShowAllProductsAdapter(allProducts, this);


        newRecycler.setAdapter(newAdapter);
        popularRecycler.setAdapter(popularAdapter);
        allRecycler.setAdapter(allAdapter);

        dataLayer = new DataLayer(Constants.USERS);


        dataLayer.getPopularProductsAdminData(popularProducts, popularAdapter, this);
        dataLayer.getNewProductsAdminData(newProducts, newAdapter, this);
        dataLayer.getAllProductsAdminData(allProducts, allAdapter, this);



        addNew.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminAddNewProductActivity.class);
            intent.putExtra("type", newProducts.get(0).productTypeConfirm().toString());
            startActivity(intent);
            finish();
        });

        addPopular.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminAddNewProductActivity.class);
            intent.putExtra("type", popularProducts.get(0).productTypeConfirm().toString());
            startActivity(intent);
            finish();
        });

        addAll.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminAddNewProductActivity.class);
            intent.putExtra("type", allProducts.get(0).productTypeConfirm().toString());
            startActivity(intent);
            finish();
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                NewProductsModel remove = newProducts.get(position);
                dataLayer.removeNewProductAdminData(AdminModificationActivity.this, newProducts, remove, NEW_PRODUCTS);
            }
        }).attachToRecyclerView(newRecycler);



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                PopularProductsModel remove = popularProducts.get(position);
                dataLayer.removePopularProductAdminData(AdminModificationActivity.this, popularProducts, remove, POPULAR_PRODUCTS);
            }
        }).attachToRecyclerView(popularRecycler);



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                ShowAllModel remove = allProducts.get(position);
                dataLayer.removeAllProductAdminData(AdminModificationActivity.this, allProducts, remove, SHOW_ALL);
            }
        }).attachToRecyclerView(allRecycler);


        exit.setOnClickListener(v -> finish());
    }
}