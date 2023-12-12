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
import com.example.e_commerceapplication.models.product.PopularProductsModel;

import java.util.List;

public class AdminPopularProductsAdapter extends RecyclerView.Adapter<AdminPopularProductsAdapter.ViewHolder> {
    private final List<PopularProductsModel> popularProductsModelList;
    private final Context context;

    public AdminPopularProductsAdapter(List<PopularProductsModel> popularProductsModelList, Context context) {
        this.popularProductsModelList = popularProductsModelList;
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
        Glide.with(context).load(popularProductsModelList.get(position).getImage_url()).into(holder.productImage);
        holder.productName.setText(popularProductsModelList.get(position).getName());
        holder.productPrice.setText(String.valueOf(popularProductsModelList.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return popularProductsModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.all_product_price);
        }
    }
}
