package com.xxx.willing.ui.wallet.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.WalletAccountBean;
import com.xxx.willing.model.http.bean.WalletMarketBean;
import com.xxx.willing.model.utils.GlideUtil;

import java.util.List;

public class WalletMarketAdapter extends BaseQuickAdapter<WalletMarketBean, BaseViewHolder> {

    public WalletMarketAdapter(@Nullable List<WalletMarketBean> data) {
        super(R.layout.item_wallet_market, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WalletMarketBean item) {
//        helper.setText(R.id.item_wallet_market_position, "")
//                .setText(R.id.item_wallet_market_symbol, "")
//                .setText(R.id.item_wallet_market_name, "")
//                .setText(R.id.item_wallet_market_usa, "")
//                .setText(R.id.item_wallet_market_rmb, "");
//
//        TextView tvRate = helper.getView(R.id.item_wallet_market_rate);
//        if () {
//            tvRate.setBackgroundResource(R.drawable.shape_item_wallet_rise);
//        } else if () {
//            tvRate.setBackgroundResource(R.drawable.shape_item_wallet_fall);
//        } else {
//            tvRate.setBackgroundResource(R.drawable.shape_item_wallet_default);
//        }
//
//        GlideUtil.loadCircle(mContext, "", helper.getView(R.id.item_wallet_market_icon));
    }
}
