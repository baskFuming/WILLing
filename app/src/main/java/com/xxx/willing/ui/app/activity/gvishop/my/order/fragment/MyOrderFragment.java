package com.xxx.willing.ui.app.activity.gvishop.my.order.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.willing.R;
import com.xxx.willing.base.fragment.BaseFragment;

import butterknife.BindView;

/**
 * @author FM
 * @desc 我的订单
 * @date 2019-12-04
 */

public class MyOrderFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener,BaseQuickAdapter.OnItemChildClickListener{

    public static MyOrderFragment getInstance(int status,String statusStr) {
        MyOrderFragment fragment = new MyOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        bundle.putString("statusStr", statusStr);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;

    private int status;
    private String statusStr;

    @Override
    protected int getLayoutId() {
        return R.layout.my_order_fragment;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            status = bundle.getInt("status");
            statusStr = bundle.getString("statusStr");
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
