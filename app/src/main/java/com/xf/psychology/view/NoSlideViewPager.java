package com.xf.psychology.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class NoSlideViewPager extends View {
    public NoSlideViewPager(Context context) {
        this(context, null);
    }

    public NoSlideViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoSlideViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

}
