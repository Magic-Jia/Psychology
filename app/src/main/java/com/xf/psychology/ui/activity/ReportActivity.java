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
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.xf.psychology.R;

import java.util.ArrayList;
import java.util.List;


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
    TextView textView4;
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
        textView4 = findViewById(R.id.sleep_advice_textview);
        btn_img=findViewById(R.id.backBtn);
        // 获取 PieChart 控件

    }
    @Override
    protected void onResume() {
        super.onResume();
        float deepSleepPercentage = (float)(deepSleepTime * 1.0) / (awakeTime + lightSleepTime + deepSleepTime);
        float lightSleepPercentage = (float)(lightSleepTime * 1.0) / (awakeTime + lightSleepTime + deepSleepTime);
        float awakeSleepPercentage = (float)(awakeTime * 1.0) / (awakeTime + lightSleepTime + deepSleepTime);
        onbedsleeptime = awakeTime + lightSleepTime + deepSleepTime;
        indeedsleeptime = lightSleepTime + deepSleepTime;
        textView1.setText("深度睡眠所占比率：" + String.format("%.2f", deepSleepPercentage) + '\n' + "浅度睡眠所占比率" + String.format("%.2f", lightSleepPercentage) + '\n' + "清醒所占比率" + String.format("%.2f", awakeSleepPercentage) + '\n');
        textView2.setText("在床时长" + String.format("%.2f", onbedsleeptime * 1.0/1000));
        textView3.setText("有效睡眠时长" + String.format("%.2f", indeedsleeptime * 1.0/1000));
        //给出建议，展示在textview4 awake/1000单位是s
        String advice;
        if (indeedsleeptime/(1000*60)< 360) { // 有效睡眠时长不足 6 小时
            advice = "您的睡眠时长不足，请尝试增加睡眠时间。长期睡眠不足可能会导致身体免疫力下降，记忆力减退，注意力不集中等问题。建议您制定一个规律的睡眠计划，保证充足的睡眠时间，让身体充分休息，保持良好的身体状态。";
        } else if (deepSleepPercentage < 0.15) { // 深度睡眠比率低于 15%
            advice = "您的深度睡眠比率偏低，请尝试进行一些有助于提高深度睡眠的活动。例如进行适量的运动，听放松的音乐，避免过度使用电子产品等。建议您保持良好的生活习惯，让身体和大脑得到充分的放松，提高睡眠质量。";
        } else if (onbedsleeptime/(1000*60) > 540 && indeedsleeptime/(1000*60) > 420) { // 睡眠时间充足且有效睡眠时间达标
            advice = "您的睡眠状况非常好，请继续保持良好的睡眠习惯。良好的睡眠习惯可以提高身体免疫力，保持身体和大脑的健康，有助于提高生活质量。建议您继续保持规律的睡眠时间和作息习惯。\n" +
                    "\n";
        } else if (onbedsleeptime/(1000*60) < 420) { // 总睡眠时间不足 7 小时
            advice = "您的睡眠时长不足，请尝试增加睡眠时间。长期睡眠不足可能会导致身体免疫力下降，记忆力减退，注意力不集中等问题。建议您制定一个规律的睡眠计划，保证充足的睡眠时间，让身体充分休息，保持良好的身体状态。";
        } else { // 其他情况
            advice = "您的睡眠状况良好，请继续保持良好的睡眠习惯。良好的睡眠习惯可以提高身体免疫力，保持身体和大脑的健康，有助于提高生活质量。建议您保持规律的睡眠时间和作息习惯，避免熬夜和使用电子产品等影响睡眠的行为。";
        }
        //
        textView4.setText(advice);
        // 获取 PieChart 控件
        PieChart pieChart = findViewById(R.id.pie_chart);
        // 设置数据
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(100, "深度睡眠时长"));
        entries.add(new PieEntry(20, "浅度睡眠时长"));
        //entries.add(new PieEntry(10f, "快速眼动睡眠"));
        entries.add(new PieEntry(30, "清醒状态时长"));
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData = new PieData(dataSet);
        //设置数据
        // 设置样式
        pieChart.setData(pieData);
        pieChart.setDrawEntryLabels(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setTouchEnabled(true);//开启触摸事件
        // 添加 OnChartValueSelectedListener
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // 获取所选条目的索引
                int index = (int) h.getX();
                // 获取对应的名称
                String name = entries.get(index).getLabel();
                // 在屏幕上显示名称
                Toast.makeText(ReportActivity.this, name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {
                // 当没有条目被选中时，不执行任何操作
            }
        });
        pieChart.invalidate();
        // 设置样式
        btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}