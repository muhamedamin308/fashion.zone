package com.example.e_commerceapplication.admin.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.models.product.NewProductsModel;

import java.util.List;

public class AdminNewProductsAdapter extends RecyclerView.Adapter<AdminNewProductsAdapter.ViewHolder> {
    private final List<NewProductsModel> newProductsModels;
    private final Context context;

    public AdminNewProductsAdapter(List<NewProductsModel> newProductsModels, Context context) {
        this.newProductsModels = newProductsModels;
        this.context = context;
    }

    @NonNull
    @Override
    public AdminNewProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdminNewProductsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdminNewProductsAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(newProductsModels.get(position).getImage_url()).into(holder.productImage);
        holder.productName.setText(newProductsModels.get(position).getName());
        holder.productPrice.setText(String.valueOf(newProductsModels.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return newProductsModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.cc_holder_name);
            productPrice = itemView.findViewById(R.id.all_product_price);
        }
    }
}
