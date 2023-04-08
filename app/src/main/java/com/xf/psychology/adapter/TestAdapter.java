package com.xf.psychology.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xf.psychology.R;
import com.xf.psychology.bean.TestBean;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestViewHolder> {
    private List<TestBean> data;

    public TestAdapter(List<TestBean> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test, parent, false);
        TestViewHolder viewHolder = new TestViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        TestBean questionBean = data.get(position);
        Log.d("TAG", "onBindViewHolder: " + questionBean + "  position->" + position);
        holder.question.setText(questionBean.question);
        holder.answerSelect.setOnCheckedChangeListener(null);
        holder.answerSelect.clearCheck();
        if (questionBean.score != -1) {
            if (questionBean.score == 0) {
                holder.answerSelect.check(R.id.btn3);
            } else if (questionBean.score == 1) {
                holder.answerSelect.check(R.id.btn2);
            } else if (questionBean.score == 2) {
                holder.answerSelect.check(R.id.btn1);
            }
        }
        holder.answerSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn1:
                        questionBean.score = 2;
                        break;
                    case R.id.btn2:
                        questionBean.score = 1;
                        break;
                    case R.id.btn3:
                        questionBean.score = 0;
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
