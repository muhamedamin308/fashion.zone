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
import com.example.e_commerceapplication.models.product.ShowAllModel;

import java.util.List;

public class AdminShowAllProductsAdapter extends RecyclerView.Adapter<AdminShowAllProductsAdapter.ViewHolder> {
    private final List<ShowAllModel> showAllModels;
    private final Context context;

    public AdminShowAllProductsAdapter(List<ShowAllModel> showAllModels, Context context) {
        this.showAllModels = showAllModels;
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
        Glide.with(context).load(showAllModels.get(position).getImage_url()).into(holder.productImage);
        holder.productName.setText(showAllModels.get(position).getName());
        holder.productPrice.setText(String.valueOf(showAllModels.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return showAllModels.size();
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
