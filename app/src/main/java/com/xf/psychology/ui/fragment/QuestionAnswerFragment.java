package com.xf.psychology.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.adapter.CommonAdapter;
import com.xf.psychology.adapter.ViewHolder;
import com.xf.psychology.base.BaseFragment;
import com.xf.psychology.bean.AnswerBean;
import com.xf.psychology.bean.FollowBean;
import com.xf.psychology.bean.QuestionBean;
import com.xf.psychology.bean.QuestionShowBean;
import com.xf.psychology.db.DBCreator;
import com.xf.psychology.ui.activity.QuestionDetailActivity;
import com.xf.psychology.ui.activity.RaiseQuestionActivity;
import com.xf.psychology.util.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuestionAnswerFragment extends BaseFragment {

    private View editQuestion;
    private RecyclerView recycler;
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
                        followBean.bIconPath = App.user.iconPath;
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

    public static QuestionAnswerFragment newInstance() {
        Bundle args = new Bundle();
        QuestionAnswerFragment fragment = new QuestionAnswerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        getQuestions();
        recycler.setAdapter(questionShowBeanCommonAdapter);
    }

    @Override
    protected void initListener() {
        editQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), RaiseQuestionActivity.class));
            }
        });
    }

    @Override
    protected void findViewsById(View view) {
        recycler=view.findViewById(R.id.recycler);
        editQuestion = view.findViewById(R.id.editQuestion);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_question_answer;
    }

    private void getQuestions() {
        Log.d("TAG", "getQuestions: " + questionShowBeans);
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
    }
    public void onResume() {
        super.onResume();
        initData();
        // 更新数据和视图
    }
}