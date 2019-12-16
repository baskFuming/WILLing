package com.xxx.willing.ui.wallet.fragment;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.willing.R;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.config.EventBusConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.WalletAccountBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.wallet.activity.WalletCoinDetailActivity;
import com.xxx.willing.ui.wallet.adapter.WalletAccountAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WalletAccountFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {

    public static WalletAccountFragment getInstance() {
        return new WalletAccountFragment();
    }

    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;

    @BindView(R.id.wallet_account_total_asset)
    TextView mTotalAsset;

    private WalletAccountAdapter mAdapter;
    private List<WalletAccountBean.ListBean> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wallet_account;
    }

    @Override
    protected void initData() {
        mAdapter = new WalletAccountAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);

        loadData();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        WalletCoinDetailActivity.actionStart(getContext(), mList.get(position));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(String eventFlag) {
        super.onEventBus(eventFlag);
        switch (eventFlag) {
            case EventBusConfig.EVENT_UPDATE_WALLET:
                loadData();
                break;
        }
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    private void loadData() {
        Api.getInstance().getWalletAccountList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<WalletAccountBean>(getActivity()) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(BaseBean<WalletAccountBean> bean, boolean todaySign) {
                        if (bean == null) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        WalletAccountBean data = bean.getData();
                        if (data == null) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        double totalAsset = data.getTotalAsset();
                        mTotalAsset.setText(totalAsset + "");
                        List<WalletAccountBean.ListBean> list = data.getList();
                        if (list == null || list.size() == 0 ) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                        }else {
                            mNotData.setVisibility(View.GONE);
                            mRecycler.setVisibility(View.VISIBLE);
                            mList.clear();
                            mList.addAll(list);
                            mAdapter.notifyDataSetChanged();
                        }
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

}


