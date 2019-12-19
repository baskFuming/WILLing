package com.xxx.willing.ui.wallet.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.model.glide.GlideUrlUtil;
import com.xxx.willing.model.http.bean.WalletCoinBean;


import java.util.List;

public class WalletExchangeCheckAdapter extends BaseQuickAdapter<WalletCoinBean.ListBean, BaseViewHolder> {

    public WalletExchangeCheckAdapter(@Nullable List<WalletCoinBean.ListBean> data) {
        super(R.layout.item_window_exchange_check, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WalletCoinBean.ListBean item) {
        helper.setText(R.id.item_window_exchange_check_symbol, item.getCoinSymbol());

        GlideUrlUtil.loadCircle(mContext, HttpConfig.HTTP_IMG_URL + item.getCoinUrl(), R.mipmap.my_icon, helper.getView(R.id.item_window_exchange_check_icon));
    }
}
