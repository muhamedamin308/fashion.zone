package com.example.e_commerceapplication.start;

import static com.example.e_commerceapplication.general.Constants.ADMIN_MODE;

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
import com.example.e_commerceapplication.fragments.CartFragment;
import com.example.e_commerceapplication.fragments.HomeFragment;
import com.example.e_commerceapplication.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    Fragment homeFragment;
    Fragment profileFragment;
    Fragment cartFragment;
    BottomNavigationView bottomNavigationAdmin;
    BottomNavigationView bottomNavigationUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        cartFragment = new CartFragment();
        loadFragment(homeFragment);
        TextView pageTitle = findViewById(R.id.page_title);
        pageTitle.setText(R.string.home);
        bottomNavigationUser = findViewById(R.id.bottomNav);
        bottomNavigationAdmin = findViewById(R.id.bottomNavAdmin);


        if (ADMIN_MODE) {
            bottomNavigationAdmin.setVisibility(View.VISIBLE);
            bottomNavigationUser.setVisibility(View.GONE);
            bottomNavigationAdmin.setOnItemSelectedListener(item -> {
                if (item.getItemId() == R.id.home_page){
                    loadFragment(homeFragment);
                    pageTitle.setText(R.string.home);
                }
                else {
                    loadFragment(profileFragment);
                    pageTitle.setText(R.string.profile);
                }
                return true;
            });
        } else {
            bottomNavigationAdmin.setVisibility(View.GONE);
            bottomNavigationUser.setVisibility(View.VISIBLE);
            bottomNavigationUser.setOnItemSelectedListener(item -> {
                if (item.getItemId() == R.id.home_page){
                    loadFragment(homeFragment);
                    pageTitle.setText(R.string.home);
                }
                else if (item.getItemId() == R.id.cart_page){
                    loadFragment(cartFragment);
                    pageTitle.setText(R.string.cart);
                }
                else {
                    loadFragment(profileFragment);
                    pageTitle.setText(R.string.profile);
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