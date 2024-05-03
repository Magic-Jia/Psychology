package com.xf.psychology.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.adapter.CommonAdapter;
import com.xf.psychology.adapter.HappyShareAdapter;
import com.xf.psychology.adapter.ViewHolder;
import com.xf.psychology.base.BaseActivity;
import com.xf.psychology.base.BaseFragment;
import com.xf.psychology.bean.DoShareBean;
import com.xf.psychology.bean.FollowBean;
import com.xf.psychology.bean.ShareBeanXF;
import com.xf.psychology.bean.ShareCommentBean;
import com.xf.psychology.db.DBCreator;
import com.xf.psychology.event.ShareSuccessEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class FollowFragment extends BaseFragment {

    private RecyclerView recycler;

    private List<DoShareBean> realData = new ArrayList<>();
    private HappyShareAdapter adapter = new HappyShareAdapter(realData);

    public void FollowFragment(){

    }

    public static FollowFragment newInstance() {
        Bundle args = new Bundle();
        FollowFragment fragment = new FollowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    protected void initData() {
        getFollowedShare();
        recycler.setAdapter(adapter);
    }

    protected void initListener() {

    }

    protected void findViewsById(View view) {
        recycler = view.findViewById(R.id.recycler);
    }

    protected int getLayoutId() {
        return R.layout.fragment_follow;
    }


    private void getFollowedShare() {
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
    }

    public void onResume() {
        super.onResume();
        getFollowedShare();
        recycler.setAdapter(adapter);
        // 更新数据和视图
    }
}