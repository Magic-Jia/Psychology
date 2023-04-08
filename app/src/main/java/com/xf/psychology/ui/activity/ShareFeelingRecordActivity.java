package com.xf.psychology.ui.activity;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.adapter.HappyShareAdapter;
import com.xf.psychology.base.BaseActivity;
import com.xf.psychology.bean.DoShareBean;
import com.xf.psychology.bean.FollowBean;
import com.xf.psychology.bean.ShareBeanXF;
import com.xf.psychology.bean.ShareCommentBean;
import com.xf.psychology.db.DBCreator;

import java.util.ArrayList;
import java.util.List;

public class ShareFeelingRecordActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private List<DoShareBean> shareData = new ArrayList<>();
    private HappyShareAdapter adapter = new HappyShareAdapter(shareData);
    private View emptyView;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        List<ShareBeanXF> shareBeanXFS = DBCreator.getShareDao().queryByUser(App.user.id);
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
            shareData.add(doShareBean);
        }
        adapter.setCanDel(true);
        recyclerView.setAdapter(adapter);
        if (shareData.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share_feeling_record;
    }

    @Override
    protected void findViewsById() {
        recyclerView = findViewById(R.id.recyclerView);
        emptyView = findViewById(R.id.emptyView);
    }
}
