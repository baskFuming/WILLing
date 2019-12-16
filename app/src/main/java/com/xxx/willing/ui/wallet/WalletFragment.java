package com.xxx.willing.ui.wallet;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
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
    XTabLayout mTabLayout;
    @BindView(R.id.wallet_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.main_title)
    TextView mTitle;
    @BindView(R.id.main_return)
    ImageButton mImgreturn;

    private ArrayList<BaseFragment> list;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wallet;
    }

    @Override
    protected void initData() {
        mTitle.setText(getString(R.string.main_wallet));
        mImgreturn.setVisibility(View.GONE);
        String[] title = getResources().getStringArray(R.array.wallet_tab);

        list = new ArrayList<>();
        list.add(WalletAccountFragment.getInstance());
        list.add(WalletReleaseFragment.getInstance());
        list.add(WalletExchangeFragment.getInstance());
        list.add(WalletMarketFragment.getInstance());
        mTabLayout.setTabGravity(XTabLayout.GRAVITY_FILL);
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