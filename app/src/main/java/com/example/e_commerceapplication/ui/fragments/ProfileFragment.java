package com.example.e_commerceapplication.ui.fragments;

import static com.example.e_commerceapplication.general.Constants.ADMIN_MODE;
import static com.example.e_commerceapplication.general.Constants.USERS;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.e_commerceapplication.ui.auth.RegistrationActivity;
import com.example.e_commerceapplication.databinding.FragmentProfileBinding;
import com.example.e_commerceapplication.ui.activities.TermsActivity;
import com.example.e_commerceapplication.ui.admin.activities.UserHistoryActivity;
import com.example.e_commerceapplication.ui.admin.activities.UsersFeedbackActivity;
import com.example.e_commerceapplication.database.DataLayer;

public class ProfileFragment extends Fragment {
    DataLayer dataLayer;
    FragmentProfileBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater);
//        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        dataLayer = new DataLayer(USERS);

        dataLayer.getCustomerData(binding.username, binding.uesrEmail);

        if (ADMIN_MODE) {
            binding.userHistory.setVisibility(View.VISIBLE);
            binding.userFeedback.setVisibility(View.VISIBLE);
            binding.userFeedback.setOnClickListener(t -> startActivity(new Intent(getContext(), UsersFeedbackActivity.class)));
            binding.userHistory.setOnClickListener(v -> startActivity(new Intent(getContext(), UserHistoryActivity.class)));
        } else {
            binding.userHistory.setVisibility(View.GONE);
            binding.userFeedback.setVisibility(View.GONE);
        }

        binding.logOut.setOnClickListener(v -> {
            dataLayer.getAuth().signOut();
            startActivity(new Intent(getContext(), RegistrationActivity.class));
            requireActivity().finish();
        });

        binding.termsConditions.setOnClickListener(v -> startActivity(new Intent(getContext(), TermsActivity.class)));

        return binding.getRoot();
    }
}