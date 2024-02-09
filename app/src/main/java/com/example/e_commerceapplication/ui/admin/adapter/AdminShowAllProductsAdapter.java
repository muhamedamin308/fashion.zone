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
import com.example.e_commerceapplication.classes.product.AllProducts;
import com.example.e_commerceapplication.ui.admin.activities.AdminAddNewProductActivity;

import java.util.List;

public class AdminShowAllProductsAdapter extends RecyclerView.Adapter<AdminShowAllProductsAdapter.ViewHolder> {
    private final List<AllProducts> allProducts;
    private final Context context;

    public AdminShowAllProductsAdapter(List<AllProducts> allProducts, Context context) {
        this.allProducts = allProducts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(allProducts.get(position).getImage_url()).into(holder.productImage);
        holder.productName.setText(allProducts.get(position).getName());
        holder.productPrice.setText(String.valueOf(allProducts.get(position).getPrice()));
        holder.item.setOnClickListener(v -> {
            Intent intent = new Intent(context, AdminAddNewProductActivity.class);
            intent.putExtra("modification", allProducts.get(position));
            context.startActivity(intent);
            ((Activity) context).finish();
        });
    }

    @Override
    public int getItemCount() {
        return allProducts.size();
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
