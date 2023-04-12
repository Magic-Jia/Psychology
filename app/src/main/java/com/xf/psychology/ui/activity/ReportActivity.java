package com.xf.psychology.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.xf.psychology.R;


public class ReportActivity extends AppCompatActivity {
    private int awakeTime = 0;
    private int lightSleepTime = 0;
    private int deepSleepTime = 0;
    private int indeedsleeptime=0;
    private int onbedsleeptime=0;
    ImageView btn_img;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Intent intent = getIntent();
        awakeTime = intent.getIntExtra("awaketime", 0);
        lightSleepTime = intent.getIntExtra("lightsleeptime", 0);
        deepSleepTime = intent.getIntExtra("deepsleeptime", 0);
        // Calculate  deepSleepPercentage = (double) deepSleepTime / (awakeTime + lightSleepTime + deepSleepTime) * 100;
        // Display the deep sleep percentage in a TextView
        textView1 = findViewById(R.id.deep_sleep_ratio_textview);
        textView2 = findViewById(R.id.onbed_sleep_ratio_textview);
        textView3 = findViewById(R.id.indeed_sleep_ratio_textview);
        btn_img=findViewById(R.id.backBtn);
    }
    @Override
    protected void onResume() {
        super.onResume();
        double deepSleepPercentage = (deepSleepTime * 1.0) / (awakeTime + lightSleepTime + deepSleepTime);
        double lightSleepPercentage = (lightSleepTime * 1.0) / (awakeTime + lightSleepTime + deepSleepTime);
        double awakeSleepPercentage = (awakeTime * 1.0) / (awakeTime + lightSleepTime + deepSleepTime);
        onbedsleeptime = awakeTime + lightSleepTime + deepSleepTime;
        indeedsleeptime = lightSleepTime + deepSleepTime;
        textView1.setText("深度睡眠所占比率：" + String.format("%.2f", deepSleepPercentage) + '\n' + "浅度睡眠所占比率" + String.format("%.2f", lightSleepPercentage) + '\n' + "清醒所占比率" + String.format("%.2f", awakeSleepPercentage) + '\n');
        textView2.setText("在床时长" + String.format("%.2f", onbedsleeptime * 1.0));
        textView2.setText("有效睡眠时长" + String.format("%.2f", indeedsleeptime * 1.0));


        btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}