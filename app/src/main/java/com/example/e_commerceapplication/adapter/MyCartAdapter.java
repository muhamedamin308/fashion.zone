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
import com.example.e_commerceapplication.classes.product.MyCart;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    private final Context context;
    private final List<MyCart> myCartList;
    private int currentQuantity;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_list_item, parent, false));
    }

    public MyCartAdapter(Context context, List<MyCart> myCartList) {
        this.context = context;
        this.myCartList = myCartList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        currentQuantity = myCartList.get(position).getTotalQuantity();
        Glide.with(context).load(myCartList.get(position).getProductImage()).into(holder.productImage);
        holder.productName.setText(myCartList.get(position).getProductName());
        holder.productPrice.setText(String.valueOf(myCartList.get(position).getProductPrice()));
        holder.productDate.setText(myCartList.get(position).getProductDate());
        holder.totalQuantity.setText(String.valueOf(currentQuantity));
        holder.increase.setOnClickListener(v -> {
            if (currentQuantity < 10){
                currentQuantity++;
                holder.totalQuantity.setText(String.valueOf(currentQuantity));
            }
        });
        holder.decrease.setOnClickListener(v -> {
            if (currentQuantity > 1){
                currentQuantity--;
                holder.totalQuantity.setText(String.valueOf(currentQuantity));
            }
        });
    }

    @Override
    public int getItemCount() {
        return myCartList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage, increase, decrease;
        TextView productName, productPrice, productDate, totalQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.cc_holder_name);
            productPrice = itemView.findViewById(R.id.all_product_price);
            productDate = itemView.findViewById(R.id.credit_holder_name);
            totalQuantity = itemView.findViewById(R.id.product_counter);
            increase = itemView.findViewById(R.id.increase);
            decrease = itemView.findViewById(R.id.decrease);
        }
    }
}
