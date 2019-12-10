package com.xxx.willing.ui.vote.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xxx.willing.base.fragment.BaseFragment;

import java.util.List;

public class VoteAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> list;
    private List<String> title;

    public VoteAdapter(FragmentManager fm, List<BaseFragment> list, List<String> title) {
        super(fm);
        this.title = title;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }

    @Override
    public int getCount() {
        return title.size();
    }
}
