package com.xxx.willing.ui.app.vote.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xxx.willing.base.fragment.BaseFragment;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragments;
    private List<String> tabls;

    public ViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String> tabls) {
        super(fm);
        this.fragments = fragments;
        this.tabls = tabls;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (tabls == null || tabls.size() == 0) return super.getPageTitle(position);
        return tabls.get(position);
    }
}