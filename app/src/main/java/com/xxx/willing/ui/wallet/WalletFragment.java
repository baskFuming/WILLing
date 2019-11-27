package com.xxx.willing.ui.wallet;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.xxx.willing.R;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.ui.wallet.adapter.WalletAdapter;
import com.xxx.willing.ui.wallet.fragment.WalletAccountFragment;
import com.xxx.willing.ui.wallet.fragment.WalletExchangeFragment;
import com.xxx.willing.ui.wallet.fragment.WalletMarketFragment;
import com.xxx.willing.ui.wallet.fragment.WalletReleaseFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class WalletFragment extends BaseFragment {

    @BindView(R.id.wallet_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.wallet_view_pager)
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wallet;
    }

    @Override
    protected void initData() {
        String[] title = getResources().getStringArray(R.array.wallet_tab);

        ArrayList<BaseFragment> list = new ArrayList<>();
        list.add(WalletAccountFragment.getInstance());
        list.add(WalletReleaseFragment.getInstance());
        list.add(WalletExchangeFragment.getInstance());
        list.add(WalletMarketFragment.getInstance());

        WalletAdapter walletAdapter = new WalletAdapter(getChildFragmentManager(), list, title);
        mViewPager.setAdapter(walletAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(3);
    }
}