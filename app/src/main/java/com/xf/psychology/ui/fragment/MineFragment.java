package com.xf.psychology.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.base.BaseFragment;
import com.xf.psychology.db.DBCreator;
import com.xf.psychology.event.LoginEvent;
import com.xf.psychology.ui.activity.FansFollowRecordActivity;
import com.xf.psychology.ui.activity.MyBookActivity;
import com.xf.psychology.ui.activity.LoginActivity;
import com.xf.psychology.ui.activity.MyFmActivity;
import com.xf.psychology.ui.activity.ShareFeelingRecordActivity;
import com.xf.psychology.ui.activity.TestRecordActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.SQLException;

import de.hdodenhof.circleimageview.CircleImageView;


public class MineFragment extends BaseFragment {

    private TextView nameTv;
    private TextView phoneTv;
    private TextView logout;
    private CircleImageView headView;

    private LinearLayout testRecordLL;
    private LinearLayout feelingRecordLL;
    private LinearLayout bookLL;
    private LinearLayout fmLL;
    private Button fans;
    private Button follow;


    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        if (App.isLogin()) {
            nameTv.setText(App.user.name);
            phoneTv.setText(App.user.phone);
            Glide.with(this).load(App.user.iconPath).into(headView);
            fans.setText(DBCreator.getFollowDao().queryFollowByAId(App.user.id).size() + "\n"+"粉丝");
            follow.setText(DBCreator.getFollowDao().queryFollowByBId(App.user.id).size() + "\n"+"关注");
        }
    }

    @Override
    protected void initListener() {
        testRecordLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), TestRecordActivity.class));
            }
        });
        feelingRecordLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), ShareFeelingRecordActivity.class));
            }
        });

        bookLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), MyBookActivity.class));
            }
        });
        fmLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), MyFmActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    App.logout();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                startActivity(new Intent(requireActivity(), LoginActivity.class));
                requireActivity().finish();
            }
        });
        nameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit, null);

                new AlertDialog.Builder(getContext()).setView(editText).setPositiveButton("取消", null).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = editText.getText().toString().trim();
                        if (name.isEmpty()) {
                            Toast.makeText(getContext(), "昵称不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        App.user.name = name;
                        DBCreator.getUserDao().updateUser(App.user);
                        nameTv.setText(name);
                        Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                    }
                }).setCancelable(false).show();
            }
        });
        fans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), FansFollowRecordActivity.class));
            }
        });
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), FansFollowRecordActivity.class));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginOkEvent(LoginEvent event) {
    }

    @Override
    protected void findViewsById(View view) {
        nameTv = view.findViewById(R.id.nameTv);

        phoneTv = view.findViewById(R.id.phoneTv);
        testRecordLL = view.findViewById(R.id.testRecordLL);
        feelingRecordLL = view.findViewById(R.id.feelingRecordLL);
        bookLL = view.findViewById(R.id.bookRecordLL);
        headView = view.findViewById(R.id.headView);
        fmLL = view.findViewById(R.id.fmRecordLL);
        fans = view.findViewById(R.id.fans);
        follow = view.findViewById(R.id.follow);

        logout = view.findViewById(R.id.logout);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    public void UpdateData(){
        fans.setText(DBCreator.getFollowDao().queryFollowByAId(App.user.id).size() + "\n"+"粉丝");
        follow.setText(DBCreator.getFollowDao().queryFollowByBId(App.user.id).size() + "\n"+"关注");
    }

    @Override
    public void onResume() {
        super.onResume();
        UpdateData();
        // 更新数据和视图
    }
}
