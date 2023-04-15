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
    // 声明变量
    private EditText edit;           // 输入框
    private TextView sendBtn;        // 发送按钮
    private View bottomView;         // 底部视图
    private RecyclerView chatRecycler;  // 聊天消息列表
    private List<ChatBean> chatBeans = new ArrayList<>();  // 聊天消息列表数据源
    private CommonAdapter<ChatBean> adapter = new CommonAdapter<ChatBean>(R.layout.item_chat, chatBeans) {
        // 适配器，用于将数据绑定到聊天消息列表中
        @Override
        public void bind(ViewHolder holder, ChatBean chatBean, int position) {  // 将数据绑定到列表项
            holder.setText(R.id.messageTv, chatBean.message);  // 设置消息文本
            holder.setText(R.id.timeTv, chatBean.messageTime);  // 设置消息时间
            ImageView view = holder.getView(R.id.userIcon);  // 获取头像视图
            GlideUtil.load(view, chatBean.sendIconPath);  // 使用 Glide 加载头像图片
        }
    };
    private boolean isEx = true;  // 声明一个 boolean 类型的变量，但似乎没有使用

    // 初始化事件监听器
    @Override
    protected void initListener() {
        sendBtn.setOnClickListener(new View.OnClickListener() {  // 点击发送按钮时触发
            @Override
            public void onClick(View v) {
                String message = edit.getText().toString().trim();  // 获取输入框中的文本
                if (message.isEmpty()) {  // 如果文本为空则直接返回
                    return;
                }
                ChatBean chatBean = new ChatBean();  // 新建一个聊天消息对象
                chatBean.message = message;  // 设置聊天消息的内容
                chatBean.sendId = App.user.id;  // 设置发送者的 ID
                chatBean.messageTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());  // 设置聊天消息的发送时间
                chatBean.sendIconPath = App.user.iconPath;  // 设置发送者的头像路径
                DBCreator.getChatDao().insert(chatBean);  // 将聊天消息插入到数据库中
                chatBeans.add(chatBean);  // 将聊天消息添加到数据源中
                adapter.notifyItemInserted(chatBeans.size() - 1);  // 刷新列表项
                edit.setText("");  // 清空输入框
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  // 获取输入法管理器
                imm.hideSoftInputFromWindow(bottomView.getWindowToken(), 0);  // 隐藏输入法
                chatRecycler.smoothScrollToPosition(chatBeans.size() - 1);  // 滚动到最新的聊天消息
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