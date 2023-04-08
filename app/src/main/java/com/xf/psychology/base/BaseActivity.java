package com.xf.psychology.base;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.jaeger.library.StatusBarUtil;
import com.xf.psychology.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        findViewsById();
        initListener();
        initData();
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

    protected abstract void initData();

    protected abstract int getLayoutId();

    protected abstract void findViewsById();

    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
