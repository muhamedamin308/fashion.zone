package com.example.e_commerceapplication.start;

import static com.example.e_commerceapplication.general.Constants.ADMIN_MODE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.databinding.ActivityMainBinding;
import com.example.e_commerceapplication.fragments.CartFragment;
import com.example.e_commerceapplication.fragments.HomeFragment;
import com.example.e_commerceapplication.fragments.ProfileFragment;
import com.example.e_commerceapplication.products.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    Fragment homeFragment;
    Fragment profileFragment;
    Fragment cartFragment;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        cartFragment = new CartFragment();

        loadFragment(homeFragment);

        binding.searchProducts.setOnClickListener(v -> startActivity(new Intent(this, SearchActivity.class)));


        binding.pageTitle.setText(R.string.home);


        if (ADMIN_MODE) {
            binding.bottomNavAdmin.setVisibility(View.VISIBLE);
            binding.bottomNav.setVisibility(View.GONE);
            binding.bottomNavAdmin.setOnItemSelectedListener(item -> {
                if (item.getItemId() == R.id.home_page){
                    loadFragment(homeFragment);
                    binding.pageTitle.setText(R.string.home);
                }
                else {
                    loadFragment(profileFragment);
                    binding.pageTitle.setText(R.string.profile);
                }
                return true;
            });
        } else {
            binding.bottomNavAdmin.setVisibility(View.GONE);
            binding.bottomNav.setVisibility(View.VISIBLE);
            binding.bottomNav.setOnItemSelectedListener(item -> {
                if (item.getItemId() == R.id.home_page){
                    loadFragment(homeFragment);
                    binding.pageTitle.setText(R.string.home);
                }
                else if (item.getItemId() == R.id.cart_page){
                    loadFragment(cartFragment);
                    binding.pageTitle.setText(R.string.cart);
                }
                else {
                    loadFragment(profileFragment);
                    binding.pageTitle.setText(R.string.profile);
                }
                return true;
            });
        }

    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.home_container, fragment);
        transaction.commit();
    }
}