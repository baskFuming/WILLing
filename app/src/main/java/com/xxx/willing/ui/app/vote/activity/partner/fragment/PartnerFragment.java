package com.xxx.willing.ui.app.vote.activity.partner.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.willing.R;
import com.xxx.willing.base.fragment.BaseFragment;

/**
 * @author FM
 * @desc 合伙人列表状态
 * @date 2019-12-03
 */
public class PartnerFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    public static PartnerFragment getInstance(int status, String statusStr) {
        PartnerFragment fragment = new PartnerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        fragment.setArguments(bundle);
        return fragment;
    }
    private int status;
    @Override
    protected int getLayoutId() {
        return R.layout.partner_fragment;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            status = bundle.getInt("status");
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreRequested() {

    }
}
