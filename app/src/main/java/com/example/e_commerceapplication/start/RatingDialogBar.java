package com.example.e_commerceapplication.start;

import static com.example.e_commerceapplication.general.Constants.ADMIN_PATH;
import static com.example.e_commerceapplication.general.Constants.USERS;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.general.data.DataLayer;
import com.example.e_commerceapplication.models.users.User;
import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.Objects;

public class RatingDialogBar {
    private final Context context;
    private Dialog dialog;
    private TextView rate;
    private Double rateValue = 0.0;
    private DataLayer dataLayer;

    public RatingDialogBar(Context context) {
        this.context = context;
    }

    public void showDialog(Activity activity) {
        dialog = new Dialog(context);
        dataLayer = new DataLayer(USERS);
        dialog.setContentView(R.layout.rating_dialog);
        rate = dialog.findViewById(R.id.rating_number);
        RatingBar bar = dialog.findViewById(R.id.progressDialog);
        AppCompatButton done = dialog.findViewById(R.id.submitRating);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.create();
        dialog.show();

        bar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            rateValue = (double) ratingBar.getRating();
            rate.setText(String.valueOf(rateValue));
        });

        done.setOnClickListener(v -> {
            HashMap<String, Object> map = new HashMap<>();
            dataLayer.getDatabaseReference().child(Objects.requireNonNull(dataLayer.getAuth().getUid()))
                    .get().addOnCompleteListener(task -> {
               if (task.isSuccessful()) {
                   for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                       User user = dataSnapshot.getValue(User.class);
                       assert user != null;
                       map.put("username", user.getUsername());
                       map.put("email", user.getEmail());
                       map.put("password", user.getPassword());
                   }
               }
            });
            map.put("paymentRate", rateValue);
            dataLayer.getDatabaseReference().child(ADMIN_PATH).child(Objects.requireNonNull(dataLayer.getAuth().getUid())).updateChildren(map);
            dataLayer.getDatabaseReference().child(Objects.requireNonNull(dataLayer.getAuth().getUid())).updateChildren(map)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()){
                                    dialog.dismiss();
                                    activity.startActivity(new Intent(activity, MainActivity.class));
                                    activity.finish();
                                } else {
                                    Toast.makeText(activity, "!!!", Toast.LENGTH_SHORT).show();
                                }
                            });
        });

    }
}
