package com.xf.psychology.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.xf.psychology.R;


public class report extends AppCompatActivity {
    private int awakeTime = 0;
    private int lightSleepTime = 0;
    private int deepSleepTime = 0;
    TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Intent intent=getIntent();
        awakeTime=intent.getIntExtra("awaketime",0);
        lightSleepTime=intent.getIntExtra("lightsleeptime",0);
        deepSleepTime=intent.getIntExtra("deepsleeptime",0);
        // Calculate  deepSleepPercentage = (double) deepSleepTime / (awakeTime + lightSleepTime + deepSleepTime) * 100;
        // Display the deep sleep percentage in a TextView
        textView1 = findViewById(R.id.deep_sleep_ratio_textview);
        double deepSleepPercentage=(deepSleepTime*1.0)/(awakeTime+lightSleepTime+deepSleepTime);
        double lightSleepPercentage=(lightSleepTime*1.0)/(awakeTime+lightSleepTime+deepSleepTime);
        double awakeSleepPercentage=(awakeTime*1.0)/(awakeTime+lightSleepTime+deepSleepTime);
        textView1.setText("深度睡眠所占比率：" + String.format("%.2f", deepSleepPercentage)+'\n'+"浅度睡眠所占比率"+String.format("%.2f", lightSleepPercentage)+'\n'+"清醒所占比率"+String.format("%.2f", awakeSleepPercentage)+'\n');


    }
}