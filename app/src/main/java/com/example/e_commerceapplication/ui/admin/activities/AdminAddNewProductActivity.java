package com.example.e_commerceapplication.ui.admin.activities;

import static com.example.e_commerceapplication.general.Constants.NEW_PRODUCTS;
import static com.example.e_commerceapplication.general.Constants.POPULAR_PRODUCTS;
import static com.example.e_commerceapplication.general.Constants.SHOW_ALL;
import static com.example.e_commerceapplication.general.Constants.USERS;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.classes.product.AllProducts;
import com.example.e_commerceapplication.classes.product.NewProduct;
import com.example.e_commerceapplication.classes.product.PopularProduct;
import com.example.e_commerceapplication.classes.product.Product;
import com.example.e_commerceapplication.database.DataLayer;
import com.example.e_commerceapplication.databinding.ActivityAdminAddNewProductBinding;
import com.example.e_commerceapplication.ui.activities.MainActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Objects;

public class AdminAddNewProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ActivityAdminAddNewProductBinding binding;
    String type, collection, productType;
    int stock;
    DataLayer dataLayer;
    Uri image;
    Product mainProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddNewProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataLayer = new DataLayer(USERS);

        Intent intent = getIntent();
        productType = Objects.requireNonNull(intent.getExtras()).getString("type");
        Object object = intent.getSerializableExtra("modification");

        if (object instanceof NewProduct)
            mainProduct = (NewProduct) object;
        if (object instanceof PopularProduct)
            mainProduct = (PopularProduct) object;
        if (object instanceof AllProducts)
            mainProduct = (AllProducts) object;


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.products_types, R.layout.spinner_item);
        adapter.setDropDownViewResource(com.razorpay.R.layout.support_simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(this);


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), res -> {
            if (res.getResultCode() == Activity.RESULT_OK) {
                Intent data = res.getData();
                assert data != null;
                image = data.getData();
                binding.newImage.setImageURI(image);
            }
        });

        binding.addNewProductImage.setOnClickListener(v -> {
            Intent photo = new Intent();
            photo.setAction(Intent.ACTION_GET_CONTENT);
            photo.setType("image/*");
            activityResultLauncher.launch(photo);
        });

        if (productType == null) {
            switch (mainProduct.productTypeConfirm()) {
                case NEW: {
                    binding.numberOfStock.setVisibility(View.VISIBLE);
                    binding.numberOfStock.setText(String.valueOf(mainProduct.getStock()));
                    collection = NEW_PRODUCTS;
                }
                break;
                case ALL: {
                    binding.numberOfStock.setVisibility(View.GONE);
                    collection = SHOW_ALL;
                }
                case POPULAR: {
                    binding.numberOfStock.setVisibility(View.GONE);
                    collection = POPULAR_PRODUCTS;
                }
                break;
            }
            binding.addNewProductName.setText(mainProduct.getName());
            binding.newProductDescription.setText(mainProduct.getDescription());
            binding.newProductPrice.setText(String.valueOf(mainProduct.getPrice()));
            binding.addNewProductRate.setText(mainProduct.getRating());
            Glide.with(this).load(mainProduct.getImage_url()).into(binding.newImage);
            binding.addNewProductImage.setClickable(false);
        } else {
            switch (Objects.requireNonNull(productType)) {
                case "NEW": {
                    mainProduct = new NewProduct();
                    collection = NEW_PRODUCTS;
                }
                break;
                case "POPULAR": {
                    mainProduct = new PopularProduct();
                    collection = POPULAR_PRODUCTS;
                }
                break;
                case "ALL": {
                    mainProduct = new AllProducts();
                    collection = SHOW_ALL;
                }
                break;
            }
        }


        binding.addNewProductBtn.setOnClickListener(v -> {
            if (productType == null) {
                String name = binding.addNewProductName.getText().toString();
                String rate = binding.addNewProductRate.getText().toString();
                String price = binding.newProductPrice.getText().toString();
                String description = binding.newProductDescription.getText().toString();
                if (!(binding.numberOfStock.getText().toString().isEmpty()))
                    stock = Integer.parseInt(binding.numberOfStock.getText().toString());
                else
                    stock = 0;
                uploadFirebase(name, rate, price, description, type, image);
            } else {
                if (image != null) {
                    String name = binding.addNewProductName.getText().toString();
                    String rate = binding.addNewProductRate.getText().toString();
                    String price = binding.newProductPrice.getText().toString();
                    String description = binding.newProductDescription.getText().toString();
                    if (!(binding.numberOfStock.getText().toString().isEmpty()))
                        stock = Integer.parseInt(binding.numberOfStock.getText().toString());
                    else
                        stock = 0;
                    uploadFirebase(name, rate, price, description, type, image);
                } else {
                    Toast.makeText(this, "Please Select Image For Your New Product", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.exit.setOnClickListener(v -> finish());
    }

    private void uploadFirebase(String name, String rate, String price, String description, String type, Uri image) {
        if (productType == null) {
            HashMap<String, Object> updatedProduct = new HashMap<>();
            updatedProduct.put("rating", rate);
            updatedProduct.put("price", Double.parseDouble(price));
            updatedProduct.put("description", description);
            updatedProduct.put("name", name);
            updatedProduct.put("type", type);
            if (stock != 0) {
                updatedProduct.put("stock", stock);
            }

            dataLayer.getFireStore().collection(collection).whereEqualTo("image_url", mainProduct.getImage_url())
                    .get()
                    .addOnCompleteListener(task -> {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        String id = documentSnapshot.getId();
                        dataLayer.getFireStore().collection(collection).document(id)
                                .update(updatedProduct).addOnCompleteListener(task2 -> {
                                    startActivity(new Intent(this, MainActivity.class));
                                    finish();
                                });
                    });
        } else {
            final StorageReference reference = FirebaseStorage.getInstance().getReference().child(System.currentTimeMillis() + "." + getFileExtension(image));

            reference.putFile(image).addOnCompleteListener(task ->
                    reference.getDownloadUrl().addOnSuccessListener(uri -> {
                        mainProduct.setRating(rate);
                        mainProduct.setPrice(Double.parseDouble(price));
                        mainProduct.setImage_url(uri.toString());
                        mainProduct.setDescription(description);
                        mainProduct.setName(name);
                        mainProduct.setType(type);
                        mainProduct.setStock(stock);
                        dataLayer.getFireStore().collection(collection).add(mainProduct).addOnCompleteListener(task2 -> finish());
                    }));
        }
    }


    private String getFileExtension(Uri file) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(contentResolver.getType(file));
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}