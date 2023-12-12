package com.example.e_commerceapplication.start;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.adapter.SliderAdapter;
import com.example.e_commerceapplication.auth.RegistrationActivity;

public class OnBoardingActivity extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout layout;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    Button getStart;
    Animation animation;
    ImageView next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        viewPager = findViewById(R.id.slider);
        layout = findViewById(R.id.dots);
        getStart = findViewById(R.id.get_started_btn);
        next = findViewById(R.id.next_btn);
        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        getStart.setOnClickListener(v -> {
            startActivity(new Intent(OnBoardingActivity.this, RegistrationActivity.class));
            finish();
        });
    }

    private void addDots(int position) {
        dots = new TextView[3];
        layout.removeAllViews();
        int i = 0;
        while(i < dots.length) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            layout.addView(dots[i]);
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
                getStart.setVisibility(View.INVISIBLE);
                next.setVisibility(View.VISIBLE);
            }
            else {
                animation = AnimationUtils.loadAnimation(OnBoardingActivity.this, R.anim.slide_anim);
                getStart.setAnimation(animation);
                getStart.setVisibility(View.VISIBLE);
                next.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}