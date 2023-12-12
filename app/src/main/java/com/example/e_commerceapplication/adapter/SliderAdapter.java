package com.example.e_commerceapplication.adapter;

import static com.example.e_commerceapplication.general.Constants.descriptionArray;
import static com.example.e_commerceapplication.general.Constants.headingArray;
import static com.example.e_commerceapplication.general.Constants.imageArray;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager.widget.PagerAdapter;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.general.Constants;

import org.w3c.dom.Text;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return headingArray.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);
        ImageView imageView = view.findViewById(R.id.slider_img);
        TextView head = view.findViewById(R.id.heading);
        TextView description = view.findViewById(R.id.description);
        imageView.setImageResource(imageArray[position]);
        head.setText(headingArray[position]);
        description.setText(descriptionArray[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}