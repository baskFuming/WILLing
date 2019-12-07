package com.xxx.willing.ui.wallet.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.WalletExchangeBean;

import java.util.List;

public class WalletExchangeAdapter extends BaseQuickAdapter<WalletExchangeBean, BaseViewHolder> {

    public WalletExchangeAdapter(@Nullable List<WalletExchangeBean> data) {
        super(R.layout.item_wallet_exchange, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WalletExchangeBean item) {
        helper.setText(R.id.item_wallet_exchange_base_amount, item.getBaseCoinSymbol())
                .setText(R.id.item_wallet_exchange_base_amount, item.getBaseCoinAmount())
                .setText(R.id.item_wallet_exchange_target_symbol, item.getTargetCoinSymbol())
                .setText(R.id.item_wallet_exchange_target_amount, item.getTargetCoinAmount())
                .setText(R.id.item_wallet_exchange_time, item.getCreateTime());
    }
}
