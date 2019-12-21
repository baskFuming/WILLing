package com.xxx.willing.ui.app;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseWebActivity;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.config.AppConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.GameBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.app.activity.GameActivity;
import com.xxx.willing.ui.app.activity.gvishop.GVIShopActivity;
import com.xxx.willing.ui.app.activity.partner.PartnerActivity;
import com.xxx.willing.ui.app.activity.rank.CmpRankActivity;
import com.xxx.willing.ui.app.activity.vote.VoteJoinActivity;
import com.xxx.willing.ui.app.adapter.AppGameAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AppFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.main_app_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.main_app_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_app_not_game)
    LinearLayout mNotGame;

    private List<GameBean> mList = new ArrayList<>();
    private AppGameAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_app;
    }

    @Override
    protected void initData() {
        mAdapter = new AppGameAdapter(mList);
        mRecycler.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
        loadGameList();
    }

    @OnClick({R.id.app_vote, R.id.app_partner, R.id.app_competition, R.id.app_shop, R.id.app_eth, R.id.app_btc, R.id.app_H_coin, R.id.app_huo_bi, R.id.app_kong_yi_college})
    public void OnClick(View view) {
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
//                ToastUtil.showToast("敬请期待");
                GVIShopActivity.actionStart(getActivity());
                break;
            case R.id.app_eth:
                BaseWebActivity.actionStart(getActivity(), AppConfig.APP_ETH_BROWSER, getString(R.string.app_eth));
                break;
            case R.id.app_btc:
                BaseWebActivity.actionStart(getActivity(), AppConfig.APP_BTC_BROWSER, getString(R.string.app_btc));
                break;
            case R.id.app_H_coin:
                BaseWebActivity.actionStart(getActivity(), AppConfig.APP_HCOIN_BROWSER, getString(R.string.app_H_coin));
                break;
            case R.id.app_huo_bi:
                BaseWebActivity.actionStart(getActivity(), AppConfig.APP_HUOBI_BROWSER, getString(R.string.app_huo_bi));
                break;
            case R.id.app_kong_yi_college:
                BaseWebActivity.actionStart(getActivity(), AppConfig.APP_Study_BROWSER, getString(R.string.app_kong_yi_college));
                break;
        }
    }

    @Override
    public void onRefresh() {
        loadGameList();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        GameActivity.actionStart(getActivity(), mList.get(position));
    }

    /**
     * @Model 获取游戏列表
     */
    private void loadGameList() {
        Api.getInstance().getGameList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<List<GameBean>>(getActivity()) {

                    @Override
                    public void onSuccess(BaseBean<List<GameBean>> bean) {
                        if (bean == null) {
                            mNotGame.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mRefresh.setRefreshing(false);
                            return;
                        }
                        List<GameBean> list = bean.getData();
                        if (list == null || list.size() == 0) {
                            mNotGame.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mRefresh.setRefreshing(false);
                            return;
                        }
                        //移除未开启的游戏
                        for (int i = 0; i < list.size(); i++) {
                            GameBean gameBean = list.get(i);
                            if (!gameBean.getEnableFlag()) {//==1 开启
                                list.remove(i);
                                i--;
                            }
                        }
                        mList.clear();
                        mList.addAll(list);
                        mAdapter.notifyDataSetChanged();
                        mNotGame.setVisibility(View.GONE);
                        mRecycler.setVisibility(View.VISIBLE);
                        mRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        mNotGame.setVisibility(View.VISIBLE);
                        mRecycler.setVisibility(View.GONE);
                        mRefresh.setRefreshing(false);
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        if (mRefresh != null) {
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


    //当Fragment隐藏状态发生改变均触发该方法
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mRefresh.post(() -> {
            loadGameList();
        });
    }
}
