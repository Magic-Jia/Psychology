package com.xf.psychology.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.adapter.CommonAdapter;
import com.xf.psychology.adapter.ViewHolder;
import com.xf.psychology.base.BaseFragment;
import com.xf.psychology.bean.FollowBean;
import com.xf.psychology.db.DBCreator;

import java.util.ArrayList;
import java.util.List;


public class ShowFollowsFragment extends BaseFragment {

    private RecyclerView recycler;

    private List<FollowBean> users = new ArrayList<>();

    private CommonAdapter<FollowBean> adapter = new CommonAdapter<FollowBean>(R.layout.item_user, users) {
        @Override
        public void bind(ViewHolder holder, FollowBean user, int position) {
            holder.setText(R.id.user_name, user.aNickName);
            holder.setText(R.id.follow,"已关注");
            if(DBCreator.getFollowDao().queryFollowByABId(App.user.id,user.aId)!=null){
                holder.setText(R.id.follow,"互相关注");
            }
            ImageView view = holder.getView(R.id.user_icon);
            Glide.with(holder.itemView).load(user.aIconPath).into(view);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    };

    public ShowFollowsFragment(){

    }

    public static ShowFollowsFragment newInstance() {
        Bundle args = new Bundle();
        ShowFollowsFragment fragment = new ShowFollowsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    protected void initData() {
        users.addAll(DBCreator.getFollowDao().queryFollowByBId(App.user.id));
        recycler.setAdapter(adapter);
    }

    protected void initListener() {

    }

    protected void findViewsById(View view) {
        recycler = view.findViewById(R.id.recycler);
    }

    protected int getLayoutId() {
        return R.layout.fragment_show_fans;
    }
}