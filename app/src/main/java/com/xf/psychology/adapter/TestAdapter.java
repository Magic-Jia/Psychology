package com.xf.psychology.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xf.psychology.R;
import com.xf.psychology.bean.TestBean;
import com.xf.psychology.ui.activity.TestActivity;

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
        holder.btn1.setText(questionBean.A);
        holder.btn2.setText(questionBean.B);
        holder.btn3.setText(questionBean.C);
        holder.btn4.setText(questionBean.D);
        holder.btn5.setText(questionBean.E);
        holder.answerSelect.setOnCheckedChangeListener(null);
        holder.answerSelect.clearCheck();
        if (questionBean.score != -1) {
            if (questionBean.score == 1) {
                holder.answerSelect.check(R.id.btn1);
            } else if (questionBean.score == 2) {
                holder.answerSelect.check(R.id.btn2);
            } else if (questionBean.score == 3) {
                holder.answerSelect.check(R.id.btn3);
            } else if (questionBean.score == 4) {
                holder.answerSelect.check(R.id.btn4);
            } else if (questionBean.score == 5) {
                holder.answerSelect.check(R.id.btn5);
            }
        }
        holder.answerSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn1:
                        questionBean.score = 1;
                        break;
                    case R.id.btn2:
                        questionBean.score = 2;
                        break;
                    case R.id.btn3:
                        questionBean.score = 3;
                        break;
                    case R.id.btn4:
                        questionBean.score = 4;
                        break;
                    case R.id.btn5:
                        questionBean.score = 5;
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
