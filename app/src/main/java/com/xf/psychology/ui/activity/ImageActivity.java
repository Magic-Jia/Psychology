package com.xf.psychology.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.xf.psychology.R;
import com.xf.psychology.util.GlideUtil;

public class ImageActivity extends AppCompatActivity {
    private View backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        SubsamplingScaleImageView iv = findViewById(R.id.iv);
        int res = getIntent().getIntExtra("res", -1);
        GlideUtil.loadLargeImage(this,res,iv);

        backbtn = findViewById(R.id.backBtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}