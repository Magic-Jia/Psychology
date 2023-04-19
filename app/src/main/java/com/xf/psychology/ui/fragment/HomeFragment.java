package com.xf.psychology.ui.fragment;

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
import com.xf.psychology.R;
import com.xf.psychology.base.BaseFragment;
import com.xf.psychology.ui.activity.BookActivity;
import com.xf.psychology.ui.activity.FMActivity;
import com.xf.psychology.ui.activity.ActivityCenterActivity;
import com.xf.psychology.ui.activity.SearchActivity;
import com.xf.psychology.ui.activity.ShareFeelingActivity;
import com.xf.psychology.ui.activity.TestActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {
    private View searchBar;
    private View homeActionTest;
    private View homeActionBook;
    private View homeActionChat;
    private View homeActionFM;
    private View homeActionShare;
    private CommonTabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    protected void initListener() {
        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        tabEntities.add(new MyEntry("关注"));
        tabEntities.add(new MyEntry("推荐"));
        tabEntities.add(new MyEntry("问答"));
        fragments.add(FollowFragment.newInstance());
        fragments.add(RecommendFragment.newInstance());
        fragments.add(QuestionAnswerFragment.newInstance());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new MyViewPagerAdapter(tabLayout,fragments, getChildFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
                if(position == 0){
                    fragments.get(0).onResume();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setTabData(tabEntities);
        tabLayout.setCurrentTab(1);
        viewPager.setCurrentItem(1);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
                if(position == 0){
                    fragments.get(0).onResume();
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        homeActionFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), FMActivity.class));
            }
        });
        homeActionTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), TestActivity.class));
            }
        });
        homeActionChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), ActivityCenterActivity.class));
            }
        });
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), SearchActivity.class));
            }
        });
        homeActionBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), BookActivity.class));
            }
        });
        homeActionShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), ShareFeelingActivity.class));

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home2;
    }

    @Override
    protected void findViewsById(View view) {
        homeActionTest = view.findViewById(R.id.homeActionTest);
        homeActionBook = view.findViewById(R.id.homeActionBook);
        homeActionChat = view.findViewById(R.id.homeActionChat);
        homeActionFM = view.findViewById(R.id.homeActionFM);
        searchBar = view.findViewById(R.id.searchBar);
        homeActionShare = view.findViewById(R.id.homeActionShare);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
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


    private class MyEntry implements CustomTabEntity {
        private String title;

        private MyEntry(String title) {
            this.title = title;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return 0;
        }

        @Override
        public int getTabUnselectedIcon() {
            return 0;
        }
    }
}