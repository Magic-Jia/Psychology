package com.xf.psychology.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xf.psychology.R;


public class HappyShareViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView content;
    public TextView time;
    public TextView likes;
    public ImageView likeImg;
    public ImageView img1;
    public ImageView img2;
    public TextView msgTv;
    public View imageParent;
    public TextView followTv;
    public TextView del;

    public HappyShareViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.userName);
        content = itemView.findViewById(R.id.contentTv);
        time = itemView.findViewById(R.id.timeTv);
        likes = itemView.findViewById(R.id.likesTv);
        likeImg = itemView.findViewById(R.id.likeImg);
        msgTv = itemView.findViewById(R.id.msgTv);
        followTv = itemView.findViewById(R.id.followTv);
        img1 = itemView.findViewById(R.id.img1);
        img2 = itemView.findViewById(R.id.img2);
        imageParent  = itemView.findViewById(R.id.imageParent);

        del = itemView.findViewById(R.id.del);
    }
}
