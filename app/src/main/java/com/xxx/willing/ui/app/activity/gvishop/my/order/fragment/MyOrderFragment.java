package com.xxx.willing.ui.app.activity.gvishop.my.order.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.willing.R;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.ui.app.activity.gvishop.my.order.adapter.MyOrderAdapter;
import com.xxx.willing.ui.app.activity.gvishop.my.order.window.MyOrderPop;
import com.xxx.willing.ui.wallet.adapter.WalletMarketAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author FM
 * @desc 我的订单
 * @date 2019-12-04
 */

public class MyOrderFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener, MyOrderPop.Callback {

    public static MyOrderFragment getInstance(int status, String statusStr) {
        MyOrderFragment fragment = new MyOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        bundle.putString("statusStr", statusStr);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            status = bundle.getInt("status");
            statusStr = bundle.getString("statusStr");
        }
    }

    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;

    private int status;
    private String statusStr;
    private int page = UIConfig.PAGE_DEFAULT;
    private List<BaseBean> mlist = new ArrayList<>();
    private MyOrderAdapter mAdapter;
    private MyOrderPop myOrderPop;


    @Override
    protected int getLayoutId() {
        return R.layout.my_order_fragment;
    }

    @Override
    protected void initData() {
        initBundle();
        mAdapter = new MyOrderAdapter(mlist);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
        mAdapter.setOnItemChildClickListener(this::onItemChildClick);

        myOrderPop = MyOrderPop.getInstance(getContext());
        myOrderPop.setCallback(this);
        loadDate();
    }

    @Override
    public void onRefresh() {
        page = UIConfig.PAGE_DEFAULT;
        loadDate();
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        loadDate();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        BaseBean bean = mlist.get(position);
        switch (view.getId()) {
            case R.id.order_cancel_btn: //待付款取消订单
                if (myOrderPop != null) {
                    myOrderPop.show();
                }
                break;
            case R.id.order_cancel_pay_btn: //付款
                loadPayOrder();
                break;
            case R.id.order_un_delivery: //待发货取消订单
                loadCanleOrder(statusStr);
                break;
            case R.id.order_confirm_btn: //确认收货
                loadConfirm();
                break;
        }
    }

    /**
     * @Model 全部订单
     */
    private void loadDate() {
    }

    /**
     * @Model 确认收货
     */
    private void loadConfirm() {
    }

    /**
     * @param statusStr
     * @mlde 取消订单
     */
    private void loadCanleOrder(String statusStr) {
    }

    /**
     * @Model 支付订单
     */
    private void loadPayOrder() {

    }

    @Override
    public void callback(String orderStr) {
        loadCanleOrder(orderStr);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mRefresh.post(() -> {
            loadDate();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myOrderPop != null) {
            myOrderPop.dismiss();
            myOrderPop = null;
        }
    }
}
