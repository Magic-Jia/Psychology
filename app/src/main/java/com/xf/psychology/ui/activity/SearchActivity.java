package com.xf.psychology.ui.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.jaeger.library.StatusBarUtil;
import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.adapter.CommonAdapter;
import com.xf.psychology.adapter.HappyShareAdapter;
import com.xf.psychology.adapter.ViewHolder;
import com.xf.psychology.base.BaseActivity;
import com.xf.psychology.bean.AnswerBean;
import com.xf.psychology.bean.BookBean;
import com.xf.psychology.bean.DoShareBean;
import com.xf.psychology.bean.FMBean;
import com.xf.psychology.bean.FollowBean;
import com.xf.psychology.bean.QuestionBean;
import com.xf.psychology.bean.QuestionShowBean;
import com.xf.psychology.bean.ShareBeanXF;
import com.xf.psychology.bean.ShareCommentBean;
import com.xf.psychology.db.DBCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SearchActivity extends BaseActivity {

    private CommonTabLayout tabLayout;
    private TextView search;
    private EditText edit;
    private RecyclerView recycler;
    private List<BookBean> bookBeans = new ArrayList<>();
    private List<DoShareBean> shareBeans = new ArrayList<>();
    private List<QuestionShowBean> questionShowBeans = new ArrayList<>();
    private List<FMBean> fmBeans = new ArrayList<>();

    private String currentKey = "";
    private Executor executor = Executors.newSingleThreadExecutor();

    private HappyShareAdapter shareAdapter = new HappyShareAdapter(shareBeans);
    private CommonAdapter<QuestionShowBean> questionShowBeanCommonAdapter = new CommonAdapter<QuestionShowBean>(R.layout.item_question, questionShowBeans) {
        @Override
        public void bind(ViewHolder holder, QuestionShowBean questionShowBean, int position) {
            Log.d("TAG", "bind: ");
            holder.setText(R.id.questionTv, questionShowBean.question);
            holder.setText(R.id.userName, questionShowBean.raiserNickName);
            holder.setText(R.id.timeTv, questionShowBean.time);
            holder.setText(R.id.firstAnswer, questionShowBean.firstAnswer);
            holder.getView(R.id.followTv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SearchActivity.this, QuestionDetailActivity.class).putExtra("questionShowBean", questionShowBean));
                }
            });
        }
    };
    private CommonAdapter<BookBean> bookAdapter = new CommonAdapter<BookBean>(R.layout.item_book, bookBeans) {
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

    private CommonAdapter<FMBean> fmBeanCommonAdapter = new CommonAdapter<FMBean>(R.layout.item_fm, fmBeans) {
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
                    startActivity(new Intent(SearchActivity.this, MusicActivity.class)
                            .putExtra("fmBean", fmBean));
                }
            });
        }
    };


    private int currentTab = 0;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            recycler.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
            switch (currentTab) {
                case 0:
                    recycler.setAdapter(shareAdapter);
                    if (shareBeans.isEmpty()) {
                        emptyView.setVisibility(View.VISIBLE);
                        recycler.setVisibility(View.GONE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        recycler.setVisibility(View.VISIBLE);
                    }
                    break;
                case 1:
                    recycler.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));
                    recycler.setAdapter(bookAdapter);
                    if (bookBeans.isEmpty()) {
                        emptyView.setVisibility(View.VISIBLE);
                        recycler.setVisibility(View.GONE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        recycler.setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:
                    recycler.setAdapter(fmBeanCommonAdapter);
                    if (fmBeans.isEmpty()) {
                        emptyView.setVisibility(View.VISIBLE);
                        recycler.setVisibility(View.GONE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        recycler.setVisibility(View.VISIBLE);
                    }
                    break;
                case 3:
                    recycler.setAdapter(questionShowBeanCommonAdapter);
                    if (questionShowBeans.isEmpty()) {
                        emptyView.setVisibility(View.VISIBLE);
                        recycler.setVisibility(View.GONE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        recycler.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    };
    private View emptyView;

    private void searchFM() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                fmBeans.clear();
                List<FMBean> queryBean = DBCreator.getFMDao().queryAll();
                if (queryBean != null) {
                    for (FMBean queryDatum : queryBean) {
                        if (queryDatum.contains(currentKey)) {
                            fmBeans.add(queryDatum);
                        }
                    }
                }
                handler.sendEmptyMessage(0);
            }
        });
    }

    private void searchQuestion() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                questionShowBeans.clear();
                List<QuestionShowBean> temp = new ArrayList<>();
                List<QuestionBean> questionBeans = DBCreator.getQuestionDao().queryAll();
                int size = questionBeans.size();
                Log.d("TAG", "getQuestions: " + size);
                for (int i = questionBeans.size() - 1; i >= 0; i--) {
                    QuestionBean questionBean = questionBeans.get(i);
                    QuestionShowBean questionShowBean = new QuestionShowBean();
                    questionShowBean.questionId = questionBean.questionId;
                    questionShowBean.raiserId = questionBean.raiserId;
                    questionShowBean.question = questionBean.question;
                    questionShowBean.raiserNickName = questionBean.raiserNickName;
                    questionShowBean.raiserIcon = questionBean.raiserIcon;
                    questionShowBean.detail = questionBean.detail;
                    questionShowBean.time = questionBean.time;
                    FollowBean followBeans = DBCreator.getFollowDao().queryFollowByABId(questionShowBean.raiserId, App.user.id);
                    questionShowBean.isFollowed = followBeans != null;
                    List<AnswerBean> answers = DBCreator.getAnswerDao().queryByQuestionId(questionShowBean.questionId);
                    questionShowBean.firstAnswer = (answers != null && !answers.isEmpty()) ? answers.get(0).answer : "此问题暂时无人回答哦，等你来回答";
                    temp.add(questionShowBean);
                }
                for (QuestionShowBean questionShowBean : temp) {
                    if (questionShowBean.contains(currentKey)) {
                        questionShowBeans.add(questionShowBean);
                    }
                }
                handler.sendEmptyMessage(0);
            }
        });
    }


    private void searchBook() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                bookBeans.clear();
                List<BookBean> queryData = DBCreator.getBookDao().queryAll();
                if (queryData != null) {
                    for (BookBean queryDatum : queryData) {
                        if (queryDatum.contains(currentKey)) {
                            bookBeans.add(queryDatum);
                        }
                    }
                }
                handler.sendEmptyMessage(0);
            }
        });
    }

    private int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    private void searchDt() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                shareBeans.clear();
                ArrayList<DoShareBean> temp = new ArrayList<>();
                List<ShareBeanXF> shareBeanXFS = DBCreator.getShareDao().queryAll();
                Log.d("TAG", "initData: " + shareBeanXFS);
                for (int i = shareBeanXFS.size() - 1; i >= 0; i--) {
                    ShareBeanXF shareBeanXF = shareBeanXFS.get(i);
                    DoShareBean doShareBean = new DoShareBean();
                    doShareBean.authorId = shareBeanXF.authorId;
                    doShareBean.authorNickName = shareBeanXF.authorNickName;
                    doShareBean.content = shareBeanXF.content;
                    doShareBean.shareId = shareBeanXF.id;
                    doShareBean.picPaths = shareBeanXF.picPaths;
                    List<FollowBean> followBeans = DBCreator.getFollowDao().queryFollowByAId(doShareBean.authorId);
                    for (FollowBean followBean : followBeans) {
                        if (followBean.bId == App.user.id) {
                            doShareBean.isFollow = true;
                        }
                    }
                    List<ShareCommentBean> shareCommentBeans = DBCreator.getShareCommentDao().queryByShareId(shareBeanXF.id);
                    doShareBean.messages = shareCommentBeans == null ? 0 : shareCommentBeans.size();
                    long l = System.currentTimeMillis() - shareBeanXF.time;
                    Log.d("TAG", "initData: " + l);
                    if (l <= 1000 * 60) {//一分钟内
                        doShareBean.time = "刚刚";
                    } else if (l <= 1000 * 60 * 10) {//10分钟内
                        doShareBean.time = (l / 1000 / 60) + "分钟前";
                    } else if (l <= 1000 * 60 * 30) {//半小时内
                        doShareBean.time = "半小时前";
                    } else if (l <= 1000 * 60 * 60 * 24) {//一天内
                        doShareBean.time = (l / 1000 / 60 / 60) + "小时前";
                    } else {
                        doShareBean.time = "一天前";
                    }
                    temp.add(doShareBean);
                }
                for (DoShareBean doShareBean : temp) {
                    if (doShareBean.contains(currentKey)) {
                        shareBeans.add(doShareBean);
                    }
                }
                handler.sendEmptyMessage(0);
            }
        });
    }

    private void startSearch() {
        switch (currentTab) {
            case 0:
                searchDt();
                break;
            case 1:
                searchBook();
                break;
            case 2:
                searchFM();
                break;
            case 3:
                searchQuestion();
                break;
        }
    }

    @Override
    protected void initListener() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentKey = edit.getText().toString().trim();
                startSearch();
            }
        });
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                currentTab = position;
                currentKey = edit.getText().toString().trim();
                if (currentKey.isEmpty()) {
                    return;
                }
                startSearch();

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }


    @Override
    protected void initData() {
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.colorPrimary));
        ArrayList<CustomTabEntity> myEntries = new ArrayList<>();
        myEntries.add(new MyEntry("动态"));
        myEntries.add(new MyEntry("好书"));
        myEntries.add(new MyEntry("FM"));
        myEntries.add(new MyEntry("问答"));
        tabLayout.setTabData(myEntries);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void findViewsById() {
        tabLayout = findViewById(R.id.tabLayout);
        search = findViewById(R.id.search);
        recycler = findViewById(R.id.recycler);
        edit = findViewById(R.id.edit);
        emptyView = findViewById(R.id.emptyView);

    }

    private class MyEntry implements CustomTabEntity {
        private String title;

        private MyEntry(String title) {
            this.title = title;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return 0;
        }

        @Override
        public int getTabUnselectedIcon() {
            return 0;
        }
    }
}