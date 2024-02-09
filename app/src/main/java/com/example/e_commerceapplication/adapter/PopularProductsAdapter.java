package com.example.e_commerceapplication.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.classes.product.PopularProduct;
import com.example.e_commerceapplication.ui.products.DetailedActivity;

import java.util.List;

public class PopularProductsAdapter extends RecyclerView.Adapter<PopularProductsAdapter.ViewHolder> {
    private final Context context;
    private final List<PopularProduct> popularProductList;

    public PopularProductsAdapter(Context context, List<PopularProduct> popularProductList) {
        this.context = context;
        this.popularProductList = popularProductList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_items, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(popularProductList.get(position).getImage_url()).into(holder.allProductImage);
        holder.allProductName.setText(popularProductList.get(position).getName());
        holder.allProductPrice.setText(String.valueOf(popularProductList.get(position).getPrice()));
        holder.itemView.setOnClickListener(v ->{
            Intent intent = new Intent(context, DetailedActivity.class);
            intent.putExtra("detailed", popularProductList.get(position));
            context.startActivity(intent);
        });
        holder.allProductOrder.setText("#"+ popularProductList.get(position).getOrder());
    }

    @Override
    public int getItemCount() {
        return popularProductList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView allProductPrice, allProductName, allProductOrder;
        ImageView allProductImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            allProductImage = itemView.findViewById(R.id.all_product_img);
            allProductName = itemView.findViewById(R.id.all_product_name);
            allProductPrice = itemView.findViewById(R.id.all_product_price);
            allProductOrder = itemView.findViewById(R.id.all_product_order);
        }
    }
}
