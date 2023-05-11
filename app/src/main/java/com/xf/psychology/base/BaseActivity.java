package com.xf.psychology.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.jaeger.library.StatusBarUtil;
import com.xf.psychology.R;

import java.sql.SQLException;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        findViewsById();
        initListener();
        try {
            initData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.white));
        }

        View backBtn = findViewById(R.id.backBtn);
        if (backBtn!=null){
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    protected abstract void initListener();

    protected abstract void initData() throws SQLException;

    protected abstract int getLayoutId();

    protected abstract void findViewsById();

    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
