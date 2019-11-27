package com.xxx.willing.ui.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xxx.willing.R;
import com.xxx.willing.base.fragment.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AppFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_app;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.app_vote, R.id.app_partner, R.id.app_competition, R.id.app_shop, R.id.app_eth, R.id.app_btc, R.id.app_H_coin, R.id.app_huo_bi, R.id.app_plane, R.id.app_not, R.id.app_kong_yi_college})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.app_vote:

                break;
            case R.id.app_partner:

                break;
            case R.id.app_competition:

                break;
            case R.id.app_shop:

                break;
            case R.id.app_eth:

                break;
            case R.id.app_btc:

                break;
            case R.id.app_H_coin:

                break;
            case R.id.app_huo_bi:

                break;
            case R.id.app_plane:

                break;
            case R.id.app_not:
                //TODO 敬请期待
                break;
            case R.id.app_kong_yi_college:

                break;
        }
    }
}
