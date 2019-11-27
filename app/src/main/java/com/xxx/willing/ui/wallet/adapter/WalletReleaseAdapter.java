package com.xxx.willing.ui.wallet.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.WalletAccountBean;
import com.xxx.willing.model.http.bean.WalletReleaseBean;
import com.xxx.willing.model.utils.GlideUtil;

import java.util.List;

public class WalletReleaseAdapter extends BaseQuickAdapter<WalletReleaseBean, BaseViewHolder> {

    public WalletReleaseAdapter(@Nullable List<WalletReleaseBean> data) {
        super(R.layout.item_wallet_release, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WalletReleaseBean item) {
//        helper.setText(R.id.item_wallet_release_number, "")
//                .setText(R.id.item_wallet_release_time, "")
//                .setText(R.id.item_wallet_release_type, "");
    }
}
