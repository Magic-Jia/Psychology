package com.xf.psychology.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        findViewsById(view);
        initListener();
        initData();
        return view;
    }

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void findViewsById(View view);

    protected abstract int getLayoutId();
}
