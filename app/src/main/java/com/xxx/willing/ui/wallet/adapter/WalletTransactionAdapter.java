package com.xxx.willing.ui.wallet.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.WalletTransactionBean;

import java.util.List;

public class WalletTransactionAdapter extends BaseQuickAdapter<WalletTransactionBean, BaseViewHolder> {

    public WalletTransactionAdapter(@Nullable List<WalletTransactionBean> data) {
        super(R.layout.item_wallet_transaction, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WalletTransactionBean item) {
//        helper.setText(R.id.item_wallet_transaction_hash, "")
//                .setText(R.id.item_wallet_transaction_time, "")
//                .setImageResource(R.id.item_wallet_transaction_type, 0)
//                .setText(R.id.item_wallet_transaction_status, "")
//                .setText(R.id.item_wallet_transaction_amount, "");
    }
}
