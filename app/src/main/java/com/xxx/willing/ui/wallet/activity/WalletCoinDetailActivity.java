package com.xxx.willing.ui.wallet.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.WalletAccountBean;
import com.xxx.willing.model.http.bean.WalletTransactionBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.PageBean;
import com.xxx.willing.model.http.utils.ApiType;
import com.xxx.willing.model.utils.GlideUtil;
import com.xxx.willing.model.utils.KeyBoardUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.wallet.adapter.WalletTransactionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WalletCoinDetailActivity extends BaseTitleActivity implements TabLayout.BaseOnTabSelectedListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    public static void actionStart(Activity activity, WalletAccountBean bean) {
        Intent intent = new Intent(activity, WalletCoinDetailActivity.class);
        intent.putExtra("bean", bean);
        activity.startActivity(intent);
    }

    private void initBundle() {
        Intent intent = getIntent();
//        bean = (WalletAccountBean) intent.getSerializableExtra("bean");
//        if (bean == null) {
//            bean = new WalletAccountBean();
//        }
        coinIcon = intent.getStringExtra("coinIcon");
        coinId = intent.getIntExtra("coinId", 0);
        amount = intent.getDoubleExtra("amount", 0);
        usaAmount = intent.getDoubleExtra("usaAmount", 0);
        free = intent.getDoubleExtra("free", 0);
        symbol = intent.getStringExtra("symbol");
        address = intent.getStringExtra("address");

    }

    @BindView(R.id.wallet_coin_detail_icon)
    ImageView mIcon;
    @BindView(R.id.wallet_coin_detail_balance)
    TextView mBalance;
    @BindView(R.id.wallet_coin_detail_usa)
    TextView mUsa;
    @BindView(R.id.wallet_coin_detail_address)
    TextView mAddress;
    @BindView(R.id.wallet_coin_detail_tab)
    TabLayout mTabLayout;

    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.wallet_coin_detail_linear)
    LinearLayout mLinear;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;


    private int type;
    private int page = UIConfig.PAGE_DEFAULT;
    private WalletTransactionAdapter mAdapter;
    private List<WalletTransactionBean> mList = new ArrayList<>();

    private String coinIcon;
    private int coinId;
    private double amount;
    private double free;
    private String symbol;
    private String address;
    private double usaAmount;

    @Override
    protected String initTitle() {
        return symbol;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet_coin_detail;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        initBundle();

        String[] tabLayout = getResources().getStringArray(R.array.wallet_coin_tab);
        for (String s : tabLayout) {
            mTabLayout.addTab(mTabLayout.newTab().setText(s));
        }
        mTabLayout.addOnTabSelectedListener(this);

        mAdapter = new WalletTransactionAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecycler);

        mBalance.setText(amount + "");
        mUsa.setText("≈$" + usaAmount);
        mAddress.setText(address);
        GlideUtil.loadCircle(this, coinIcon, mIcon);

        loadData();
    }

    @OnClick({R.id.wallet_coin_detail_copy, R.id.wallet_coin_detail_transfer, R.id.wallet_coin_detail_recharge})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.wallet_coin_detail_copy:
                KeyBoardUtil.copy(this, address);
                break;
            case R.id.wallet_coin_detail_transfer:
                WithdrawalActivity.actionStart(this, coinId, symbol, amount, free);
                break;
            case R.id.wallet_coin_detail_recharge:
                RechargeActivity.actionStart(this, address, symbol);
                break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                type = 0;
                break;
            case 1:
                type = ApiType.TRANSFER_TYPE;
                break;
            case 2:
                type = ApiType.RECHARGE_TYPE;
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @Override
    public void onRefresh() {
        page = UIConfig.PAGE_DEFAULT;
        loadData();
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        loadData();
    }

    private void loadData() {
        Api.getInstance().getTransferRecordList(type, page, UIConfig.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PageBean<WalletTransactionBean>>(this) {

                    @Override
                    public void onSuccess(BaseBean<PageBean<WalletTransactionBean>> bean) {
                        if (bean == null) {
                            mNotData.setVisibility(View.VISIBLE);
                            mLinear.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        PageBean<WalletTransactionBean> data = bean.getData();
                        if (data == null) {
                            mNotData.setVisibility(View.VISIBLE);
                            mLinear.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        List<WalletTransactionBean> list = data.getList();
                        if (list == null || list.size() == 0 && page == UIConfig.PAGE_DEFAULT) {
                            mNotData.setVisibility(View.VISIBLE);
                            mLinear.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }

                        mNotData.setVisibility(View.GONE);
                        mLinear.setVisibility(View.VISIBLE);
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
                            mLinear.setVisibility(View.GONE);
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
