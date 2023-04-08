package com.xf.psychology.ui.activity;

import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xf.psychology.R;
import com.xf.psychology.adapter.CommonAdapter;
import com.xf.psychology.adapter.ViewHolder;
import com.xf.psychology.base.BaseActivity;
import com.xf.psychology.bean.AnswerBean;
import com.xf.psychology.bean.QuestionShowBean;
import com.xf.psychology.db.DBCreator;
import com.xf.psychology.event.AnswerEvent;
import com.xf.psychology.util.GlideUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuestionDetailActivity extends BaseActivity {
    private ViewPager2 pager2;
    private View emptyView;
    private CircleImageView userIcon;
    private TextView userName;
    private TextView timeTv;
    private TextView questionTv;
    private TextView answerTv;
    private List<AnswerBean> answerData = new ArrayList<>();
    private CommonAdapter<AnswerBean> answerBeanCommonAdapter = new CommonAdapter<AnswerBean>(R.layout.item_answer, answerData) {
        @Override
        public void bind(ViewHolder holder, AnswerBean answerBean, int position) {
            CircleImageView view = holder.getView(R.id.userIcon);
            GlideUtil.load(view,answerBean.answererIconPath);
            holder.setText(R.id.userName, answerBean.answererNickName);
            holder.setText(R.id.answerTv, answerBean.answer);
            holder.setText(R.id.timeTv, answerBean.time + "回答了");
            if (position == answerData.size() - 1) {
                holder.setText(R.id.nextAnswer, "没有更多回答了");
                holder.getView(R.id.icBottom).setVisibility(View.GONE);
            } else {
                holder.setText(R.id.nextAnswer, "下一条回答");
                holder.getView(R.id.icBottom).setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void initListener() {

    }

    private QuestionShowBean questionShowBean;

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        questionShowBean = (QuestionShowBean) getIntent().getSerializableExtra("questionShowBean");
        userName.setText(questionShowBean.raiserNickName);
        timeTv.setText(questionShowBean.time);
        questionTv.setText(questionShowBean.question);
        pager2.setAdapter(answerBeanCommonAdapter);

        GlideUtil.load(userIcon,questionShowBean.raiserIcon);
        answerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuestionDetailActivity.this, AnswerActivity.class).putExtra("questionShowBean", questionShowBean));
            }
        });
        List<AnswerBean> answerBeans = DBCreator.getAnswerDao().queryByQuestionId(questionShowBean.questionId);
        if (answerBeans != null && !answerBeans.isEmpty()) {
            answerData.addAll(answerBeans);
            pager2.setAdapter(answerBeanCommonAdapter);
        } else {
            pager2.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_question_detail;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void answerSuccess(AnswerEvent event) {
        List<AnswerBean> answerBeans = DBCreator.getAnswerDao().queryByQuestionId(questionShowBean.questionId);
        if (answerBeans != null && !answerBeans.isEmpty()) {
            answerData.clear();
            answerData.addAll(answerBeans);
            answerBeanCommonAdapter.notifyDataSetChanged();
        } else {
            pager2.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void findViewsById() {
        pager2 = findViewById(R.id.pager2);
        emptyView = findViewById(R.id.emptyView);
        userIcon = findViewById(R.id.userIcon);
        userName = findViewById(R.id.userName);
        timeTv = findViewById(R.id.timeTv);
        questionTv = findViewById(R.id.questionTv);
        answerTv = findViewById(R.id.answerTv);

    }
}