package com.xxx.willing.model.http.bean;

public class WalletExchangeBean {

    private String baseCoinSymbol;
    private String targetCoinSymbol;
    private double baseCoinAmount;
    private double targetCoinAmount;
    private String createTime;

    public String getBaseCoinSymbol() {
        return baseCoinSymbol;
    }

    public String getTargetCoinSymbol() {
        return targetCoinSymbol;
    }

    public String getBaseCoinAmount() {
        return baseCoinAmount + "";
    }

    public String getTargetCoinAmount() {
        return targetCoinAmount + "";
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
