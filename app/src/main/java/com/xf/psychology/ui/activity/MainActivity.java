package com.xf.psychology.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    private View add;
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


        ArrayList<CustomTabEntity> tabs = new ArrayList<>();
        tabs.add(new Tab("首页", R.drawable.icon_home, R.drawable.icon_home_un));
        tabs.add(new Tab("文章", R.drawable.ic_wz, R.drawable.ic_wz_un));
        tabs.add(new Tab("",0,0));
        tabs.add(new Tab("睡眠", R.drawable.ic_sleep, R.drawable.ic_sleep_un));
        tabs.add(new Tab("我的", R.drawable.icon_mine, R.drawable.icon_mine_un));
        fragments.add(HomeFragment.newInstance());
        fragments.add(ArticleFragment.newInstance());
        fragments.add(SleepFragment.newInstance());
        fragments.add(MineFragment.newInstance());
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new MyViewPagerAdapter(fragments, getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == 3){
                    fragments.get(3).onResume();
                }
            }

            @Override
            public void onPageSelected(int position) {
                if(position>=2)
                    position++;
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
                if(position == 2)
                    return;
                if(position>=3)
                    position--;
                viewPager.setCurrentItem(position);
                if(position == 3){
                    fragments.get(3).onResume();
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ShareFeelingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
                fragments.get(0).getChildFragmentManager().getFragment(new Bundle(),"");
            }
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
        add = findViewById(R.id.add);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public MyViewPagerAdapter(List<Fragment> fragments, @NonNull FragmentManager fm) {
            super(fm); // 在这里调用父类的构造函数
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


    private class Tab implements CustomTabEntity {
        private String title;
        private int select;
        private int unSelect;

        public Tab(String title, int select, int unSelect) {
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
}