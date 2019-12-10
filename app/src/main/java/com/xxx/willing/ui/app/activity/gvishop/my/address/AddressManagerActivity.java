package com.xxx.willing.ui.app.vote.activity.gvishop.my.address;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;

import butterknife.OnClick;
/**
 *  @desc  我的收货地址
 *  @author FM
 *  @date   2019-12-05
 */

public class AddressManagerActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, AddressManagerActivity.class);
        activity.startActivity(intent);
    }


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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
