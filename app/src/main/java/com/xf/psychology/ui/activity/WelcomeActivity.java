package com.xf.psychology.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.xf.psychology.R;
import com.xf.psychology.adapter.CommonAdapter;
import com.xf.psychology.adapter.ViewHolder;
import com.xf.psychology.util.GlideUtil;
import com.xf.psychology.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    public ViewPager2 viewPager;
    public List<Integer> list = new ArrayList<>();
    private TextView textView;
    public CommonAdapter<Integer> adapter = new CommonAdapter<Integer>(R.layout.item_welcome, list) {
        @Override
        public void bind(ViewHolder holder, Integer integer, int position) {
            ImageView view = holder.getView(R.id.image);
            GlideUtil.load(view,integer);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        boolean firstIn =PreferenceUtil.getInstance().get("flag",true);
        Log.d("TAG", "onCreate: "+firstIn);
        if (!firstIn){
            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
            finish();
            return;
        }
        PreferenceUtil.getInstance().save("flag", false);
        StatusBarUtil.setTranslucentForImageView(this,0,viewPager);
        viewPager = findViewById(R.id.viewPager);
        textView = findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();
            }
        });
        list.add(R.drawable.wel1);
        list.add(R.drawable.wel2);
        list.add(R.drawable.wel3);
        viewPager.setAdapter(adapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d("TAG", "onPageSelected: "+position);
                if (position == 2) {
                    textView.setVisibility(View.VISIBLE);
                } else {
                    textView.setVisibility(View.GONE);
                }
            }


        });

    }
}