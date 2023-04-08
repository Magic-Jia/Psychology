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

        articles.add(new Article("生活中的心理学|大学生恋爱心理特征", "爱情是人类永恒的话题，也是大学校园内一道亮丽的风景线。正值青春期的大学生，生理趋于成熟，卸下了高中学业的重压，免除了老师家长的约束，由于情感的需要及周围环境的影响，大学生们渴望爱情，想谈恋爱已成为一种较普遍的心理。对于文",
                R.drawable.lianaixinli1,
                R.drawable.lianaixinli_f_1));
        articles.add(new Article("心理科普│大学生恋爱心理",
                "近些年，不少高校开设了以恋爱为主题的课程，授课教师希望以此培养学生“健康的爱情观\"。根据中国青年报10月12日报",
                R.drawable.lianaixinli2,
                R.drawable.lianaixinli_f_2));
        articles.add(new Article("心理小知识|大学生的恋爱心理", "恋爱已成为当代大学生最为关注和最想尝试的事情之一，由于大学生的心智正处于逐渐成熟的阶段，幼稚性和成熟性并存，因此恋爱过程中常伴随着各种心理问题。从现实表现来看，一些大学生不能处理好恋爱中的关系，从而引发多种心理困惑，严重影响了大学生的身心健康发展。",
                R.drawable.lianaixinli3,
                R.drawable.lianaixinli_f_3));
        articles.add(new Article("【心理健康】浅谈大学生恋爱心理", "美好的爱情一直以来是人们最大追求，而大学生恋爱已经越来越被人视为平常了。然而,恋爱问题恰恰又是其最感困惑的问题之一,严重影响到他们的学习、生活乃至人格的健康发展。因此,关注大学生恋爱心理,培养大学生正确的恋爱行为,成为大学生心理健康教育工作的一项重要内容。恋爱就象吃辣椒，\n",
                R.drawable.lianaixinli4,
                R.drawable.lianaixinli_f_4));

        articles.add(new Article(
                "辅导员说|大学生如何提高自己的情绪管理能力",
                "情绪管理能力是指一个人对个体主观情绪的自我管理能力。“情绪管理\"就是用最合适的方式来表达和传播情绪，如同亚里士多德所言:“任何人都会生气，这没什么难的，但要能适时适所，以适当方式对适当的对象怡如其分地生气，可就难上加难。\"据此，情绪管理指的是要根据特定",
                R.drawable.qxyl1,
                R.drawable.qxyl_1));
        articles.add(new Article("与大学生谈心理:情绪管理的九种方法", "近几年，情绪领导力作为一种新型领导力视角,备受学界和商界的重视和推崇。情绪领导力不仅从意识层面拓宽了对领导力的微观解读，在实践层面也为引领者应对新时代情绪危机提供了新的路径。作为正在成长中的大学生，掌握一定的情绪管理方法和技巧，对提升自我领导力，扩大团队的影响力都非常必要。\n",
                R.drawable.qxyl2,
                R.drawable.qxyl_2));

        articles.add(new Article("大学生如何应对持续的生活压力", "朋友跟我吐槽:“长假过后，周一上学依然是满满的疲惫。明明在床上睡了两天，为什么身体还是这么沉重?\"这种情况并不少见，在平时，我们也能经常听到身边人抱怨:“明明没有做什么，为什么总是感觉压力很大?究竟为什么，我每天都过得这么疲惫?”今天，我们就来和大家聊聊，每个人都逃不开的“生活压力\"这件事。", R.drawable.qxyl3,
                R.drawable.qxyl_3));


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