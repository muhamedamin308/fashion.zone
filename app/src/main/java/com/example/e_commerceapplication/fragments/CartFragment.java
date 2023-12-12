package com.example.e_commerceapplication.fragments;

import static com.example.e_commerceapplication.general.Constants.ADD_TO_CART;
import static com.example.e_commerceapplication.general.Constants.USER;
import static com.example.e_commerceapplication.general.Constants.USERS;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.adapter.MyCartAdapter;
import com.example.e_commerceapplication.address.AddressActivity;
import com.example.e_commerceapplication.general.data.DataLayer;
import com.example.e_commerceapplication.models.product.MyCartModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    RecyclerView recyclerView;
    List<MyCartModel> list;
    MyCartAdapter adapter;
    TextView totalPrice;
    Button finalBuy;
    DataLayer dataLayer;
    View layout, empty;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        dataLayer = new DataLayer(USERS);

        totalPrice = view.findViewById(R.id.my_cart_total_price);
        finalBuy = view.findViewById(R.id.final_buy);
        recyclerView = view.findViewById(R.id.my_cart_rec);
        layout = view.findViewById(R.id.cart_layout);
        empty = view.findViewById(R.id.empty_cart);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        adapter = new MyCartAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);


        dataLayer.cartDatabase(ADD_TO_CART, USER, adapter, list, totalPrice, layout, empty);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                MyCartModel removeCart = list.get(position);
                dataLayer.removeCartDatabase(ADD_TO_CART,
                        USER,
                        list,
                        totalPrice,
                        removeCart,
                        CartFragment.this);
            }
        }).attachToRecyclerView(recyclerView);

        finalBuy.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddressActivity.class);
            double finalPaymentAmount = 0.0;
            for (MyCartModel model : list) finalPaymentAmount += model.getTotalPrice();
            intent.putExtra("finalPaymentAmount", finalPaymentAmount);
            intent.putExtra("listOfCart", (Serializable) list);
            intent.putExtra("quantity", list.size());
            startActivity(intent);
        });
        return view;
    }
}