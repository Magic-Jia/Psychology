package com.xf.psychology.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xf.psychology.R;
import com.xf.psychology.base.BaseFragment;
import com.xf.psychology.ui.activity.ShareFeelingActivity;

public class AddFragment extends BaseFragment {

    public static AddFragment newInstance() {
        Bundle args = new Bundle();
        AddFragment fragment = new AddFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intent intent = new Intent(requireActivity(), ShareFeelingActivity.class);
        startActivity(intent);
        View view = inflater.inflate(getLayoutId(), container, false);
        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void findViewsById(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add;
    }
}