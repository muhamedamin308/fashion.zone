package com.example.e_commerceapplication.address;

import static com.example.e_commerceapplication.general.Constants.USERS;
import static com.example.e_commerceapplication.general.Constants.isAddressSelected;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.adapter.AddressAdapter;
import com.example.e_commerceapplication.databinding.ActivityAddressBinding;
import com.example.e_commerceapplication.general.data.DataLayer;
import com.example.e_commerceapplication.models.address.AddressModel;
import com.example.e_commerceapplication.models.product.NewProductsModel;
import com.example.e_commerceapplication.models.product.PopularProductsModel;
import com.example.e_commerceapplication.models.product.Product;
import com.example.e_commerceapplication.models.product.ShowAllModel;
import com.example.e_commerceapplication.products.PaymentActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress {
    private List<AddressModel> list;
    String address = "";
    private DataLayer dataLayer;
    private Product mainProduct;
    ActivityAddressBinding binding;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataLayer = new DataLayer(USERS);

        Object object = getIntent().getSerializableExtra("item");
        int totalQuantity = getIntent().getIntExtra("quantity", 1);
        Serializable serializableExtra = getIntent().getSerializableExtra("listOfCart");
        double totalPaymentOfCartModel = getIntent().getDoubleExtra("finalPaymentAmount", 0.0);

        if (object instanceof NewProductsModel)
            mainProduct = (NewProductsModel) object;
        else if (object instanceof PopularProductsModel)
            mainProduct = (PopularProductsModel) object;
        else if (object instanceof ShowAllModel)
            mainProduct = (ShowAllModel) object;

        binding.addressRecycler.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        AddressAdapter adapter = new AddressAdapter(list, this);
        binding.addressRecycler.setAdapter(adapter);

        dataLayer.getAddressDatabase(list, adapter);

        binding.addAddressBtn.setOnClickListener(v -> startActivity(new Intent(this, AddAddressActivity.class)));

        binding.paymentBtn.setOnClickListener(v -> {
            if (isAddressSelected) {
                double amount = 0.0;
                double shipping = 0.0;
                double discount = 0.0;
                double[] payments = new double[]{amount, shipping, discount};
                Intent intent = new Intent(this, PaymentActivity.class);

                if (serializableExtra != null) {
                    payments[0] = totalPaymentOfCartModel;
                    payments[1] = payments[0] * 0.15;
                    payments[2] = payments[0] * 0.1;
                    intent.putExtra("product", serializableExtra);
                } else {
                    assert mainProduct != null;
                    payments = mainProduct.calculatePayments(amount, shipping, discount);
                    intent.putExtra("product", mainProduct);
                }
                intent.putExtra("amount", payments[0]);
                intent.putExtra("shipping", payments[1]);
                intent.putExtra("discount", payments[2]);
                intent.putExtra("quantity", totalQuantity);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Please Select Any Address", Toast.LENGTH_SHORT).show();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                AddressModel removerAddress = list.get(position);
                dataLayer.removeAddressDatabase(AddressActivity.this, list, removerAddress);
            }
        }).attachToRecyclerView(binding.addressRecycler);

        binding.exit.setOnClickListener(v -> finish());
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }
}