package com.xxx.willing.ui.wallet;

import android.support.v4.view.ViewPager;

import com.xxx.willing.R;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.config.EventBusConfig;
import com.xxx.willing.ui.wallet.adapter.WalletAdapter;
import com.xxx.willing.ui.wallet.fragment.WalletAccountFragment;
import com.xxx.willing.ui.wallet.fragment.WalletExchangeFragment;
import com.xxx.willing.ui.wallet.fragment.WalletMarketFragment;
import com.xxx.willing.ui.wallet.fragment.WalletReleaseFragment;
import com.xxx.willing.view.MyTabLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

public class WalletFragment extends BaseFragment {

    @BindView(R.id.wallet_tab_layout)
    MyTabLayout mTabLayout;
    @BindView(R.id.wallet_view_pager)
    ViewPager mViewPager;

    private ArrayList<BaseFragment> list;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wallet;
    }

    @Override
    protected void initData() {
        String[] title = getResources().getStringArray(R.array.wallet_tab);

        list = new ArrayList<>();
        list.add(WalletAccountFragment.getInstance());
        list.add(WalletReleaseFragment.getInstance());
        list.add(WalletExchangeFragment.getInstance());
        list.add(WalletMarketFragment.getInstance());


        mTabLayout.setSelectedIndicatorHeight(6);
        mTabLayout.setTabIndicatorWidth(80);
        mTabLayout.setTabTextColors(getResources().getColor(R.color.wallet_tab_default_color), getResources().getColor(R.color.wallet_tab_select_color));
        mTabLayout.setTabTextSize(38, 44);
        mTabLayout.setTextSelectedBold(true);
        mTabLayout.setTabMode(MyTabLayout.GRAVITY_CENTER);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.main_tab_select_color));

        WalletAdapter walletAdapter = new WalletAdapter(getChildFragmentManager(), list, title);
        mViewPager.setAdapter(walletAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(3);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(String eventFlag) {
        //首先调用父类
        super.onEventBus(eventFlag);
        switch (eventFlag) {
            case EventBusConfig.EVENT_LOGIN:
                for (BaseFragment baseFragment : list) {
                    if (baseFragment instanceof WalletAccountFragment) {
                        ((WalletAccountFragment) baseFragment).onRefresh();
                    }
                    if (baseFragment instanceof WalletReleaseFragment) {
                        ((WalletReleaseFragment) baseFragment).onRefresh();
                    }
                    if (baseFragment instanceof WalletExchangeFragment) {
                        ((WalletExchangeFragment) baseFragment).onRefresh();
                    }
                    if (baseFragment instanceof WalletMarketFragment) {
                        ((WalletMarketFragment) baseFragment).onRefresh();
                    }
                }
                break;
        }
    }

}