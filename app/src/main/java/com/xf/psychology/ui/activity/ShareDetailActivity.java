package com.xf.psychology.ui.activity;

import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.adapter.CommonAdapter;
import com.xf.psychology.adapter.ViewHolder;
import com.xf.psychology.base.BaseActivity;
import com.xf.psychology.bean.DoShareBean;
import com.xf.psychology.bean.FollowBean;
import com.xf.psychology.bean.ShareCommentBean;
import com.xf.psychology.bean.ShareLikes;
import com.xf.psychology.db.DBCreator;
import com.xf.psychology.event.ShareSuccessEvent;
import com.xf.psychology.util.GlideUtil;
import com.xf.psychology.util.SomeUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShareDetailActivity extends BaseActivity {
    private CircleImageView userIcon;
    private TextView userName;
    private TextView followTv;
    private TextView timeTv;

    private CircleImageView titleUserIcon;
    private TextView titleTimeTv;
    private TextView titleFollowTv;
    private TextView titleUserName;

    private TextView contentTv;
    private TextView likesTv;
    private TextView titleTv;
    private TextView msgTv;
    private TextView emptyView;
    private TextView sendBtn;
    private EditText commentEdit;
    private ImageView likeImg;
    private RecyclerView imgRecycler;
    private RecyclerView commentRecycler;
    private View userInfo;
    private View titleUserInfo;
    private List<String> imgPath = new ArrayList<>();
    private int currentReplyId;
    private String currentReplyNickName;


    private NestedScrollView scrollView;
    private CommonAdapter<String> imaRecAdapter = new CommonAdapter<String>(R.layout.item_image, imgPath) {
        @Override
        public void bind(ViewHolder holder, String s, int position) {
            ImageView view = holder.getView(R.id.image);
            Glide.with(view).load(s).into(view);
        }
    };

    private List<ShareCommentBean> commentBeans = new ArrayList<>();
    private CommonAdapter<ShareCommentBean> commentAdapter = new CommonAdapter<ShareCommentBean>(R.layout.item_comment, commentBeans) {
        @Override
        public void bind(ViewHolder holder, ShareCommentBean s, int position) {
            holder.setText(R.id.userName, s.initiatorNickName);
            holder.setText(R.id.timeTv, s.time);
            if (s.toId == 0) {
                holder.getView(R.id.reply).setVisibility(View.GONE);
            } else {
                holder.getView(R.id.reply).setVisibility(View.VISIBLE);
                holder.setText(R.id.toNickName, s.toNickName);
            }
            holder.setText(R.id.comment, s.comment);
            CircleImageView view = holder.getView(R.id.userIcon);
            GlideUtil.load(view,s.initiatorIcon);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentEdit.setHint("回复 @" + s.initiatorNickName + ":");
                    currentReplyId = s.initiatorId;
                    currentReplyNickName = s.initiatorNickName;
                }
            });
        }
    };

    @Override
    protected void findViewsById() {
        userIcon = findViewById(R.id.userIcon);
        titleTv = findViewById(R.id.titleTv);
        likeImg = findViewById(R.id.likeImg);
        userName = findViewById(R.id.userName);
        followTv = findViewById(R.id.followTv);
        contentTv = findViewById(R.id.contentTv);
        timeTv = findViewById(R.id.timeTv);
        likesTv = findViewById(R.id.likesTv);
        msgTv = findViewById(R.id.msgTv);
        emptyView = findViewById(R.id.emptyView);
        sendBtn = findViewById(R.id.sendBtn);
        commentEdit = findViewById(R.id.commentEdit);
        imgRecycler = findViewById(R.id.imgRecycler);
        userInfo = findViewById(R.id.userInfo);
        titleUserInfo = findViewById(R.id.titleUserInfo);
        commentRecycler = findViewById(R.id.commentRecycler);
        scrollView = findViewById(R.id.scrollView);

        titleTimeTv = findViewById(R.id.titleTimeTv);
        titleUserIcon = findViewById(R.id.titleUserIcon);
        titleFollowTv = findViewById(R.id.titleFollowTv);
        titleUserName = findViewById(R.id.titleUserName);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initListener() {
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY >= userInfo.getBottom()) {
                    titleUserInfo.setVisibility(View.VISIBLE);
                    titleTv.setVisibility(View.GONE);
                } else {
                    titleUserInfo.setVisibility(View.GONE);
                    titleTv.setVisibility(View.VISIBLE);
                }
            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentEdit.getText().toString().trim();
                if (comment.isEmpty()) {
                    return;
                }
                ShareCommentBean shareCommentBean = new ShareCommentBean();
                if (currentReplyId == shareBean.authorId) {
                    shareCommentBean.toId = 0;
                } else {
                    shareCommentBean.toId = currentReplyId;
                    shareCommentBean.toNickName = currentReplyNickName;
                }
                shareCommentBean.time = SomeUtil.getTime();
                shareCommentBean.initiatorNickName = App.user.name;
                shareCommentBean.initiatorId = App.user.id;
                shareCommentBean.comment = comment;
                shareCommentBean.shareId = shareBean.shareId;
                shareCommentBean.initiatorIcon = App.user.iconPath;
                DBCreator.getShareCommentDao().insert(shareCommentBean);
                commentBeans.clear();
                List<ShareCommentBean> shareCommentBeans = DBCreator.getShareCommentDao().queryByShareId(shareBean.shareId);
                commentBeans.addAll(shareCommentBeans);
                Collections.reverse(commentBeans);
                msgTv.setText(commentBeans.size() + "");
                shareBean.messages = commentBeans.size();
                commentAdapter.notifyDataSetChanged();
                commentEdit.setText("");
                emptyView.setVisibility(View.GONE);
                commentRecycler.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                EventBus.getDefault().post(new ShareSuccessEvent());
            }
        });
        contentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentEdit.setHint("回复 @" + shareBean.authorNickName + ":");
                currentReplyId = shareBean.authorId;
                currentReplyNickName = shareBean.authorNickName;
            }
        });
        View.OnClickListener followClickListener = v -> {
            if (shareBean.isFollow) {
                shareBean.isFollow = false;
                FollowBean followBean = DBCreator.getFollowDao().queryFollowByABId(shareBean.authorId, App.user.id);
                DBCreator.getFollowDao().del(followBean);
                followTv.setText("+ 关注");
                titleFollowTv.setText("+ 关注");
            } else {
                shareBean.isFollow = true;
                FollowBean followBean = new FollowBean();
                followBean.aId = shareBean.authorId;
                followBean.aNickName = shareBean.authorNickName;
                followBean.aIconPath = shareBean.authorIcon;
                followBean.bId = App.user.id;
                followBean.bNickName = App.user.name;
                followBean.bIconPath = App.user.name;
                DBCreator.getFollowDao().insert(followBean);
                followTv.setText("已关注");
                titleFollowTv.setText("已关注");
            }
            EventBus.getDefault().post(new ShareSuccessEvent());
        };

        followTv.setOnClickListener(followClickListener);
        titleFollowTv.setOnClickListener(followClickListener);

        likeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shareBean.isLike) {
                    shareBean.isLike = false;
                    shareBean.likes = shareBean.likes - 1;
                    ShareLikes shareLikes = DBCreator.getShareLikesDao().queryByShareId(shareBean.shareId, App.user.phone);
                    DBCreator.getShareLikesDao().del(shareLikes);
                } else {
                    shareBean.isLike = true;
                    ShareLikes shareLikes = new ShareLikes();
                    shareLikes.shareId = shareBean.shareId;
                    shareLikes.user = App.user.phone;
                    shareBean.likes = shareBean.likes + 1;
                    DBCreator.getShareLikesDao().insert(shareLikes);
                }
                if (shareBean.isLike) {

                    likeImg.setImageResource(R.drawable.ic_like);
                    likesTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    likeImg.setImageResource(R.drawable.ic_like_un);
                    likesTv.setTextColor(getResources().getColor(R.color.bfbfbf));
                }
                likesTv.setText(shareBean.likes + "");
                EventBus.getDefault().post(new ShareSuccessEvent());
            }
        });

    }

    private DoShareBean shareBean;

    @Override
    protected void initData() {
        StatusBarUtil.setTranslucent(this);
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.colorPrimary));
        shareBean = (DoShareBean) getIntent().getSerializableExtra("shareBean");
        if (shareBean != null) {

            GlideUtil.load(titleUserIcon, shareBean.authorIcon);
            GlideUtil.load(userIcon, shareBean.authorIcon);
            commentEdit.setHint("回复 @" + shareBean.authorNickName + ":");
            userName.setText(shareBean.authorNickName);
            titleUserName.setText(shareBean.authorNickName);
            contentTv.setText(shareBean.content);
            likesTv.setText(shareBean.likes + "");

            if (shareBean.isFollow) {
                followTv.setText("已关注");
                titleFollowTv.setText("已关注");
            } else {
                followTv.setText("+ 关注");
                titleFollowTv.setText("+ 关注");
            }
            if (shareBean.isLike) {
                likeImg.setImageResource(R.drawable.ic_like);
                likesTv.setTextColor(getResources().getColor(R.color.colorPrimary));
            } else {
                likeImg.setImageResource(R.drawable.ic_like_un);
                likesTv.setTextColor(getResources().getColor(R.color.bfbfbf));
            }
            timeTv.setText(shareBean.time);
            titleTimeTv.setText(shareBean.time);
            currentReplyId = shareBean.authorId;
            currentReplyNickName = shareBean.authorNickName;
            if (shareBean.picPaths != null && !shareBean.picPaths.isEmpty()) {
                imgPath.addAll(shareBean.picPaths);
                imgRecycler.setAdapter(imaRecAdapter);
            }
            List<ShareCommentBean> shareCommentBeans = DBCreator.getShareCommentDao().queryByShareId(shareBean.shareId);
            if (shareCommentBeans.size() == 0) {
                emptyView.setVisibility(View.VISIBLE);
                commentRecycler.setVisibility(View.GONE);
                msgTv.setText("0");
            } else {
                emptyView.setVisibility(View.GONE);
                commentRecycler.setVisibility(View.VISIBLE);
                commentBeans.addAll(shareCommentBeans);
                Collections.reverse(commentBeans);
                msgTv.setText(commentBeans.size() + "");
            }
            commentRecycler.setAdapter(commentAdapter);
        }

    }

    @Override
    protected int getLayoutId() {
        return (R.layout.activity_share_detail);
    }


}