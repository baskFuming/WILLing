package com.xxx.willing.ui.wallet.window;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.willing.R;
import com.xxx.willing.base.popup.BasePopup;
import com.xxx.willing.model.http.bean.WalletCoinBean;
import com.xxx.willing.ui.wallet.adapter.WalletExchangeCheckAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WalletExchangeCheckWindow extends BasePopup {

    public static WalletExchangeCheckWindow getInstance(Context context, List<WalletCoinBean.ListBean> list, BaseQuickAdapter.OnItemClickListener itemClickListener) {
        return new WalletExchangeCheckWindow(context, list, itemClickListener);
    }

    @BindView(R.id.window_exchange_check_recycler)
    RecyclerView mRecycler;

    private Context context;
    private List<WalletCoinBean.ListBean> mList = new ArrayList<>();
    private BaseQuickAdapter.OnItemClickListener itemClickListener;
    private WalletExchangeCheckAdapter mAdapter;

    private WalletExchangeCheckWindow(Context context, List<WalletCoinBean.ListBean> list, BaseQuickAdapter.OnItemClickListener itemClickListener) {
        super(context);
        this.context = context;
        this.mList.addAll(list);
        this.itemClickListener = itemClickListener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.window_exchange_check;
    }

    @Override
    protected void initData() {
        mAdapter = new WalletExchangeCheckAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(context));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(itemClickListener);
    }


    public void notifyData(List<WalletCoinBean.ListBean> list) {
        mList.clear();
        mList.addAll(list);
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }
}
