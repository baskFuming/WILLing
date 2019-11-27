package com.xxx.willing.ui.wallet.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xxx.willing.base.fragment.BaseFragment;

import java.util.List;

public class WalletAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> list;
    private String[] title;

    public WalletAdapter(FragmentManager fm, List<BaseFragment> list, String[] title) {
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
        return title[position];
    }

    @Override
    public int getCount() {
        return title.length;
    }
}
