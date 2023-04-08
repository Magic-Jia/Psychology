package com.xf.psychology.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xf.psychology.R;


class TestViewHolder extends RecyclerView.ViewHolder {
    public TextView question;
    public RadioButton btn1;
    public RadioButton btn2;
    public RadioButton btn3;
    public RadioGroup answerSelect;
    public TestViewHolder(@NonNull View itemView) {
        super(itemView);
        question = itemView.findViewById(R.id.question);
        answerSelect = itemView.findViewById(R.id.answerSelect);
        btn1 = itemView.findViewById(R.id.btn1);
        btn2 = itemView.findViewById(R.id.btn2);
        btn3 = itemView.findViewById(R.id.btn3);
    }

}
