package com.xf.psychology.ui.activity;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.adapter.CommonAdapter;
import com.xf.psychology.adapter.ViewHolder;
import com.xf.psychology.base.BaseActivity;
import com.xf.psychology.bean.BookBean;
import com.xf.psychology.db.DBCreator;

import java.util.ArrayList;
import java.util.List;


public class MyBookActivity extends BaseActivity {
    private TextView titleView;
    private RecyclerView recyclerView;
    private List<BookBean> bookBeans = new ArrayList<>();
    private CommonAdapter<BookBean> adapter = new CommonAdapter<BookBean>(R.layout.item_book, bookBeans) {
        @Override
        public void bind(ViewHolder holder, BookBean bookBean, int position) {
            ShapeableImageView shapeableImageView = holder.getView(R.id.faceIv);
            Glide.with(shapeableImageView).load(bookBean.facePicPath).into(shapeableImageView);
            holder.setText(R.id.bookNameTv, "《" + bookBean.bookName + "》");
            holder.setText(R.id.authorTv, bookBean.author);
            holder.setText(R.id.whyWantTv, bookBean.whyWant);
            holder.setText(R.id.upNameTv, bookBean.upName);
            shapeableImageView.getLayoutParams().height = getScreenWidth() * 3 / 7;
        }
    };

    private int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    protected void initListener() {

    }
    private     View emptyView;
    @Override
    protected void initData() {
        titleView.setText("我推荐的书");
        List<BookBean> queryData = DBCreator.getBookDao().queryByUpId(App.user.id);
        if (queryData != null) {
            bookBeans.addAll(queryData);
        }
        recyclerView.setAdapter(adapter);

        if (bookBeans.isEmpty()){
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
        titleView = findViewById(R.id.titleView);
        emptyView = findViewById(R.id.emptyView);
    }
}
