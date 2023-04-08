package com.xf.psychology.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.base.BaseActivity;
import com.xf.psychology.bean.AnswerBean;
import com.xf.psychology.bean.QuestionShowBean;
import com.xf.psychology.db.DBCreator;
import com.xf.psychology.event.AnswerEvent;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AnswerActivity extends BaseActivity {
    private TextView submit;
    private EditText edit;

    @Override
    protected void initListener() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = edit.getText().toString().trim();
                if (answer.isEmpty()) {
                    toast("请输入回复内容");
                    return;
                }
                AnswerBean answerBean = new AnswerBean();
                answerBean.answer = answer;
                answerBean.answererId = App.user.id;
                answerBean.answererIconPath = App.user.iconPath;
                answerBean.answererNickName = App.user.name;
                answerBean.questionId = questionShowBean.questionId;
                answerBean.time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                DBCreator.getAnswerDao().insert(answerBean);
                EventBus.getDefault().post(new AnswerEvent());
                toast("回复成功");
                finish();
            }

        });
    }

    QuestionShowBean questionShowBean;

    @Override
    protected void initData() {
        questionShowBean = (QuestionShowBean) getIntent().getSerializableExtra("questionShowBean");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_answer;
    }

    @Override
    protected void findViewsById() {
        submit = findViewById(R.id.submit);
        edit = findViewById(R.id.edit);
    }
}