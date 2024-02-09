package com.example.e_commerceapplication.adapter;

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
import com.example.e_commerceapplication.classes.product.NewProduct;
import com.example.e_commerceapplication.ui.products.DetailedActivity;

import java.util.List;

public class NewProductsAdapter extends RecyclerView.Adapter<NewProductsAdapter.ViewHolder> {

    private final List<NewProduct> list;
    private final Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_products, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImage_url()).into(holder.newProductImage);
        holder.newProductName.setText(list.get(position).getName());
        holder.newProductPrice.setText(String.valueOf(list.get(position).getPrice()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailedActivity.class);
            intent.putExtra("detailed", list.get(position));
            context.startActivity(intent);
        });
    }

    public NewProductsAdapter(List<NewProduct> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView newProductImage;
        TextView newProductName, newProductPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newProductImage = itemView.findViewById(R.id.new_img);
            newProductName = itemView.findViewById(R.id.new_product_name);
            newProductPrice = itemView.findViewById(R.id.new_product_price);
        }
    }
}
