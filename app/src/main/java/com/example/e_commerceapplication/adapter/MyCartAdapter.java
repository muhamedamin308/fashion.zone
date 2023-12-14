package com.example.e_commerceapplication.adapter;

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
import com.example.e_commerceapplication.models.product.MyCartModel;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    private Context context;
    private List<MyCartModel> myCartModelList;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_list_item, parent, false));
    }

    public MyCartAdapter(Context context, List<MyCartModel> myCartModelList) {
        this.context = context;
        this.myCartModelList = myCartModelList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(myCartModelList.get(position).getProductImage()).into(holder.productImage);
        holder.productName.setText(myCartModelList.get(position).getProductName());
        holder.productPrice.setText(String.valueOf(myCartModelList.get(position).getProductPrice()));
        holder.productDate.setText(myCartModelList.get(position).getProductDate());
        holder.totalQuantity.setText(String.valueOf(myCartModelList.get(position).getTotalQuantity()));
    }

    @Override
    public int getItemCount() {
        return myCartModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, productDate, totalQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.cc_holder_name);
            productPrice = itemView.findViewById(R.id.all_product_price);
            productDate = itemView.findViewById(R.id.credit_holder_name);
            totalQuantity = itemView.findViewById(R.id.product_counter);
        }
    }
}
