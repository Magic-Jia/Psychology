package com.xf.psychology.adapter;

import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.xf.psychology.R;


public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        views = new SparseArray<>();
    }

    public <T extends View> T getView(int id) {
        View view = views.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            views.put(id, view);
        }
        return (T) view;
    }

    public ViewHolder setText(int id, String text) {
        TextView view = getView(id);
        view.setText(text);
        return this;
    }

    public ViewHolder setLinearLayoutLeft(int id) {
        LinearLayout view = getView(id);
        view.setGravity(Gravity.LEFT);
        return this;
    }

    public ViewHolder setLinearLayoutRight(int id) {
        LinearLayout view = getView(id);
        view.setGravity(Gravity.RIGHT);
        return this;
    }

    public ViewHolder setIconLeft(int id) {
        ConstraintLayout view = getView(id);
        ConstraintSet set = new ConstraintSet();
        set.clone(view);
        set.connect(R.id.userIcon, ConstraintSet.END, R.id.messageTv, ConstraintSet.START,8);
        set.connect(R.id.messageTv, ConstraintSet.START, R.id.userIcon, ConstraintSet.END);
        set.applyTo(view);
        return this;
    }

    public ViewHolder setIconRight(int id) {
        ConstraintLayout view = getView(id);
        ConstraintSet set = new ConstraintSet();
        set.clone(view);
        set.connect(R.id.messageTv, ConstraintSet.END, R.id.userIcon, ConstraintSet.START,8);
        set.connect(R.id.userIcon, ConstraintSet.START, R.id.messageTv, ConstraintSet.END);
        set.applyTo(view);
        return this;
    }

}
