package com.example.e_commerceapplication.ui.admin.adapter;

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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.classes.product.PopularProduct;
import com.example.e_commerceapplication.ui.admin.activities.AdminAddNewProductActivity;

import java.util.List;

public class AdminPopularProductsAdapter extends RecyclerView.Adapter<AdminPopularProductsAdapter.ViewHolder> {
    private final List<PopularProduct> popularProductList;
    private final Context context;

    public AdminPopularProductsAdapter(List<PopularProduct> popularProductList, Context context) {
        this.popularProductList = popularProductList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdminPopularProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdminPopularProductsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdminPopularProductsAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(popularProductList.get(position).getImage_url()).into(holder.productImage);
        holder.productName.setText(popularProductList.get(position).getName());
        holder.productPrice.setText(String.valueOf(popularProductList.get(position).getPrice()));
        holder.item.setOnClickListener(v -> {
            Intent intent = new Intent(context, AdminAddNewProductActivity.class);
            intent.putExtra("modification", popularProductList.get(position));
            context.startActivity(intent);
            ((Activity) context).finish();
        });
    }

    @Override
    public int getItemCount() {
        return popularProductList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage;
        ConstraintLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.cc_holder_name);
            productPrice = itemView.findViewById(R.id.all_product_price);
            item = itemView.findViewById(R.id.product_update);
        }
    }
}
