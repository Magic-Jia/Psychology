package com.xf.psychology.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.bean.TestRecordXFBean;

import java.util.List;


public class TestRecordAdapter extends RecyclerView.Adapter<TestRecordViewHolder> {
    private List<TestRecordXFBean> data;

    public TestRecordAdapter(List<TestRecordXFBean> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public TestRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_record, parent, false);
        TestRecordViewHolder viewHolder = new TestRecordViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestRecordViewHolder holder, int position) {
        TestRecordXFBean testRecordXFBean = data.get(position);
        holder.contentTv.setText("心理预警提示：" + testRecordXFBean.warningTips);
        holder.nameTv.setText(testRecordXFBean.name);
        holder.timeTv.setText(testRecordXFBean.time);
        holder.scoreTv.setText("(" + testRecordXFBean.score + ")");
        if (testRecordXFBean.score <= 16) {
            holder.stateTv.setText("健康");
            holder.stateTv.setTextColor(App.getContext().getResources().getColor(R.color.health));
        }else if (testRecordXFBean.score<=40){
            holder.stateTv.setText("警告");
            holder.stateTv.setTextColor(App.getContext().getResources().getColor(R.color.warn));

        }else{
            holder.stateTv.setText("危险");
            holder.stateTv.setTextColor(App.getContext().getResources().getColor(R.color.danger));

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
