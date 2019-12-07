package com.xxx.willing.ui.wallet.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.WalletReleaseBean;

import java.util.List;

public class WalletReleaseAdapter extends BaseQuickAdapter<WalletReleaseBean, BaseViewHolder> {

    public WalletReleaseAdapter(@Nullable List<WalletReleaseBean> data) {
        super(R.layout.item_wallet_release, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WalletReleaseBean item) {
        helper.setText(R.id.item_wallet_release_number, "+" + item.getAmount() + item.getCoinSymbol())
                .setText(R.id.item_wallet_release_time, item.getCreateTime())
                .setText(R.id.item_wallet_release_type, item.getType(mContext));
    }
}
