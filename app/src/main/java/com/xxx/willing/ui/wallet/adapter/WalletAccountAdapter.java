package com.xxx.willing.ui.wallet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.WalletAccountBean;
import com.xxx.willing.model.http.bean.WalletTransactionBean;
import com.xxx.willing.model.utils.GlideUtil;
import com.xxx.willing.ui.wallet.activity.WalletCoinDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class WalletAccountAdapter extends BaseQuickAdapter<WalletAccountBean, BaseViewHolder> {


    public WalletAccountAdapter(@Nullable List<WalletAccountBean> data) {
        super(R.layout.item_wallet_account, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WalletAccountBean item) {
        helper.setText(R.id.item_wallet_account_symbol, item.getSymbol())
                .setText(R.id.item_wallet_account_number, item.getAmount() + "")
                .setText(R.id.item_wallet_account_rmb, "≈$" + item.getUsaAmount());

        GlideUtil.loadCircle(mContext, item.getCoinIcon(), helper.getView(R.id.item_wallet_account_icon));


        helper.getView(R.id.line1).setOnClickListener(view -> {
            Intent intent = new Intent(mContext, WalletCoinDetailActivity.class);
            intent.putExtra("coinIcon",item.getCoinIcon());
            intent.putExtra("coinId",item.getCoinId());
            intent.putExtra("symbol",item.getSymbol());
            intent.putExtra("amount",item.getAmount());
            intent.putExtra("usaAmount",item.getUsaAmount());
            intent.putExtra("address",item.getAddress());
            intent.putExtra("fee",item.getFee());
            mContext.startActivity(intent);
        });
    }
}
