package com.xxx.willing.ui.wallet.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.model.glide.GlideUrlUtil;
import com.xxx.willing.model.http.bean.WalletAccountBean;


import java.util.List;

public class WalletAccountAdapter extends BaseQuickAdapter<WalletAccountBean.ListBean, BaseViewHolder> {

    public WalletAccountAdapter(@Nullable List<WalletAccountBean.ListBean> data) {
        super(R.layout.item_wallet_account, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WalletAccountBean.ListBean item) {
        helper.setText(R.id.item_wallet_account_symbol, item.getSymbol())
                .setText(R.id.item_wallet_account_number, item.getAmount() + "")
                .setText(R.id.item_wallet_account_rmb, "≈$" + item.getUsaAmount());

        GlideUrlUtil.loadCircle(mContext, HttpConfig.HTTP_IMG_URL + item.getCoinIcon(),R.mipmap.coin_default, helper.getView(R.id.item_wallet_account_icon));
    }
}
