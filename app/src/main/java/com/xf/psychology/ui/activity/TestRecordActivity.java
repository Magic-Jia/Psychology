package com.xf.psychology.ui.activity;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.adapter.TestRecordAdapter;
import com.xf.psychology.base.BaseActivity;
import com.xf.psychology.bean.TestRecordXFBean;
import com.xf.psychology.db.DBCreator;

import java.util.ArrayList;
import java.util.List;

public class TestRecordActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private View emptyView;
    private List<TestRecordXFBean> data = new ArrayList<>();
    private TestRecordAdapter adapter = new TestRecordAdapter(data);

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        List<TestRecordXFBean> testRecordXFBeans = DBCreator.getTestRecordDao().queryByPhone(App.user.phone);
        data.addAll(testRecordXFBeans);
        recyclerView.setAdapter(adapter);
        if (data.isEmpty()){
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_record;
    }

    @Override
    protected void findViewsById() {
        recyclerView = findViewById(R.id.recyclerView);
        emptyView = findViewById(R.id.emptyView);
    }
}
