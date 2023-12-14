package com.example.e_commerceapplication.products;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.address.AddressActivity;
import com.example.e_commerceapplication.general.data.DataLayer;
import com.example.e_commerceapplication.models.product.NewProductsModel;
import com.example.e_commerceapplication.models.product.PopularProductsModel;
import com.example.e_commerceapplication.models.product.Product;
import com.example.e_commerceapplication.models.product.ShowAllModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {
    ImageView detailedImage, addOneItem, removeOneItem, exit;
    TextView detailedName, detailedDescription, detailedRate, detailedPrice, quantity;
    Button addToCard, buyNow;
    RatingBar ratingBar;
    int totalQuantity = 1;
    double totalPrice = 0;
    String image;
    Product mainProduct;
    Animation animation;
    DataLayer dataLayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        dataLayer = new DataLayer(USERS);

        isAddressSelected = false;

        final Object object = getIntent().getSerializableExtra("detailed");

        if (object instanceof NewProductsModel)
            mainProduct = (NewProductsModel) object;
        else if (object instanceof PopularProductsModel)
            mainProduct = (PopularProductsModel) object;
        else if (object instanceof ShowAllModel)
            mainProduct = (ShowAllModel) object;
        assert mainProduct != null;

        detailedImage = findViewById(R.id.image_details);
        detailedName = findViewById(R.id.details_product_name);
        detailedDescription = findViewById(R.id.product_detail_description);
        detailedRate = findViewById(R.id.product_detail_rate);
        detailedPrice = findViewById(R.id.product_detail_price);
        ratingBar = findViewById(R.id.star_rate);

        quantity = findViewById(R.id.quantity);
        addOneItem = findViewById(R.id.image_add_one_item);
        removeOneItem = findViewById(R.id.image_remove_one_item);

        addToCard = findViewById(R.id.add_to_card);
        buyNow = findViewById(R.id.buy_now);

        initializeView(mainProduct);

        if (ADMIN_MODE){
            quantity.setVisibility(View.GONE);
            addOneItem.setVisibility(View.GONE);
            removeOneItem.setVisibility(View.GONE);
            addToCard.setVisibility(View.GONE);
            buyNow.setVisibility(View.GONE);
        }
        addToCard.setOnClickListener(v -> addToCard());

        addOneItem.setOnClickListener(v -> {
            if (totalQuantity < 10) {
                totalQuantity++;
                quantity.setText(String.valueOf(totalQuantity));
                totalPrice = mainProduct.getPrice() * totalQuantity;
            }
        });

        removeOneItem.setOnClickListener(v -> {
            if (totalQuantity > 1) {
                totalQuantity--;
                quantity.setText(String.valueOf(totalQuantity));
            }
        });

        exit = findViewById(R.id.exit);
        exit.setOnClickListener(v -> finish());
        buyNow.setOnClickListener(v -> {
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
        cartMap.put("productName", detailedName.getText().toString());
        cartMap.put("productPrice", Double.valueOf(detailedPrice.getText().toString()));
        cartMap.put("productTime", saveCurrentTime);
        cartMap.put("productDate", saveCurrentDate);
        cartMap.put("totalQuantity", Double.parseDouble(quantity.getText().toString()));
        cartMap.put("totalPrice", totalPrice);
        cartMap.put("productRate", detailedRate.getText().toString());

        dataLayer.customerDataActivation(cartMap, DetailedActivity.this, ADD_TO_CART);
    }
    @SuppressLint("SetTextI18n")
    private void initializeView(Product product) {
        TextView newItem = findViewById(R.id.new_item_sign);

        switch (product.productTypeConfirm()){
            case NEW: newItem.setText(R.string.new_item);
            break;
            case POPULAR: newItem.setText("#"+((PopularProductsModel) product).getOrder()+" \uD83D\uDD25");
            break;
            case ALL: newItem.setText(categories.get(((ShowAllModel) product).getType()));
        }

        animation = AnimationUtils.loadAnimation(DetailedActivity.this, R.anim.slide_anim);
        Glide.with(getApplicationContext()).load(product.getImage_url()).into(detailedImage);
        image = product.getImage_url();
        detailedImage.setAnimation(animation);
        detailedName.setText(product.getName());
        detailedDescription.setText(product.getDescription());
        detailedPrice.setText(String.valueOf(product.getPrice()));
        detailedRate.setText(product.getRating());
        ratingBar.setRating(Float.parseFloat(detailedRate.getText().toString()));
        totalPrice = product.getPrice() * totalQuantity;
    }
}