package com.xxx.willing.ui.app.activity.gvishop.my.order.fragment;

import android.content.Intent;
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
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.MyOrderBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.BooleanBean;
import com.xxx.willing.model.http.bean.base.PageBean;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.app.activity.gvishop.my.order.adapter.MyOrderAdapter;
import com.xxx.willing.ui.app.activity.gvishop.my.order.window.MyOrderPop;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
    private int orderId;
    private int page = UIConfig.PAGE_DEFAULT;
    private List<MyOrderBean> mlist = new ArrayList<>();
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
        MyOrderBean myOrderBean = mlist.get(position);
        orderId = myOrderBean.getId();
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
                loadCancelOrder();
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
        Api.getInstance().myOrder(status, page, UIConfig.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PageBean<MyOrderBean>>(getActivity()) {
                    @Override
                    public void onSuccess(BaseBean<PageBean<MyOrderBean>> bean) {
                        if (bean == null) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        PageBean<MyOrderBean> data = bean.getData();
                        if (data == null) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        List<MyOrderBean> list = data.getList();
                        if (list == null || list.size() == 0 && page == UIConfig.PAGE_DEFAULT) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }

                        mNotData.setVisibility(View.GONE);
                        mRecycler.setVisibility(View.VISIBLE);
                        if (page == UIConfig.PAGE_DEFAULT) {
                            mlist.clear();
                        }

                        mlist.addAll(list);
                        if (list.size() < UIConfig.PAGE_SIZE) {
                            mAdapter.loadMoreEnd(true);
                        } else {
                            mAdapter.loadMoreComplete();
                        }
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        if (mlist.size() == 0) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                        }
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        if (mRefresh != null && page == UIConfig.PAGE_DEFAULT) {
                            mRefresh.setRefreshing(true);
                        }
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        if (mRefresh != null) {
                            mRefresh.setRefreshing(false);
                        }
                    }
                });
    }

    /**
     * @Model 确认收货
     */
    private void loadConfirm() {
        Api.getInstance().confirmOrder(orderId, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(getActivity()) {
                    @Override
                    public void onSuccess(BaseBean<Object> bean) {
                        ToastUtil.showToast(getString(R.string.comfirm_successful));
                        Intent intent = new Intent();
                        intent.putExtra("type", 2);
                        getActivity().setResult(UIConfig.RESULT_CODE, intent);
                        getActivity().finish();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        showLoading();
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        hideLoading();
                    }
                });
    }

    /**
     * @mlde 取消订单
     */
    private void loadCancelOrder() {
        Api.getInstance().cancelOrder(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(getActivity()) {
                    @Override
                    public void onSuccess(BaseBean<Object> bean) {
                        ToastUtil.showToast(getString(R.string.order_cancle_successful));
                        Intent intent = new Intent();
                        intent.putExtra("type", 0);
                        getActivity().setResult(UIConfig.RESULT_CODE, intent);
                        getActivity().finish();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        showLoading();
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        hideLoading();
                    }
                });
    }

    /**
     * @Model 支付订单
     */
    private void loadPayOrder() {
        Api.getInstance().paymentOrder(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<BooleanBean>(getActivity()) {
                    @Override
                    public void onSuccess(BaseBean<BooleanBean> bean) {
                        ToastUtil.showToast(getString(R.string.pay_seccess));
                        Intent intent = new Intent();
                        intent.putExtra("type", 1);
                        getActivity().setResult(UIConfig.RESULT_CODE, intent);
                        getActivity().finish();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        showLoading();
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        hideLoading();
                    }
                });
    }

    @Override
    public void callback() {
        loadCancelOrder();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mRefresh.post(this::loadDate);
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
