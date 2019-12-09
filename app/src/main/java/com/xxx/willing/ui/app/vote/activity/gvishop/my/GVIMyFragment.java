package com.xxx.willing.ui.app.vote.activity.gvishop.my;

import android.view.View;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseWebActivity;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.ui.app.vote.activity.gvishop.my.address.ShipAddressActivity;
import com.xxx.willing.ui.app.vote.activity.gvishop.my.order.MyOrderActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author FM
 * @desc 商城首页
 * @date 2019-12-04
 */

public class GVIMyFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.gvi_my_fragment;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(getActivity());
    }

    @OnClick({R.id.re_my_order, R.id.re_my_address})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.re_my_order: //订单
                MyOrderActivity.actionStart(getActivity());
                break;
            case R.id.re_my_address: //我的地址
                ShipAddressActivity.actionStart(getActivity());
                break;
        }
    }

    //    //头像监听事件
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onUserInfoEvent(UserInfoEntry infoEntry) {
//
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(getActivity());
    }
}
