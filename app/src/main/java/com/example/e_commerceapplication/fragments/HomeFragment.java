package com.example.e_commerceapplication.fragments;

import static com.example.e_commerceapplication.general.Constants.ADMIN_MODE;
import static com.example.e_commerceapplication.general.Constants.USERS;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.adapter.CategoryAdapter;
import com.example.e_commerceapplication.adapter.NewProductsAdapter;
import com.example.e_commerceapplication.adapter.PopularProductsAdapter;
import com.example.e_commerceapplication.admin.activities.AdminModificationActivity;
import com.example.e_commerceapplication.databinding.FragmentHomeBinding;
import com.example.e_commerceapplication.general.Constants;
import com.example.e_commerceapplication.general.data.DataLayer;
import com.example.e_commerceapplication.models.product.CategoryModel;
import com.example.e_commerceapplication.models.product.NewProductsModel;
import com.example.e_commerceapplication.models.product.PopularProductsModel;
import com.example.e_commerceapplication.products.ShowAllActivity;
import com.example.e_commerceapplication.start.LoadingDialogBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    //Category
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModels;

    //New Product
    NewProductsAdapter newProductsAdapter;
    List<NewProductsModel> newProductsModelList;

    //Popular Products
    PopularProductsAdapter popularProductsAdapter;
    List<PopularProductsModel> popularProductsModelList;

    //Data
    DataLayer dataLayer;
    FragmentHomeBinding homeBinding;

    public HomeFragment() {
        // Required empty public constructor
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater);
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
        dataLayer = new DataLayer(USERS);

        //Initializing Variables

        Constants.loadingDialogBar = new LoadingDialogBar(getContext());
        homeBinding.homeLayout.setVisibility(View.GONE);



        //Image Slider Drawing
        List<SlideModel> slideModelList = new ArrayList<>();
        slideModelList.add(new SlideModel(R.drawable.shopping_bags1, "Discount", ScaleTypes.CENTER_INSIDE));
        slideModelList.add(new SlideModel(R.drawable.shopping_bags2, "Perfume", ScaleTypes.CENTER_INSIDE));
        slideModelList.add(new SlideModel(R.drawable.mobile_select_items, "Charts Mas", ScaleTypes.CENTER_INSIDE));
        homeBinding.imageSlider.setImageList(slideModelList);

        //Loading Dialog Processing
        Constants.loadingDialogBar.showDialog();

        //See All Events
        homeBinding.categorySeeAll.setOnClickListener(v -> startActivity(new Intent(getContext(), ShowAllActivity.class)));
        homeBinding.newProductsSeeAll.setOnClickListener(v -> startActivity(new Intent(getContext(), ShowAllActivity.class)));
        homeBinding.popularSeeAll.setOnClickListener(v -> startActivity(new Intent(getContext(), ShowAllActivity.class)));

        //Category
        homeBinding.recCategory.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryModels = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryModels);
        homeBinding.recCategory.setAdapter(categoryAdapter);
        dataLayer.categoryDatabase(categoryModels, categoryAdapter,this, homeBinding.homeLayout);


        //New Product
        homeBinding.newProductRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        newProductsModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(newProductsModelList, getContext());
        homeBinding.newProductRec.setAdapter(newProductsAdapter);
        dataLayer.newProductsDatabase(newProductsModelList, newProductsAdapter,this);


        //Popular Product
        homeBinding.popularRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularProductsModelList = new ArrayList<>();
        popularProductsAdapter = new PopularProductsAdapter(getContext(), popularProductsModelList);
        homeBinding.popularRec.setAdapter(popularProductsAdapter);
        dataLayer.popularProductsDatabase(popularProductsModelList, popularProductsAdapter, this);

        //Admin Mode
        if (ADMIN_MODE) {
           homeBinding.adminModification.setVisibility(View.VISIBLE);
           homeBinding.adminModification.setOnClickListener(v -> startActivity(new Intent(getContext(), AdminModificationActivity.class)));
        }
        return homeBinding.getRoot();
    }
}