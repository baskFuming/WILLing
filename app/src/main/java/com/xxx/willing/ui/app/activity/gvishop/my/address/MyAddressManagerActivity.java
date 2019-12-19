package com.xxx.willing.ui.app.activity.gvishop.my.address;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.MyAddressBean;
import com.xxx.willing.model.http.bean.MyOrderBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.PageBean;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.app.activity.gvishop.my.address.adapter.AddressAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author FM
 * @desc 我的收货地址
 * @date 2019-12-05
 */

public class MyAddressManagerActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener
        , BaseQuickAdapter.OnItemChildClickListener {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MyAddressManagerActivity.class);
        activity.startActivity(intent);
    }

    private int page = UIConfig.PAGE_DEFAULT;

    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;
    private List<MyAddressBean> mList = new ArrayList<>();
    private AddressAdapter mAdapter;

    @Override
    protected String initTitle() {
        return getString(R.string.my_address_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_manager;
    }

    @Override
    protected void initData() {

        mAdapter = new AddressAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager((this)));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
        mAdapter.setOnItemChildClickListener(this::onItemChildClick);
        loadDate();
    }


    @OnClick({R.id.add_new_address})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.add_new_address://添加新地址
                SettingAddressActivity.actionStart(this, SettingAddressActivity.ADD_TAG);
                break;
        }
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
        switch (view.getId()) {
            case R.id.update_address_item:
                SettingAddressActivity.actionStartForResult(this, SettingAddressActivity.UPDATE_TAG,mList.get(position));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == UIConfig.RESULT_CODE) {
            if (data != null) {

            }
        }
    }

    /**
     * @model 我的收货地址
     */
    private void loadDate() {
        Api.getInstance().myAddresses(page, UIConfig.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PageBean<MyAddressBean>>(this) {
                    @Override
                    public void onSuccess(BaseBean<PageBean<MyAddressBean>> bean) {
                        if (bean == null) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        PageBean<MyAddressBean> data = bean.getData();
                        if (data == null) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        List<MyAddressBean> list = data.getList();
                        if (list == null || list.size() == 0 && page == UIConfig.PAGE_DEFAULT) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }

                        mNotData.setVisibility(View.GONE);
                        mRecycler.setVisibility(View.VISIBLE);
                        if (page == UIConfig.PAGE_DEFAULT) {
                            mList.clear();
                        }

                        mList.addAll(list);
                        if (list.size() < UIConfig.PAGE_SIZE) {
                            mAdapter.loadMoreEnd(true);
                        } else {
                            mAdapter.loadMoreComplete();
                        }
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        if (mList.size() == 0) {
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
}
