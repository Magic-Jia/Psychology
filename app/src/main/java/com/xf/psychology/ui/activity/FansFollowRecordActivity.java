package com.xf.psychology.ui.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jaeger.library.StatusBarUtil;
import com.xf.psychology.R;
import com.xf.psychology.base.BaseActivity;
import com.xf.psychology.ui.fragment.FansFragment;
import com.xf.psychology.ui.fragment.FollowFragment;

import java.util.ArrayList;
import java.util.List;

public class FansFollowRecordActivity extends BaseActivity {

    private ViewPager viewPager;

    private CommonTabLayout tabLayout;

    private List<Fragment> fragments = new ArrayList<>();

    protected void initListener(){
        StatusBarUtil.setTranslucent(this);
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.colorPrimary));
        ArrayList<CustomTabEntity> tabs = new ArrayList<>();

        tabs.add(new Tab("关注", R.drawable.icon_home, R.drawable.icon_home_un));
        tabs.add(new Tab("粉丝", R.drawable.ic_wz, R.drawable.ic_wz_un));
        fragments.add(FollowFragment.newInstance());
        fragments.add(FansFragment.newInstance());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new MyViewPagerAdapter(fragments, getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
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
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    protected void initData(){

    }

    protected int getLayoutId(){
        return R.layout.activity_fans_follow_record;
    }

    protected void findViewsById(){
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public MyViewPagerAdapter(List<Fragment> fragments, @NonNull FragmentManager fm) {
            super(fm);
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