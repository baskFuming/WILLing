package com.xxx.willing.ui.wallet.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.WalletMarketBean;
import com.xxx.willing.model.utils.GlideUtil;

import java.util.List;

public class WalletMarketAdapter extends BaseQuickAdapter<WalletMarketBean, BaseViewHolder> {

    public WalletMarketAdapter(@Nullable List<WalletMarketBean> data) {
        super(R.layout.item_wallet_market, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WalletMarketBean item) {
        String position = String.valueOf((helper.getAdapterPosition() + 1));
        if (position.length() == 1) position = "0" + position;
        helper.setText(R.id.item_wallet_market_position, position)
                .setText(R.id.item_wallet_market_symbol, item.getCoinSymbol())
                .setText(R.id.item_wallet_market_name, item.getCoinName())
                .setText(R.id.item_wallet_market_usa, item.getCoinPriceUsdt())
                .setText(R.id.item_wallet_market_rmb, item.getCoinPriceRmb());

        TextView tvRate = helper.getView(R.id.item_wallet_market_rate);
        if (item.getCoinFluctuation().contains("+")) {
            tvRate.setBackgroundResource(R.drawable.shape_item_wallet_rise);
        } else if (item.getCoinFluctuation().contains("-")) {
            tvRate.setBackgroundResource(R.drawable.shape_item_wallet_fall);
        } else {
            tvRate.setBackgroundResource(R.drawable.shape_item_wallet_default);
        }

        GlideUtil.loadCircle(mContext, item.getCoinUrl(), helper.getView(R.id.item_wallet_market_icon));
    }
}
