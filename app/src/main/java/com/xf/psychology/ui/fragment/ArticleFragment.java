package com.xf.psychology.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xf.psychology.R;
import com.xf.psychology.adapter.CommonAdapter;
import com.xf.psychology.adapter.ViewHolder;
import com.xf.psychology.base.BaseFragment;
import com.xf.psychology.ui.activity.ImageActivity;

import java.util.ArrayList;
import java.util.List;


public class ArticleFragment extends BaseFragment {


    private class Article {
        public Article(String title, String secTitle, int contentResId, int faceResId) {
            this.faceResId = faceResId;
            this.title = title;
            this.secTitle = secTitle;
            this.contentResId = contentResId;
        }

        public String title;
        public String secTitle;
        public int faceResId;
        public int contentResId;
    }


    private RecyclerView recycler;
    private List<Article> articles = new ArrayList<>();
    private CommonAdapter<Article> adapter = new CommonAdapter<Article>(R.layout.item_article, articles) {
        @Override
        public void bind(ViewHolder holder, Article article, int position) {
            holder.setText(R.id.titleTv, article.title);
            holder.setText(R.id.secTitleTv, article.secTitle);
            ImageView view = holder.getView(R.id.faceIv);
            Glide.with(holder.itemView).load(article.faceResId).into(view);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(requireActivity(), ImageActivity.class).putExtra("res", article.contentResId));
                }
            });
        }
    };

    public ArticleFragment() {
    }

    public static ArticleFragment newInstance() {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {

        articles.add(new Article("抑郁症得到改善会有哪些迹象", "随着抑郁症越来越多被更多人了解，我们也发现，身边的不少人正在受抑郁症困扰，今天这篇文章是给那些正在“恢复”中的朋友的，也许你正在慢慢好转。祝你早日回归正常生活！",
                R.drawable.arti1_2,
                R.drawable.arti1_1));
        articles.add(new Article("幸福生活的7个小习惯",
                "近年来，在术后康复中心、生活养生馆等场所，我们经常会听到专业人士提及“生活方式医学”这个热点词。其实，生活方式不仅仅是吃好一点、睡好一点，它已经上升到了医学调节的高度。",
                R.drawable.arti2_2,
                R.drawable.arti2_1));
        articles.add(new Article("心理体验小分享|大学生的甜蜜春日故事", "春天的风里有不止这些，还有春草暖阳，还有伴侣们并肩坐在草坪上。春风正好，阳光正好。 正是你来的那天，春天也来到，风景刚好。 在这个春日里，我们收集了一些伴侣们的故事， 希望TA们的故事能给你的春日带来另一份甜蜜。",
                R.drawable.arti3_1,
                R.drawable.arti3_2));
        articles.add(new Article("青少年保持心理健康--请你这样做", "青年学生正处在从青年向成人转化的重要时期，正逐渐从依赖父母的心理状态中独立出来",
                R.drawable.arti4_2,
                R.drawable.arti4_1));
        articles.add(new Article("情绪闪回|久违的心理学小科普", "在此之前，我几乎忘了这些事情",
                R.drawable.arti5_2,
                R.drawable.arti5_1));


        recycler.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void findViewsById(View view) {
        recycler = view.findViewById(R.id.recycler);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_article;
    }
}