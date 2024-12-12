package com.example.e_commerceapplication.ui.admin.activities;

import static com.example.e_commerceapplication.general.Constants.ADMIN_PATH;
import static com.example.e_commerceapplication.general.Constants.USER;
import static com.example.e_commerceapplication.general.Constants.USERS;
import static com.example.e_commerceapplication.general.Constants.USER_PAYMENTS;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceapplication.classes.product.Payment;
import com.example.e_commerceapplication.classes.users.User;
import com.example.e_commerceapplication.database.DataLayer;
import com.example.e_commerceapplication.databinding.ActivityUserHistoryBinding;
import com.example.e_commerceapplication.ui.admin.adapter.UserPaymentsHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserHistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    UserPaymentsHistoryAdapter adapter;
    List<Payment> payments;
    ActivityUserHistoryBinding binding;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.historyRec;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        payments = new ArrayList<>();

        DataLayer dataLayer = new DataLayer(USERS);

        dataLayer.getDatabaseReference().child(ADMIN_PATH).get().addOnCompleteListener(task -> task.getResult().getChildren().forEach(child -> {
            User user = child.getValue(User.class);
            assert user != null;
            Log.i("FashionLogging", user.getUsername());

            dataLayer.getFireStore().collection(USER_PAYMENTS).document(user.getUserID())
                    .collection(USER).get().addOnCompleteListener(pay -> pay.getResult()
                            .getDocuments().forEach(task1 -> {
                                Payment payment = task1.toObject(Payment.class);
                                payments.add(payment);
                                adapter.notifyDataSetChanged();
                                Log.i("FashionLogging", payments.get(0).getProductName());
                            }));
        }));

        adapter = new UserPaymentsHistoryAdapter(payments);
        recyclerView.setAdapter(adapter);

        binding.exit.setOnClickListener(v -> finish());
    }
}