package com.xf.psychology.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.xf.psychology.R;
import com.xf.psychology.base.BaseActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityCenterActivity extends BaseActivity {

    public View onlineView;
    public View offlineView;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        onlineView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityCenterActivity.this, OnlineChatActivity.class));
            }
        });
        offlineView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCenterActivity.this);
            AlertDialog alertDialog = builder.create();
            ViewGroup r = (ViewGroup) alertDialog.getWindow().getDecorView();
            View view = LayoutInflater.from(ActivityCenterActivity.this).inflate(R.layout.dialog_date_picker, r, false);
            DatePicker datePicker = view.findViewById(R.id.datePicker);
            TimePicker timePick = view.findViewById(R.id.timePick);

            datePicker.setMinDate(System.currentTimeMillis());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        datePicker.setVisibility(View.INVISIBLE);
                        timePick.setVisibility(View.VISIBLE);
                    }
                });
            }
            View noView = view.findViewById(R.id.noView);
            View okView = view.findViewById(R.id.okView);
            okView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (datePicker.getVisibility() == View.VISIBLE) {
                        toast("请选择日期");
                        return;
                    }
                    int year = datePicker.getYear();
                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth() + 1;
                    int hour = timePick.getHour();
                    int minute = timePick.getMinute();
                    String dateStr = year + "-" + month + "-" + day + " " + hour + ":" + minute;
                    Log.d("TAG", "onClick: " + dateStr);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:m");
                    try {
                        Date selectDate = dateFormat.parse(dateStr);
                        Log.d("TAG", "onClick: " + selectDate.getTime());
                        Log.d("TAG", "onClick: " + System.currentTimeMillis());
                        if (selectDate.getTime() <= System.currentTimeMillis()) {
                            toast("选择的时间已过期，请重新选择");
                            return;
                        }
                        Toast.makeText(ActivityCenterActivity.this, "预约成功！请于" + dateStr + "前往心理活动中心", Toast.LENGTH_LONG).show();
                        alertDialog.dismiss();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Log.d("TAG", "onClick: " + dateStr);
                }
            });
            noView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            alertDialog.setView(view);
            alertDialog.show();
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void findViewsById() {

        onlineView = findViewById(R.id.onlineView);
        offlineView = findViewById(R.id.offlineView);
    }
}
