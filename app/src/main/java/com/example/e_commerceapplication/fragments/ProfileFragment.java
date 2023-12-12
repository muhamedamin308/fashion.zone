package com.example.e_commerceapplication.fragments;

import static com.example.e_commerceapplication.general.Constants.ADMIN_MODE;
import static com.example.e_commerceapplication.general.Constants.USERS;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.auth.RegistrationActivity;
import com.example.e_commerceapplication.general.TermsActivity;
import com.example.e_commerceapplication.general.UserHistoryActivity;
import com.example.e_commerceapplication.general.UsersFeedbackActivity;
import com.example.e_commerceapplication.general.data.DataLayer;

public class ProfileFragment extends Fragment {
    TextView email, username;
    AppCompatButton terms, logout, history, feedback;
    DataLayer dataLayer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        dataLayer = new DataLayer(USERS);

        username = view.findViewById(R.id.username);
        email = view.findViewById(R.id.uesrEmail);
        terms = view.findViewById(R.id.terms_conditions);
        history = view.findViewById(R.id.user_history);
        feedback = view.findViewById(R.id.user_feedback);
        logout = view.findViewById(R.id.logOut);

        dataLayer.getCustomerData(username, email);

        if (ADMIN_MODE) {
            history.setVisibility(View.GONE);
            feedback.setVisibility(View.VISIBLE);
            feedback.setOnClickListener(t -> startActivity(new Intent(getContext(), UsersFeedbackActivity.class)));
        } else {
            history.setVisibility(View.VISIBLE);
            feedback.setVisibility(View.GONE);
            history.setOnClickListener(v -> startActivity(new Intent(getContext(), UserHistoryActivity.class)));
        }

        logout.setOnClickListener(v -> {
            dataLayer.getAuth().signOut();
            startActivity(new Intent(getContext(), RegistrationActivity.class));
            requireActivity().finish();
        });

        terms.setOnClickListener(v -> startActivity(new Intent(getContext(), TermsActivity.class)));

        return view;
    }
}