package com.xf.psychology.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.adapter.CommonAdapter;
import com.xf.psychology.adapter.ViewHolder;
import com.xf.psychology.base.BaseActivity;
import com.xf.psychology.bean.ChatBean;
import com.xf.psychology.db.DBCreator;
import com.xf.psychology.util.GlideUtil;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class OnlineChatActivity extends BaseActivity {
    private EditText edit;
    private TextView sendBtn;
    private View bottomView;
    private RecyclerView chatRecycler;
    private List<ChatBean> chatBeans = new ArrayList<>();
    private CommonAdapter<ChatBean> adapter = new CommonAdapter<ChatBean>(R.layout.item_chat, chatBeans) {
        @Override
        public void bind(ViewHolder holder, ChatBean chatBean, int position) {
            holder.setText(R.id.messageTv, chatBean.message);
            holder.setText(R.id.timeTv, chatBean.messageTime);
            ImageView view = holder.getView(R.id.userIcon);
            GlideUtil.load(view, chatBean.sendIconPath);
        }
    };
    private boolean isEx = true;

    @Override
    protected void initListener() {
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = edit.getText().toString().trim();
                if (message.isEmpty()) {
                    return;
                }
                ChatBean chatBean = new ChatBean();
                chatBean.message = message;
                chatBean.sendId = App.user.id;
                chatBean.messageTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                chatBean.sendIconPath = App.user.iconPath;
                DBCreator.getChatDao().insert(chatBean);
                chatBeans.add(chatBean);
                adapter.notifyItemInserted(chatBeans.size() - 1);
                edit.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(bottomView.getWindowToken(), 0);
                chatRecycler.smoothScrollToPosition(chatBeans.size() - 1);
//                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) bottomView.getLayoutParams();
//                layoutParams.bottomMargin = 0;
            }
        });
    }

    @Override
    protected void initData() {
        List<ChatBean> queryData = DBCreator.getChatDao().queryBySendId(App.user.id);
        chatBeans.addAll(queryData);
        chatRecycler.setAdapter(adapter);
        chatRecycler.scrollToPosition(chatBeans.size() - 1);
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_online_chat;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect rect = new Rect();
        bottomView.getGlobalVisibleRect(rect);
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            if (!rect.contains((int) ev.getX(), (int) ev.getY())) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(bottomView.getWindowToken(), 0);
//                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) bottomView.getLayoutParams();
//                layoutParams.bottomMargin = 0;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void findViewsById() {
        edit = findViewById(R.id.edit);
        sendBtn = findViewById(R.id.sendBtn);
        chatRecycler = findViewById(R.id.chatRecycler);
        bottomView = findViewById(R.id.bottomView);
    }
}