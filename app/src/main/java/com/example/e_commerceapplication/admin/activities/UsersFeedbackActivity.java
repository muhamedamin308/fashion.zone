package com.example.e_commerceapplication.admin.activities;

import static com.example.e_commerceapplication.general.Constants.ADMIN_PATH;
import static com.example.e_commerceapplication.general.Constants.USERS;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.databinding.ActivityUsersFeedbackBinding;
import com.example.e_commerceapplication.general.data.DataLayer;
import com.example.e_commerceapplication.models.users.User;

import java.util.ArrayList;

public class UsersFeedbackActivity extends AppCompatActivity {
    ActivityUsersFeedbackBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpChart();

        binding.exit.setOnClickListener(v -> finish());
    }

    private void setUpChart() {
        Pie pie = AnyChart.pie();
        ArrayList<DataEntry> dataEntries = new ArrayList<>();
        DataLayer dataLayer = new DataLayer(USERS);
        dataLayer.getDatabaseReference().child(ADMIN_PATH).get()
            .addOnCompleteListener(task -> {
                task.getResult().getChildren().forEach(child -> {
                    User s = child.getValue(User.class);
                    assert s != null;
                    dataEntries.add(new ValueDataEntry(s.getUsername(), s.getPaymentRate() * 10));
                });
                pie.data(dataEntries);
                binding.userData.setChart(pie);
            });
    }
}