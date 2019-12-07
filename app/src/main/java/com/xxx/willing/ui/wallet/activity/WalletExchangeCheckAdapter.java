package com.xxx.willing.ui.wallet.activity;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.WalletCoinBean;
import com.xxx.willing.model.utils.GlideUtil;

import java.util.List;

public class WalletExchangeCheckAdapter extends BaseQuickAdapter<WalletCoinBean.ListBean, BaseViewHolder> {

    public WalletExchangeCheckAdapter(@Nullable List<WalletCoinBean.ListBean> data) {
        super(R.layout.item_window_exchange_check, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WalletCoinBean.ListBean item) {
        helper.setText(R.id.item_window_exchange_check_symbol, item.getCoinSymbol());

        GlideUtil.loadCircle(mContext, item.getCoinUrl(), helper.getView(R.id.item_window_exchange_check_icon));
    }
}
