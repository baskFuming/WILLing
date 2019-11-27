package com.xxx.willing.ui.wallet.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.fragment.BaseFragment;

import butterknife.BindView;

public class WalletAccountFragment extends BaseFragment {

    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;

    @BindView(R.id.wallet_account_total_asset)
    TextView mTotalAsset;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wallet_account;
    }

    @Override
    protected void initData() {

    }
}
