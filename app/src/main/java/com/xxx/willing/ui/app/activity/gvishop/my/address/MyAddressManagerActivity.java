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
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.ui.app.activity.gvishop.my.address.adapter.AddressAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    private List<BaseBean> mList = new ArrayList<>();
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
                SettingAddressActivity.actionStartForResult(this, SettingAddressActivity.UPDATE_TAG);
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
     * @model 添加收货地址
     */
    private void loadDate() {

    }

}
