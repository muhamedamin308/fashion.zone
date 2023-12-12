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

    //Organizing
    RecyclerView categoryRecyclerView, newProductRecyclerView, popularProductsRecyclerView;
    LinearLayout linearLayout;
    TextView seeAllCategories, seeAllNewProducts, seeAllPopularProducts;
    DataLayer dataLayer;

    public HomeFragment() {
        // Required empty public constructor
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        dataLayer = new DataLayer(USERS);

        //Initializing Variables
        categoryRecyclerView = view.findViewById(R.id.rec_category);
        newProductRecyclerView = view.findViewById(R.id.new_product_rec);
        popularProductsRecyclerView = view.findViewById(R.id.popular_rec);
        Constants.loadingDialogBar = new LoadingDialogBar(getContext());
        linearLayout = view.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);
        seeAllCategories = view.findViewById(R.id.category_see_all);
        seeAllNewProducts = view.findViewById(R.id.newProducts_see_all);
        seeAllPopularProducts = view.findViewById(R.id.popular_see_all);


        //Image Slider Drawing
        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
        List<SlideModel> slideModelList = new ArrayList<>();
        slideModelList.add(new SlideModel(R.drawable.shopping_bags1, "Discount", ScaleTypes.CENTER_INSIDE));
        slideModelList.add(new SlideModel(R.drawable.shopping_bags2, "Perfume", ScaleTypes.CENTER_INSIDE));
        slideModelList.add(new SlideModel(R.drawable.mobile_select_items, "Charts Mas", ScaleTypes.CENTER_INSIDE));
        imageSlider.setImageList(slideModelList);

        //Loading Dialog Processing
        Constants.loadingDialogBar.showDialog();

        //See All Events
        seeAllCategories.setOnClickListener(v -> startActivity(new Intent(getContext(), ShowAllActivity.class)));
        seeAllNewProducts.setOnClickListener(v -> startActivity(new Intent(getContext(), ShowAllActivity.class)));
        seeAllPopularProducts.setOnClickListener(v -> startActivity(new Intent(getContext(), ShowAllActivity.class)));


        //Category
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryModels = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryModels);
        categoryRecyclerView.setAdapter(categoryAdapter);
        dataLayer.categoryDatabase(categoryModels, categoryAdapter,this, linearLayout);


        //New Product
        newProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        newProductsModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(newProductsModelList, getContext());
        newProductRecyclerView.setAdapter(newProductsAdapter);
        dataLayer.newProductsDatabase(newProductsModelList, newProductsAdapter,this);


        //Popular Product
        popularProductsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularProductsModelList = new ArrayList<>();
        popularProductsAdapter = new PopularProductsAdapter(getContext(), popularProductsModelList);
        popularProductsRecyclerView.setAdapter(popularProductsAdapter);
        dataLayer.popularProductsDatabase(popularProductsModelList, popularProductsAdapter, this);


        //Admin Mode
        if (ADMIN_MODE) {
           FloatingActionButton adminModification = view.findViewById(R.id.adminModification);
           adminModification.setVisibility(View.VISIBLE);
           adminModification.setOnClickListener(v -> {
               startActivity(new Intent(getContext(), AdminModificationActivity.class));
           });
        }
        return view;
    }
}