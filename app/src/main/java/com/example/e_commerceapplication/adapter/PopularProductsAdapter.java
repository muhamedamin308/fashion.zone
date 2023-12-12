package com.example.e_commerceapplication.adapter;

import android.annotation.SuppressLint;
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
import com.example.e_commerceapplication.models.product.PopularProductsModel;
import com.example.e_commerceapplication.products.DetailedActivity;

import java.util.List;

public class PopularProductsAdapter extends RecyclerView.Adapter<PopularProductsAdapter.ViewHolder> {
    private final Context context;
    private final List<PopularProductsModel> popularProductsModelList;

    public PopularProductsAdapter(Context context, List<PopularProductsModel> popularProductsModelList) {
        this.context = context;
        this.popularProductsModelList = popularProductsModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_items, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(popularProductsModelList.get(position).getImage_url()).into(holder.allProductImage);
        holder.allProductName.setText(popularProductsModelList.get(position).getName());
        holder.allProductPrice.setText(String.valueOf(popularProductsModelList.get(position).getPrice()));
        holder.itemView.setOnClickListener(v ->{
            Intent intent = new Intent(context, DetailedActivity.class);
            intent.putExtra("detailed", popularProductsModelList.get(position));
            context.startActivity(intent);
        });
        holder.allProductOrder.setText("#"+popularProductsModelList.get(position).getOrder());
    }

    @Override
    public int getItemCount() {
        return popularProductsModelList.size();
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
