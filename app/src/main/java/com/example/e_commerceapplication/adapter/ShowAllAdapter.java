package com.example.e_commerceapplication.adapter;

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
import com.example.e_commerceapplication.models.product.ShowAllModel;
import com.example.e_commerceapplication.products.DetailedActivity;

import java.util.List;

public class ShowAllAdapter extends RecyclerView.Adapter<ShowAllAdapter.ViewHolder> {
    private final Context context;
    private final List<ShowAllModel> showAllModelList;

    public ShowAllAdapter(Context context, List<ShowAllModel> showAllModelList) {
        this.context = context;
        this.showAllModelList = showAllModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.show_all_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(showAllModelList.get(position).getImage_url()).into(holder.productImage);
        holder.productName.setText(showAllModelList.get(position).getName());
        holder.productPrice.setText(String.valueOf(showAllModelList.get(position).getPrice()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailedActivity.class);
            intent.putExtra("detailed", showAllModelList.get(position));
            context.startActivity(intent);
            ((Activity) context).finish();
        });
    }

    @Override
    public int getItemCount() {
        return showAllModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productPrice, productName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.item_image);
            productPrice = itemView.findViewById(R.id.product_showall_price);
            productName = itemView.findViewById(R.id.item_nam);
        }
    }
}
