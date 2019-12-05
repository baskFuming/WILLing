package com.xxx.willing.ui.app;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.xxx.willing.R;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.config.AppConfig;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.app.vote.activity.GameActivity;
import com.xxx.willing.ui.app.vote.activity.StudyActivity;
import com.xxx.willing.ui.app.vote.activity.gvishop.GVIShopActivity;
import com.xxx.willing.ui.app.vote.activity.partner.PartnerActivity;
import com.xxx.willing.ui.app.vote.activity.rank.CmpRankActivity;
import com.xxx.willing.ui.app.vote.activity.vote.VoteJoinActivity;

import butterknife.OnClick;

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
        Uri uri = null;
        switch (view.getId()) {
            case R.id.app_vote://投票加盟
                VoteJoinActivity.actionStart(getActivity());
                break;
            case R.id.app_partner://合伙人列表
                PartnerActivity.actionStart(getActivity());
                break;
            case R.id.app_competition: //竞赛排名
                CmpRankActivity.actionStart(getActivity());
                break;
            case R.id.app_shop: //GVI商场
                GVIShopActivity.actionStart(getActivity());
                break;
            case R.id.app_eth:
                uri = Uri.parse(AppConfig.APP_ETH_BROWSER);
                break;
            case R.id.app_btc:
                uri = Uri.parse(AppConfig.APP_BTC_BROWSER);
                break;
            case R.id.app_H_coin:
//                uri = Uri.parse(ConfigClass.APP_BTC_BROWSER);
                break;
            case R.id.app_huo_bi:
//                uri = Uri.parse(ConfigClass.APP_BTC_BROWSER);
                break;
            case R.id.app_plane:
                GameActivity.actionStart(getActivity(), "");
                break;
            case R.id.app_not:
                //TODO 敬请期待
                ToastUtil.showToast(getString(R.string.app_not));
                break;
            case R.id.app_kong_yi_college:
                StudyActivity.actionStart(getActivity(), "");
                break;
        }
        if (uri != null) {
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }
}
