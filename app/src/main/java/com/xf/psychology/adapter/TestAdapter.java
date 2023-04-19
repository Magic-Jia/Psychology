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
        TestBean testBean = data.get(position);
        Log.d("TAG", "onBindViewHolder: " + testBean + "  position->" + position);
        holder.question.setText(testBean.question);
        holder.btn1.setText(testBean.A);
        holder.btn2.setText(testBean.B);
        holder.btn3.setText(testBean.C);
        holder.btn4.setText(testBean.D);
        holder.btn5.setText(testBean.E);
        holder.answerSelect.setOnCheckedChangeListener(null);
        holder.answerSelect.clearCheck();
        if (testBean.score != -1) {
            if (testBean.score == 1) {
                holder.answerSelect.check(R.id.btn1);
            } else if (testBean.score == 2) {
                holder.answerSelect.check(R.id.btn2);
            } else if (testBean.score == 3) {
                holder.answerSelect.check(R.id.btn3);
            } else if (testBean.score == 4) {
                holder.answerSelect.check(R.id.btn4);
            } else if (testBean.score == 5) {
                holder.answerSelect.check(R.id.btn5);
            }
        }
        holder.answerSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn1:
                        testBean.score = 1;
                        break;
                    case R.id.btn2:
                        testBean.score = 2;
                        break;
                    case R.id.btn3:
                        testBean.score = 3;
                        break;
                    case R.id.btn4:
                        testBean.score = 4;
                        break;
                    case R.id.btn5:
                        testBean.score = 5;
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
