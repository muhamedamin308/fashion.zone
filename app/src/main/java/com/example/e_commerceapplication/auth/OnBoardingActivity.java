package com.example.e_commerceapplication.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.adapter.SliderAdapter;
import com.example.e_commerceapplication.databinding.ActivityOnBoardingBinding;

public class OnBoardingActivity extends AppCompatActivity {
    SliderAdapter sliderAdapter;
    TextView[] dots;
    Animation animation;
    ActivityOnBoardingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addDots(0);
        binding.slider.addOnPageChangeListener(changeListener);
        sliderAdapter = new SliderAdapter(this);
        binding.slider.setAdapter(sliderAdapter);

        binding.getStartedBtn.setOnClickListener(v -> {
            startActivity(new Intent(OnBoardingActivity.this, RegistrationActivity.class));
            finish();
        });
    }

    private void addDots(int position) {
        dots = new TextView[3];
        binding.dots.removeAllViews();
        int i = 0;
        while(i < dots.length) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            binding.dots.addView(dots[i]);
            i++;
        }
        if (dots.length > 0)
            dots[position].setTextColor(getResources().getColor(R.color.green));
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            if (position == 0 || position == 1){
                binding.getStartedBtn.setVisibility(View.INVISIBLE);
                binding.nextBtn.setVisibility(View.VISIBLE);
            }
            else {
                animation = AnimationUtils.loadAnimation(OnBoardingActivity.this, R.anim.slide_anim);
                binding.getStartedBtn.setAnimation(animation);
                binding.getStartedBtn.setVisibility(View.VISIBLE);
                binding.nextBtn.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}