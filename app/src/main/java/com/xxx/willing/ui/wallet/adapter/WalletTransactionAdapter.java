package com.xxx.willing.ui.wallet.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.willing.R;
import com.xxx.willing.model.http.bean.WalletTransactionBean;
import com.xxx.willing.model.http.utils.ApiType;
import com.xxx.willing.model.utils.StringUtil;

import java.util.List;

public class WalletTransactionAdapter extends BaseQuickAdapter<WalletTransactionBean, BaseViewHolder> {

    public WalletTransactionAdapter(@Nullable List<WalletTransactionBean> data) {
        super(R.layout.item_wallet_transaction, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WalletTransactionBean item) {
        String amount = "";
        int type = 0;
        String status = "";
        if (item.getTypes() == ApiType.ASSET_RECORD_RECHARGE_TYPE) {
            amount = "+" + item.getAmount();
            type = R.mipmap.item_recharge_icon;
            status = "成功";
        } else if (item.getTypes() == ApiType.ASSET_RECORD_TRANSFER_TYPE) {
            type = R.mipmap.item_transfer_icon;
            amount = "-" + item.getAmount();
            switch (item.getStatus()) {
                case ApiType.TRANSFER_WAIT_STATUS:
                    status = "待放币";
                    break;
                case ApiType.RECHARGE_SUCCESS_STATUS:
                    status = "成功";
                    break;
                case ApiType.TRANSFER_FAIL_TYPE:
                    status = "失败";
                    break;
                case ApiType.TRANSFER_AUDIT_CURRENCY_TYPE:
                    status = "审核中";
                    break;
                default:
                    status = "待放币";
                    break;
            }
        }


        helper.setText(R.id.item_wallet_transaction_hash, StringUtil.getAddress(item.getAddress()))
                .setText(R.id.item_wallet_transaction_time, item.getCreateTime())
                .setImageResource(R.id.item_wallet_transaction_type, type)
                .setText(R.id.item_wallet_transaction_status, status)
                .setText(R.id.item_wallet_transaction_amount, amount);
    }
}
