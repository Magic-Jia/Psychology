package com.xf.psychology.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.adapter.CommonAdapter;
import com.xf.psychology.adapter.ViewHolder;
import com.xf.psychology.base.BaseActivity;
import com.xf.psychology.bean.FMBean;
import com.xf.psychology.db.DBCreator;

import java.util.ArrayList;
import java.util.List;

public class MyFmActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private List<FMBean> fmData = new ArrayList<>();
    private CommonAdapter<FMBean> fmBeanCommonAdapter = new CommonAdapter<FMBean>(R.layout.item_fm, fmData) {
        @Override
        public void bind(ViewHolder holder, FMBean fmBean, int position) {
            holder.setText(R.id.titleTv, fmBean.fmTitle);
            holder.setText(R.id.secTitleTv, fmBean.fmSecTitle);
            holder.setText(R.id.fmAnchorTv, fmBean.fmAuthor);
            holder.setText(R.id.uperTv, "上传者：" + fmBean.up);
            ImageView faceView = holder.getView(R.id.faceIv);
            Glide.with(faceView).load(fmBean.faceFilePath).into(faceView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MyFmActivity.this, MusicActivity.class)
                            .putExtra("fmBean", fmBean));
                }
            });
        }
    };

    @Override
    protected void initListener() {

    }
    View emptyView;
    @Override
    protected void initData() {
        List<FMBean> queryData = DBCreator.getFMDao().queryByUpId(App.user.id);
        fmData.addAll(queryData);
        recyclerView.setAdapter(fmBeanCommonAdapter);

        if (fmData.isEmpty()){
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_fm;
    }

    @Override
    protected void findViewsById() {
        emptyView = findViewById(R.id.emptyView);
        recyclerView = findViewById(R.id.recyclerView);
    }
}