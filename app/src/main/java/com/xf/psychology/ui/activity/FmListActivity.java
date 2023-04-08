package com.xf.psychology.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xf.psychology.R;
import com.xf.psychology.adapter.CommonAdapter;
import com.xf.psychology.adapter.ViewHolder;
import com.xf.psychology.base.BaseActivity;
import com.xf.psychology.bean.FMBean;
import com.xf.psychology.db.DBCreator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class FmListActivity extends BaseActivity {
    private RecyclerView fmRecycler;
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
                    startActivity(new Intent(FmListActivity.this, MusicActivity.class)
                            .putExtra("fmBean", fmBean));
                }
            });
        }
    };
    private View emptyView;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        int type = getIntent().getIntExtra("type", -1);
        if (type == -1) {
            finish();
            toast("参数错误");
            return;
        }
        List<FMBean> fmQueryData = DBCreator.getFMDao().queryByType(type);
        if (fmQueryData != null && !fmQueryData.isEmpty()) {
            fmData.addAll(fmQueryData);
        }
        fmRecycler.setAdapter(fmBeanCommonAdapter);
        if (fmData.isEmpty()){
            emptyView.setVisibility(View.VISIBLE);
            fmRecycler.setVisibility(View.GONE);
        }else {
            emptyView.setVisibility(View.GONE);
            fmRecycler.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fm_list;
    }

    @Override
    protected void findViewsById() {
        fmRecycler = findViewById(R.id.fmRecycler);
        emptyView = findViewById(R.id.emptyView);
    }
}