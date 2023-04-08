package com.xf.psychology.ui.activity;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xf.psychology.R;
import com.xf.psychology.adapter.CommonAdapter;
import com.xf.psychology.adapter.ViewHolder;
import com.xf.psychology.base.BaseActivity;
import com.xf.psychology.bean.FMBean;
import com.xf.psychology.bean.FMMenuItem;
import com.xf.psychology.db.DBCreator;
import com.xf.psychology.event.FMUpSuccessEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class FMActivity extends BaseActivity {
    private String fileParentPath;
    private List<FMMenuItem> items = new ArrayList<>();
    private RecyclerView menuRecycler;
    private RecyclerView fmRecycler;

    private TextView uploadTv;

    private CommonAdapter<FMMenuItem> menuItemCommonAdapter = new CommonAdapter<FMMenuItem>(R.layout.item_fm_menu, items) {
        @Override
        public void bind(ViewHolder holder, FMMenuItem fmMenuItem, int position) {
            holder.setText(R.id.actionTv, fmMenuItem.getName());
            ImageView view = holder.getView(R.id.iconView);
            Glide.with(view).load(fmMenuItem.getIcon()).into(view);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(FMActivity.this, FmListActivity.class)
                            .putExtra("type", position+1));
                }
            });
        }
    };

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
                    startActivity(new Intent(FMActivity.this, MusicActivity.class)
                            .putExtra("fmBean", fmBean));
                }
            });
        }
    };
    private View emptyView;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//

////        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent = new Intent(Intent.ACTION_PICK);
////                intent.setType("audio/*");
////                startActivityForResult(intent, 100);
////            }
////        });
//    }

    @Override
    protected void initListener() {
        uploadTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FMActivity.this, FmUpActivity.class));
            }
        });
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        items.add(new FMMenuItem("情绪管理", R.drawable.ic_qxgl));
        items.add(new FMMenuItem("人际沟通", R.drawable.ic_rjgt));
        items.add(new FMMenuItem("心理科普", R.drawable.ic_kp));
        items.add(new FMMenuItem("课程讲座", R.drawable.ic_jz));
        menuRecycler.setAdapter(menuItemCommonAdapter);
        List<FMBean> fmBeans = DBCreator.getFMDao().queryAll();
        fmData.addAll(fmBeans);
        fmRecycler.setAdapter(fmBeanCommonAdapter);
        if (fmData.isEmpty()){
            emptyView.setVisibility(View.VISIBLE);
            fmRecycler.setVisibility(View.GONE);
        }else {
            emptyView.setVisibility(View.GONE);
            fmRecycler.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpSuccessEvent(FMUpSuccessEvent event) {
        fmData.clear();
        List<FMBean> fmBeans = DBCreator.getFMDao().queryAll();
        fmData.addAll(fmBeans);
        fmBeanCommonAdapter.notifyDataSetChanged();
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
        return R.layout.activity_fm;
    }

    @Override
    protected void findViewsById() {
        menuRecycler = findViewById(R.id.menuRecycler);
        fmRecycler = findViewById(R.id.fmRecycler);
        uploadTv = findViewById(R.id.uploadTv);
        emptyView = findViewById(R.id.emptyView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}