package com.xxx.willing.ui.wallet.fragment;

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
import com.xxx.willing.model.http.bean.WalletMarketBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.PageBean;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.wallet.adapter.WalletMarketAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WalletMarketFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public static WalletMarketFragment getInstance() {
        return new WalletMarketFragment();
    }

    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;

    private WalletMarketAdapter mAdapter;
    private List<WalletMarketBean> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wallet_market;
    }

    @Override
    protected void initData() {
        mAdapter = new WalletMarketAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);

        loadData();
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    private void loadData() {
        Api.getInstance().getWalletMarketList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<List<WalletMarketBean>>(getActivity()) {

                    @Override
                    public void onSuccess(BaseBean<List<WalletMarketBean>> bean) {
                        if (bean != null) {
                            List<WalletMarketBean> list = bean.getData();
                            if (list != null && list.size() != 0) {
                                mNotData.setVisibility(View.GONE);
                                mRecycler.setVisibility(View.VISIBLE);
                                mList.clear();
                                mList.addAll(list);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mNotData.setVisibility(View.VISIBLE);
                                mRecycler.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
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
