package com.example.e_commerceapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.general.Constants;
import com.example.e_commerceapplication.classes.address.Address;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private final List<Address> list;
    final SelectedAddress selectedAddress;
    private RadioButton selectedRadioButton;


    public AddressAdapter(List<Address> list, SelectedAddress selectedAddress) {
        this.list = list;
        this.selectedAddress = selectedAddress;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_item,
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.addressCity.setText(list.get(position).getCity());
        holder.addressSelected.setOnClickListener(v -> {
            for (Address address: list)
                address.setSelected(false);
            list.get(position).setSelected(true);
            if (selectedRadioButton != null)
                selectedRadioButton.setChecked(false);
            selectedRadioButton = (RadioButton) v;
            selectedRadioButton.setChecked(true);
            selectedAddress.setAddress(list.get(position).getCity());
            Constants.isAddressSelected = true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView addressCity;
        final RadioButton addressSelected;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            addressCity = itemView.findViewById(R.id.address_city);
            addressSelected = itemView.findViewById(R.id.select_address);
        }
    }

    public interface SelectedAddress {
        void setAddress(String address);
    }
}
