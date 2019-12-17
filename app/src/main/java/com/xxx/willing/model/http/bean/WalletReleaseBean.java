package com.xxx.willing.model.http.bean;

import android.content.Context;

import com.xxx.willing.R;
import com.xxx.willing.model.http.utils.ApiType;

public class WalletReleaseBean {

    private int id;

    private int type;

    private double amount;

    private String coinSymbol;

    private String createTime;

    public double getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public String getType(Context context) {
        switch (type) {
            case ApiType.WALLET_RELEASE_TYPE_1:
                return context.getString(R.string.item_wallet_release_type_1);
            case ApiType.WALLET_RELEASE_TYPE_2:
                return context.getString(R.string.item_wallet_release_type_2);
            default:
                return context.getString(R.string.item_wallet_release_type_0);
        }
    }

    public String getCoinSymbol() {
        return coinSymbol;
    }

    public String getCreateTime() {
        if (createTime != null) {
            String[] split = createTime.split(" ");
            if (split.length == 2) {
                return split[0] + "\n" + split[1];
            } else {
                return createTime;
            }
        } else {
            return "---";
        }
    }
}
