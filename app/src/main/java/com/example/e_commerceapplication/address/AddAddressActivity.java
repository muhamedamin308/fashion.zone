package com.example.e_commerceapplication.address;

import static com.example.e_commerceapplication.general.Constants.USERS;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.e_commerceapplication.databinding.ActivityAddAddressBinding;
import com.example.e_commerceapplication.general.Constants;
import com.example.e_commerceapplication.general.data.DataLayer;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AddAddressActivity extends AppCompatActivity {

    FusedLocationProviderClient client;
    String addressLocation, cityLocation, countyLocation;
    private DataLayer dataLayer;
    ActivityAddAddressBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataLayer = new DataLayer(USERS);
        client = LocationServices.getFusedLocationProviderClient(AddAddressActivity.this);
        binding.getLocation.setOnClickListener(v -> {
            getLastLocation();
            binding.confirmLocation.setVisibility(View.VISIBLE);
        });

        binding.confirmLocation.setOnClickListener(v -> {
            if (!(addressLocation.isEmpty() || cityLocation.isEmpty() || countyLocation.isEmpty())) {
                HashMap<String, Object> addressMap = new HashMap<>();
                addressMap.put("address", addressLocation);
                addressMap.put("city", cityLocation);
                addressMap.put("county", countyLocation);
                dataLayer.addAddressDatabase(addressMap, AddAddressActivity.this);
            }
        });

        binding.exit.setOnClickListener(v -> finish());
    }

    @SuppressLint("SetTextI18n")
    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            client.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            Geocoder geocoder = new Geocoder(AddAddressActivity.this, Locale.getDefault());
                            List<Address> addresses;
                            try {
                                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            assert addresses != null;
                            addressLocation = addresses.get(0).getAddressLine(0);
                            binding.address.setText("Address: " + addressLocation);
                            cityLocation = addresses.get(0).getLocality();
                            binding.city.setText("city: " + cityLocation);
                            countyLocation = addresses.get(0).getCountryName();
                            binding.country.setText("Country: " + countyLocation);
                        }
                    });
        } else {
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(AddAddressActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                Constants.REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Constants.REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                getLastLocation();
            else
                Toast.makeText(AddAddressActivity.this, "Please Provide The Permissions", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}