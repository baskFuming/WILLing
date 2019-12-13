package com.xxx.willing.ui.main.wheels;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FM
 * @desc 引导页帮助类
 * @date 2019-12-13
 */

public class PageFragmentAdapter extends FragmentPagerAdapter {
    private List<PageFragment> fragments = new ArrayList<>();

    public PageFragmentAdapter(FragmentManager fm, List<PageFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public PageFragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
