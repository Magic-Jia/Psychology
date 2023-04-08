package com.xf.psychology.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xf.psychology.R;


class TestRecordViewHolder extends RecyclerView.ViewHolder {
    public TextView nameTv;
    public TextView scoreTv;
    public TextView stateTv;
    public TextView contentTv;
    public TextView timeTv;
    public TestRecordViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTv = itemView.findViewById(R.id.nameTv);
        scoreTv = itemView.findViewById(R.id.scoreTv);
        stateTv = itemView.findViewById(R.id.stateTv);
        contentTv = itemView.findViewById(R.id.contentTv);
        timeTv = itemView.findViewById(R.id.timeTv);
    }
}
