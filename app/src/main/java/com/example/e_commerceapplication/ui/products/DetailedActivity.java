package com.example.e_commerceapplication.ui.products;

import static com.example.e_commerceapplication.general.Constants.ADD_TO_CART;
import static com.example.e_commerceapplication.general.Constants.ADMIN_MODE;
import static com.example.e_commerceapplication.general.Constants.USERS;
import static com.example.e_commerceapplication.general.Constants.categories;
import static com.example.e_commerceapplication.general.Constants.isAddressSelected;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.classes.product.AllProducts;
import com.example.e_commerceapplication.classes.product.NewProduct;
import com.example.e_commerceapplication.classes.product.PopularProduct;
import com.example.e_commerceapplication.classes.product.Product;
import com.example.e_commerceapplication.database.DataLayer;
import com.example.e_commerceapplication.databinding.ActivityDetailedBinding;
import com.example.e_commerceapplication.general.enums.ProductType;
import com.example.e_commerceapplication.ui.address.AddressActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {
    int totalQuantity = 1, totalInStock = 0;
    double totalPrice = 0;
    String image;
    Product mainProduct;
    Animation animation;
    DataLayer dataLayer;
    ActivityDetailedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataLayer = new DataLayer(USERS);

        isAddressSelected = false;

        final Object object = getIntent().getSerializableExtra("detailed");

        if (object instanceof NewProduct) mainProduct = (NewProduct) object;
        else if (object instanceof PopularProduct) mainProduct = (PopularProduct) object;
        else if (object instanceof AllProducts) mainProduct = (AllProducts) object;
        assert mainProduct != null;

        initializeView(mainProduct);

        if (ADMIN_MODE) {
            binding.quantity.setVisibility(View.GONE);
            binding.imageAddOneItem.setVisibility(View.GONE);
            binding.imageRemoveOneItem.setVisibility(View.GONE);
            binding.addToCard.setVisibility(View.GONE);
            binding.buyNow.setVisibility(View.GONE);
        }
        binding.addToCard.setOnClickListener(v -> addToCard());

        binding.imageAddOneItem.setOnClickListener(v -> {
            if (mainProduct.productTypeConfirm().equals(ProductType.NEW)) {
                if (totalQuantity < 10 && totalQuantity < totalInStock) {
                    totalQuantity++;
                    binding.quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = mainProduct.getPrice() * totalQuantity;
                }
            } else {
                if (totalQuantity < 10) {
                    totalQuantity++;
                    binding.quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = mainProduct.getPrice() * totalQuantity;
                }
            }
        });

        binding.imageRemoveOneItem.setOnClickListener(v -> {
            if (totalQuantity > 1) {
                totalQuantity--;
                binding.quantity.setText(String.valueOf(totalQuantity));
            }
        });

        binding.exit.setOnClickListener(v -> finish());
        binding.buyNow.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddressActivity.class);
            intent.putExtra("item", mainProduct);
            intent.putExtra("quantity", totalQuantity);
            startActivity(intent);
        });
    }

    @SuppressLint("SimpleDateFormat")
    private void addToCard() {
        String saveCurrentTime, saveCurrentDate;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productImage", image);
        cartMap.put("productName", binding.detailsProductName.getText().toString());
        cartMap.put("productPrice", Double.valueOf(binding.productDetailPrice.getText().toString()));
        cartMap.put("productTime", saveCurrentTime);
        cartMap.put("productDate", saveCurrentDate);
        cartMap.put("totalQuantity", Double.parseDouble(binding.quantity.getText().toString()));
        cartMap.put("totalPrice", totalPrice);
        cartMap.put("productRate", binding.productDetailRate.getText().toString());

        dataLayer.customerDataActivation(cartMap, DetailedActivity.this, ADD_TO_CART);
    }

    @SuppressLint("SetTextI18n")
    private void initializeView(Product product) {

        switch (product.productTypeConfirm()) {
            case NEW: {
                binding.newItemSign.setText(R.string.new_item);
                totalInStock = product.getStock();
                binding.stockCounter.setText("Stock: " + totalInStock);
                binding.addToCard.setVisibility(View.GONE);
            }
            break;
            case POPULAR: {
                binding.newItemSign.setText("#" + ((PopularProduct) product).getOrder() + " \uD83D\uDD25");
                binding.stockCounter.setVisibility(View.GONE);
            }
            break;
            case ALL: {
                binding.newItemSign.setText(categories.get(product.getType()));
                binding.stockCounter.setVisibility(View.GONE);
            }
        }

        animation = AnimationUtils.loadAnimation(DetailedActivity.this, R.anim.slide_anim);
        Glide.with(getApplicationContext()).load(product.getImage_url()).into(binding.imageDetails);
        image = product.getImage_url();
        binding.imageDetails.setAnimation(animation);
        binding.detailsProductName.setText(product.getName());
        binding.productDetailDescription.setText(product.getDescription());
        binding.productDetailPrice.setText(String.valueOf(product.getPrice()));
        binding.productDetailRate.setText(product.getRating());
        binding.starRate.setRating(Float.parseFloat(binding.productDetailRate.getText().toString()));
        totalPrice = product.getPrice() * totalQuantity;
    }
}