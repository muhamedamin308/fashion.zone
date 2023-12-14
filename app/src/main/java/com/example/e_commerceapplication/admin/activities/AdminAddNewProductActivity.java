package com.example.e_commerceapplication.admin.activities;

import static com.example.e_commerceapplication.general.Constants.NEW_PRODUCTS;
import static com.example.e_commerceapplication.general.Constants.POPULAR_PRODUCTS;
import static com.example.e_commerceapplication.general.Constants.SHOW_ALL;
import static com.example.e_commerceapplication.general.Constants.USERS;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.databinding.ActivityAdminAddNewProductBinding;
import com.example.e_commerceapplication.general.data.DataLayer;
import com.example.e_commerceapplication.general.data.ProductType;
import com.example.e_commerceapplication.models.product.NewProductsModel;
import com.example.e_commerceapplication.models.product.PopularProductsModel;
import com.example.e_commerceapplication.models.product.Product;
import com.example.e_commerceapplication.models.product.ShowAllModel;
import com.example.e_commerceapplication.start.MainActivity;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class AdminAddNewProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ActivityAdminAddNewProductBinding binding;
    String type, collection;
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
        String productType = Objects.requireNonNull(intent.getExtras()).getString("type");


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.products_types, R.layout.spinner_item);
        adapter.setDropDownViewResource(com.razorpay.R.layout.support_simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(this);


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                res -> {
                    if (res.getResultCode() == Activity.RESULT_OK){
                        Intent data = res.getData();
                        assert data != null;
                        image = data.getData();
                        binding.newImage.setImageURI(image);
                    }
                }
        );

        binding.addNewProductImage.setOnClickListener(v -> {
            Intent photo = new Intent();
            photo.setAction(Intent.ACTION_GET_CONTENT);
            photo.setType("image/*");
            activityResultLauncher.launch(photo);
        });


        switch (Objects.requireNonNull(productType)) {
            case "NEW": {
                mainProduct = new NewProductsModel();
                collection = NEW_PRODUCTS;
            }
            break;
            case "POPULAR": {
                mainProduct = new PopularProductsModel();
                collection = POPULAR_PRODUCTS;
            }
            break;
            case "ALL": {
                mainProduct = new ShowAllModel();
                collection = SHOW_ALL;
            }
            break;
        }


        binding.addNewProductBtn.setOnClickListener(v -> {
            if (image != null) {
                String name = binding.addNewProductName.getText().toString();
                String rate = binding.addNewProductRate.getText().toString();
                String price = binding.newProductPrice.getText().toString();
                String description = binding.newProductDescription.getText().toString();
                uploadFirebase(name, rate, price, description, type, image);
            } else {
                Toast.makeText(this, "Please Select Image For Your New Product", Toast.LENGTH_SHORT).show();
            }
        });

        binding.exit.setOnClickListener(v -> finish());
    }

    private void uploadFirebase(String name, String rate, String price, String description, String type, Uri image) {

        final StorageReference reference = FirebaseStorage.getInstance().getReference().child(System.currentTimeMillis() + "." + getFileExtension(image));

        reference.putFile(image).addOnCompleteListener(task -> {
           reference.getDownloadUrl().addOnSuccessListener(uri -> {
               mainProduct.setRating(rate);
               mainProduct.setPrice(Double.parseDouble(price));
               mainProduct.setImage_url(uri.toString());
               mainProduct.setDescription(description);
               mainProduct.setName(name);
               mainProduct.setType(type);
               dataLayer.getFireStore().collection(collection)
                   .add(mainProduct)
                   .addOnCompleteListener(task2 -> {
                       finish();
                   });
           });
        });
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