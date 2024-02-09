package com.example.e_commerceapplication.ui.admin.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.classes.product.Payment;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class UserPaymentsHistoryAdapter extends RecyclerView.Adapter<UserPaymentsHistoryAdapter.ViewHolder> {
    private final List<Payment> payments;

    public UserPaymentsHistoryAdapter(List<Payment> payments) {
        this.payments = payments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.creditHolderName.setText(payments.get(position).getCreditCardHolderName());
        holder.name.setText(payments.get(position).getProductName());
        holder.rate.setText(payments.get(position).getProductRate());
        NumberFormat formatter = new DecimalFormat("#0.00");
        holder.price.setText(String.format("$%s", formatter.format(payments.get(position).getProductTotalPrice())));
        holder.quantity.setText("Quantity: " + payments.get(position).getProductQuantity());
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView creditHolderName;
        TextView name;
        TextView price;
        TextView quantity;
        TextView rate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            creditHolderName = itemView.findViewById(R.id.userName);
            name = itemView.findViewById(R.id.tv_product_name);
            rate = itemView.findViewById(R.id.tv_product_rate);
            price = itemView.findViewById(R.id.tv_product_price);
            quantity = itemView.findViewById(R.id.tv_product_quantity);
        }
    }
}
