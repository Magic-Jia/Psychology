package com.xf.psychology.adapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


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

}
