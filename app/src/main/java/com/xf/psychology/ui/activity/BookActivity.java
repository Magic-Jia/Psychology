package com.xf.psychology.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.xf.psychology.R;
import com.xf.psychology.adapter.CommonAdapter;
import com.xf.psychology.adapter.ViewHolder;
import com.xf.psychology.base.BaseActivity;
import com.xf.psychology.bean.BookBean;
import com.xf.psychology.event.UpBookSuccessEvent;
import com.xf.psychology.db.DBCreator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends BaseActivity {
    private RecyclerView bookRecycler;
    private TextView uploadTv;
    private List<BookBean> bookBeans = new ArrayList<>();
    private CommonAdapter<BookBean> adapter = new CommonAdapter<BookBean>(R.layout.item_book, bookBeans) {
        @Override
        public void bind(ViewHolder holder, BookBean bookBean, int position) {
            ShapeableImageView shapeableImageView = holder.getView(R.id.faceIv);
            Glide.with(shapeableImageView).load(bookBean.facePicPath).into(shapeableImageView);
            holder.setText(R.id.bookNameTv, "《"+bookBean.bookName+"》");
            holder.setText(R.id.authorTv, bookBean.author);
            holder.setText(R.id.whyWantTv, bookBean.whyWant);
            holder.setText(R.id.upNameTv, bookBean.upName);
            shapeableImageView.getLayoutParams().height = getScreenWidth() * 3 / 7;

        }
    };
    private View emptyView;

    @Override
    protected void initListener() {
        uploadTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookActivity.this, UpBookActivity.class));
            }
        });
    }

    private int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        List<BookBean> queryData = DBCreator.getBookDao().queryAll();
        if (queryData != null) {
            bookBeans.addAll(queryData);
        }
        bookRecycler.setAdapter(adapter);
        if (bookBeans.isEmpty()){
            emptyView.setVisibility(View.VISIBLE);
            bookRecycler.setVisibility(View.GONE);
        }else {
            emptyView.setVisibility(View.GONE);
            bookRecycler.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpSuccessEvent(UpBookSuccessEvent event) {
        bookBeans.clear();
        List<BookBean> queryData = DBCreator.getBookDao().queryAll();
        if (queryData != null) {
            bookBeans.addAll(queryData);
        }
        adapter.notifyDataSetChanged();
        if (bookBeans.isEmpty()){
            emptyView.setVisibility(View.VISIBLE);
            bookRecycler.setVisibility(View.GONE);
        }else {
            emptyView.setVisibility(View.GONE);
            bookRecycler.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void findViewsById() {
        bookRecycler = findViewById(R.id.bookRecycler);
        emptyView = findViewById(R.id.emptyView);
        uploadTv = findViewById(R.id.uploadTv);
    }
}