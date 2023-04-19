package com.xf.psychology.ui.activity;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jaeger.library.StatusBarUtil;
import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.base.BaseActivity;
import com.xf.psychology.ui.fragment.ArticleFragment;
import com.xf.psychology.ui.fragment.HomeFragment;
import com.xf.psychology.ui.fragment.MineFragment;
import com.xf.psychology.ui.fragment.SleepFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private ViewPager viewPager;
    private CommonTabLayout tabLayout;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void initListener() {
        if (!App.isLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        StatusBarUtil.setTranslucent(this);
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.colorPrimary));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        ArrayList<CustomTabEntity> tabs = new ArrayList<>();
        tabs.add(new Tab1("首页", R.drawable.icon_home, R.drawable.icon_home_un));
        tabs.add(new Tab1("文章", R.drawable.ic_wz, R.drawable.ic_wz_un));
        tabs.add(new Tab1("睡眠", R.drawable.ic_sleep, R.drawable.ic_sleep_un));
        tabs.add(new Tab1("我的", R.drawable.icon_mine, R.drawable.icon_mine_un));
        fragments.add(HomeFragment.newInstance());
        fragments.add(ArticleFragment.newInstance());
        fragments.add(SleepFragment.newInstance());
        fragments.add(MineFragment.newInstance());
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new MyViewPagerAdapter(tabLayout,fragments, getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == 3){
                    fragments.get(3).onResume();
                }
            }
            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
                if(position == 3){
                    fragments.get(3).onResume();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tabLayout.setTabData(tabs);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
                if(position == 3){
                    fragments.get(3).onResume();
                }
            }
            @Override
            public void onTabReselect(int position) {}
        });


    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViewsById() {
        viewPager = findViewById(R.id.viewPager);
        /*viewPager.setScanScroll(false);*/
        tabLayout = findViewById(R.id.tabLayout);
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        private CommonTabLayout tabLayout;

        public MyViewPagerAdapter(CommonTabLayout tabLayout,List<Fragment> fragments, @NonNull FragmentManager fm) {
            super(fm);
            this.tabLayout = tabLayout;
            this.fragments = fragments;
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
    private class Tab1 implements CustomTabEntity {
        private String title;
        private int select;
        private int unSelect;

        public Tab1(String title, int select, int unSelect) {
            this.title = title;
            this.select = select;
            this.unSelect = unSelect;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return select;
        }

        @Override
        public int getTabUnselectedIcon() {
            return unSelect;
        }
    }

    private class Tab2 implements CustomTabEntity {
        private int select;
        private int unSelect;

        public Tab2(int select, int unSelect) {
            this.select = select;
            this.unSelect = unSelect;
        }

        @Override
        public String getTabTitle() {
            return null;
        }

        @Override
        public int getTabSelectedIcon() {
            return select;
        }

        @Override
        public int getTabUnselectedIcon() {
            return unSelect;
        }
    }
}