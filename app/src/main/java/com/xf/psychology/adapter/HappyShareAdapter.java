package com.xf.psychology.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.bean.DoShareBean;
import com.xf.psychology.bean.FollowBean;
import com.xf.psychology.bean.ShareBeanXF;
import com.xf.psychology.bean.ShareLikes;
import com.xf.psychology.db.DBCreator;
import com.xf.psychology.event.DelEvent;
import com.xf.psychology.event.ShareSuccessEvent;
import com.xf.psychology.ui.activity.ShareDetailActivity;
import com.xf.psychology.util.GlideUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class HappyShareAdapter extends RecyclerView.Adapter<HappyShareViewHolder> {
    private List<DoShareBean> data;

    public HappyShareAdapter(List<DoShareBean> data) {
        this.data = data;
    }

    private boolean isCamDel = false;

    public void setCanDel(boolean isCamDel) {
        this.isCamDel = isCamDel;
    }

    @NonNull
    @Override
    public HappyShareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_share_1, parent, false);
        HappyShareViewHolder viewHolder = new HappyShareViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HappyShareViewHolder holder, int position) {
        DoShareBean doShare = data.get(position);
        holder.name.setText(doShare.authorNickName);
        holder.content.setText(doShare.content);
        holder.time.setText(doShare.time);
        holder.likes.setText(doShare.likes + "");
        holder.msgTv.setText(doShare.messages + "");
        CircleImageView view = holder.itemView.findViewById(R.id.userIcon);
        GlideUtil.load(view, doShare.authorIcon);
        holder.itemView.setOnLongClickListener(view1 -> {
            if (isCamDel) {
                new AlertDialog.Builder(holder.itemView.getContext()).setItems(new CharSequence[]{"删除", "返回"},
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) {
                                    DBCreator.getShareDao().delete(doShare.shareId);
                                    data.remove(position);
                                    notifyDataSetChanged();

                                }
                            }
                        }).show();
            }
            return true;
        });
        if (doShare.authorId == App.user.id) {
            holder.followTv.setVisibility(View.GONE);
        } else {
            if (doShare.isFollow) {
                holder.followTv.setText("已关注");
            } else {
                holder.followTv.setText("+ 关注");
            }
        }

        holder.followTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doShare.isFollow) {
                    doShare.isFollow = false;
                    FollowBean followBean = DBCreator.getFollowDao().queryFollowByABId(doShare.authorId, App.user.id);
                    DBCreator.getFollowDao().del(followBean);
                    holder.followTv.setText("+ 关注");
                } else {
                    doShare.isFollow = true;
                    FollowBean followBean = new FollowBean();
                    followBean.aId = doShare.authorId;
                    followBean.aNickName = doShare.authorNickName;
                    followBean.aIconPath = doShare.authorIcon;
                    followBean.bId = App.user.id;
                    followBean.bNickName = App.user.name;
                    followBean.bIconPath = App.user.name;
                    DBCreator.getFollowDao().insert(followBean);
                    holder.followTv.setText("已关注");
                }
                notifyDataSetChanged();
            }
        });
        if (doShare.isLike) {
            holder.likeImg.setImageResource(R.drawable.ic_like);
            holder.likes.setTextColor(holder.likes.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.likeImg.setImageResource(R.drawable.ic_like_un);
            holder.likes.setTextColor(holder.likes.getResources().getColor(R.color.bfbfbf));
        }
        if (doShare.picPaths == null || doShare.picPaths.isEmpty()) {
            holder.imageParent.setVisibility(View.GONE);
        } else {
            Glide.with(holder.img1).load(doShare.picPaths.get(0)).into(holder.img1);
            holder.img2.setVisibility(View.GONE);
            if (doShare.picPaths.size() > 1) {
                holder.img2.setVisibility(View.VISIBLE);
                Glide.with(holder.img2).load(doShare.picPaths.get(1)).into(holder.img2);
            }
        }
        holder.likeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doShare.isLike) {
                    doShare.isLike = false;
                    doShare.likes = doShare.likes - 1;
                    ShareLikes shareLikes = DBCreator.getShareLikesDao().queryByShareId(doShare.shareId, App.user.phone);
                    DBCreator.getShareLikesDao().del(shareLikes);
                } else {
                    doShare.isLike = true;
                    ShareLikes shareLikes = new ShareLikes();
                    shareLikes.shareId = doShare.shareId;
                    shareLikes.user = App.user.phone;
                    doShare.likes = doShare.likes + 1;
                    DBCreator.getShareLikesDao().insert(shareLikes);
                }
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), ShareDetailActivity.class).putExtra("shareBean", doShare));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
