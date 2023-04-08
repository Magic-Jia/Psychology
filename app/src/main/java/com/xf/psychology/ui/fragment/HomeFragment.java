package com.xf.psychology.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.adapter.CommonAdapter;
import com.xf.psychology.adapter.HappyShareAdapter;
import com.xf.psychology.adapter.ViewHolder;
import com.xf.psychology.base.BaseFragment;
import com.xf.psychology.bean.AnswerBean;
import com.xf.psychology.bean.DoShareBean;
import com.xf.psychology.bean.FollowBean;
import com.xf.psychology.bean.QuestionBean;
import com.xf.psychology.bean.QuestionShowBean;
import com.xf.psychology.bean.ShareBeanXF;
import com.xf.psychology.bean.ShareCommentBean;
import com.xf.psychology.db.DBCreator;
import com.xf.psychology.event.ShareSuccessEvent;
import com.xf.psychology.ui.activity.BookActivity;
import com.xf.psychology.ui.activity.FMActivity;
import com.xf.psychology.ui.activity.QuestionDetailActivity;
import com.xf.psychology.ui.activity.RaiseQuestionActivity;
import com.xf.psychology.ui.activity.ActivityCenterActivity;
import com.xf.psychology.ui.activity.SearchActivity;
import com.xf.psychology.ui.activity.ShareFeelingActivity;
import com.xf.psychology.ui.activity.TestActivity;
import com.xf.psychology.util.GlideUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends BaseFragment {
    private View homeActionTest;
    private View searchBar;
    private CommonTabLayout tabLayout;
    private View homeActionBook;
    private View homeActionChat;
    private View homeActionShare;
    private View emptyView;
    private View homeActionFM;
    private View editQuestion;
    private SliderLayout slider;
    private RecyclerView shareRecycler;

    private List<DoShareBean> realData = new ArrayList<>();
    private HappyShareAdapter adapter = new HappyShareAdapter(realData);
    private List<QuestionShowBean> questionShowBeans = new ArrayList<>();

    private CommonAdapter<QuestionShowBean> questionShowBeanCommonAdapter = new CommonAdapter<QuestionShowBean>(R.layout.item_question, questionShowBeans) {
        @Override
        public void bind(ViewHolder holder, QuestionShowBean questionShowBean, int position) {
            Log.d("TAG", "bind: ");
            holder.setText(R.id.questionTv, questionShowBean.question);
            holder.setText(R.id.userName, questionShowBean.raiserNickName);
            holder.setText(R.id.timeTv, questionShowBean.time);
            holder.setText(R.id.firstAnswer, questionShowBean.firstAnswer);
            View followTv = holder.getView(R.id.followTv);
            if (questionShowBean.raiserId == App.user.id) {
                followTv.setVisibility(View.GONE);
            } else {
                if (!questionShowBean.isFollowed) {
                    holder.setText(R.id.followTv, "+ 关注");
                } else {
                    holder.setText(R.id.followTv, "已关注");
                }
            }

            followTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (questionShowBean.isFollowed) {
                        questionShowBean.isFollowed = false;
                        FollowBean followBean = DBCreator.getFollowDao().queryFollowByABId(questionShowBean.raiserId, App.user.id);
                        DBCreator.getFollowDao().del(followBean);
                        holder.setText(R.id.followTv, "+ 关注");
                    } else {
                        questionShowBean.isFollowed = true;
                        FollowBean followBean = new FollowBean();
                        followBean.aId = questionShowBean.raiserId;
                        followBean.aNickName = questionShowBean.raiserNickName;
                        followBean.aIconPath = questionShowBean.raiserIcon;
                        followBean.bId = App.user.id;
                        followBean.bNickName = App.user.name;
                        followBean.bIconPath = App.user.name;
                        DBCreator.getFollowDao().insert(followBean);
                        holder.setText(R.id.followTv, "已关注");
                    }
                    notifyDataSetChanged();
                }
            });
            CircleImageView view = holder.getView(R.id.userIcon);
            GlideUtil.load(view, questionShowBean.raiserIcon);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(requireActivity(), QuestionDetailActivity.class).putExtra("questionShowBean", questionShowBean));
                }
            });
        }
    };

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private int currentTab = 0;

    @Override
    protected void initData() {

        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        tabEntities.add(new MyEntry("广场"));
        tabEntities.add(new MyEntry("关注"));
        tabEntities.add(new MyEntry("问答"));

        tabLayout.setTabData(tabEntities);

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        slider.getLayoutParams().height = screenWidth * 3 / 8;
        List<Integer> resIds = new ArrayList<>();
        resIds.add(R.drawable.bg_home_1);
        resIds.add(R.drawable.bg_home_2);
        resIds.add(R.drawable.bg_home_3);
        resIds.add(R.drawable.bg_home_4);
        resIds.add(R.drawable.bg_home_6);
        resIds.add(R.drawable.bg_home_7);
        resIds.add(R.drawable.bg_home_8);
        resIds.add(R.drawable.bg_home_9);

        for (Integer resId : resIds) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView.image(resId).setScaleType(BaseSliderView.ScaleType.CenterCrop);
            slider.addSlider(textSliderView);
        }

        slider.setDuration(2500);
        slider.startAutoCycle();
        shareRecycler.setAdapter(adapter);
        getAllShare();

    }

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (currentTab) {
                case 0:
                case 1:
                    shareRecycler.setAdapter(adapter);
                    break;
                case 2:
                    shareRecycler.setAdapter(questionShowBeanCommonAdapter);

            }
        }
    };

    private void getAllShare() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                realData.clear();
                List<ShareBeanXF> shareBeanXFS = DBCreator.getShareDao().queryAll();
                Log.d("TAG", "initData: " + shareBeanXFS);
                for (int i = shareBeanXFS.size() - 1; i >= 0; i--) {
                    ShareBeanXF shareBeanXF = shareBeanXFS.get(i);
                    DoShareBean doShareBean = new DoShareBean();
                    doShareBean.authorId = shareBeanXF.authorId;
                    doShareBean.authorNickName = shareBeanXF.authorNickName;
                    doShareBean.authorIcon = shareBeanXF.authorIcon;
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
                    realData.add(doShareBean);

                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (realData.isEmpty()) {
                            emptyView.setVisibility(View.VISIBLE);
                            shareRecycler.setVisibility(View.GONE);
                        } else {
                            emptyView.setVisibility(View.GONE);
                            shareRecycler.setVisibility(View.VISIBLE);
                        }
                    }
                });
                handler.sendEmptyMessage(0);
            }
        });
    }

    @Override
    protected void initListener() {
        homeActionFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), FMActivity.class));
            }
        });
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                Log.d("TAG", "onTabSelect: " + position);
                currentTab = position;
                switch (position) {
                    case 0:
                        getAllShare();
                        editQuestion.setVisibility(View.GONE);
                        break;
                    case 1:
                        getFollowedShare();
                        editQuestion.setVisibility(View.GONE);
                        break;
                    case 2:
                        getQuestions();
                        editQuestion.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        homeActionTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), TestActivity.class));
            }
        });
        homeActionChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), ActivityCenterActivity.class));
            }
        });
        homeActionShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), ShareFeelingActivity.class));

            }
        });
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), SearchActivity.class));
            }
        });
        homeActionBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), BookActivity.class));
            }
        });
        editQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), RaiseQuestionActivity.class));
            }
        });
    }

    private void getQuestions() {
        Log.d("TAG", "getQuestions: " + questionShowBeans);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                questionShowBeans.clear();
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
                    questionShowBeans.add(questionShowBean);

                }
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (questionShowBeans.isEmpty()) {
                            emptyView.setVisibility(View.VISIBLE);
                            shareRecycler.setVisibility(View.GONE);
                        } else {
                            emptyView.setVisibility(View.GONE);
                            shareRecycler.setVisibility(View.VISIBLE);
                        }
                    }
                });
                handler.sendEmptyMessage(0);
            }
        });
    }

    private void getFollowedShare() {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                realData.clear();
                List<ShareBeanXF> tempDate = new ArrayList<>();
                List<FollowBean> followBeans = DBCreator.getFollowDao().queryFollowByBId(App.user.id);
                for (FollowBean followBean : followBeans) {
                    List<ShareBeanXF> shareBeanXFS = DBCreator.getShareDao().queryByUser(followBean.aId);
                    if (shareBeanXFS != null) {
                        tempDate.addAll(shareBeanXFS);
                    }
                }
                for (int i = tempDate.size() - 1; i >= 0; i--) {
                    ShareBeanXF shareBeanXF = tempDate.get(i);
                    DoShareBean doShareBean = new DoShareBean();
                    doShareBean.authorId = shareBeanXF.authorId;
                    doShareBean.authorNickName = shareBeanXF.authorNickName;
                    doShareBean.content = shareBeanXF.content;
                    doShareBean.shareId = shareBeanXF.id;
                    doShareBean.picPaths = shareBeanXF.picPaths;
                    doShareBean.authorIcon = shareBeanXF.authorIcon;
                    List<FollowBean> checkFollowBeans = DBCreator.getFollowDao().queryFollowByAId(doShareBean.authorId);
                    for (FollowBean followBean : checkFollowBeans) {
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
                    realData.add(doShareBean);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (realData.isEmpty()) {
                            emptyView.setVisibility(View.VISIBLE);
                            shareRecycler.setVisibility(View.GONE);
                        } else {
                            emptyView.setVisibility(View.GONE);
                            shareRecycler.setVisibility(View.VISIBLE);
                        }
                    }
                });
                handler.sendEmptyMessage(0);
            }
        });

    }

    @Override
    protected void findViewsById(View view) {
        EventBus.getDefault().register(this);
        homeActionTest = view.findViewById(R.id.homeActionTest);
        homeActionBook = view.findViewById(R.id.homeActionBook);
        homeActionChat = view.findViewById(R.id.homeActionChat);
        homeActionFM = view.findViewById(R.id.homeActionFM);
        editQuestion = view.findViewById(R.id.editQuestion);
        homeActionShare = view.findViewById(R.id.homeActionShare);
        tabLayout = view.findViewById(R.id.tabLayout);
        searchBar = view.findViewById(R.id.searchBar);
        emptyView = view.findViewById(R.id.emptyView);

        shareRecycler = view.findViewById(R.id.shareRecycler);
        slider = view.findViewById(R.id.slider);


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareSuccessEvent(ShareSuccessEvent event) {
        if (currentTab == 0) {
            getAllShare();
        } else if (currentTab == 1) {
            getFollowedShare();
        } else if (currentTab == 2) {
            getQuestions();
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
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
