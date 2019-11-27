package com.xxx.willing.ui.wallet.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.WalletExchangeBean;
import com.xxx.willing.model.http.bean.WalletReleaseBean;

import java.util.List;

public class WalletExchangeAdapter extends BaseQuickAdapter<WalletExchangeBean, BaseViewHolder> {

    public WalletExchangeAdapter(@Nullable List<WalletExchangeBean> data) {
        super(R.layout.item_wallet_exchange, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WalletExchangeBean item) {
//        helper.setText(R.id.item_wallet_exchange_base_amount, "")
//                .setText(R.id.item_wallet_exchange_base_amount, "")
//                .setText(R.id.item_wallet_exchange_target_symbol, "")
//                .setText(R.id.item_wallet_exchange_target_amount, "")
//                .setText(R.id.item_wallet_exchange_time, "")
//                .setText(R.id.item_wallet_exchange_year, "");
    }
}
