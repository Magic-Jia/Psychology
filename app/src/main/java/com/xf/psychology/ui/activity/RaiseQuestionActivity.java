package com.xf.psychology.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.base.BaseActivity;
import com.xf.psychology.bean.QuestionBean;
import com.xf.psychology.db.DBCreator;
import com.xf.psychology.event.ShareSuccessEvent;
import com.xf.psychology.view.LimitEditText;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RaiseQuestionActivity extends BaseActivity {
    private LimitEditText editText;
    private EditText detailEdit;
    private TextView submit;


    @Override
    protected void initListener() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = editText.getText().toString();
                String detail = detailEdit.getText().toString();
                if (question.length() < 5) {
                    toast("请最少输入五个字");
                    return;
                }
                if (editText.isMax()) {
                    toast("最多输入25个字符哟");
                    return;
                }
                if (detail.isEmpty()) {
                    toast("请详细说说你的问题哦");
                    return;
                }
                QuestionBean questionBean = new QuestionBean();
                questionBean.question = question;
                questionBean.detail = detail;
                questionBean.raiserId = App.user.id;
                questionBean.raiserIcon = App.user.iconPath;
                questionBean.raiserNickName = App.user.name;
                questionBean.time =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                DBCreator.getQuestionDao().insert(questionBean);
                EventBus.getDefault().post(new ShareSuccessEvent());
                toast("提问成功");
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_raise_question;
    }

    @Override
    protected void findViewsById() {
        editText = findViewById(R.id.edit);
        submit = findViewById(R.id.submit);
        detailEdit = findViewById(R.id.detailEdit);
    }
}