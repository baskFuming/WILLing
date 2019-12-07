package com.xxx.willing.model.http.bean;

public class WalletMarketBean {

    private int coinId;
    private String coinSymbol;
    private String coinUrl;
    private String coinName;
    private double coinPriceRmb;
    private String coinPriceUsdt;
    private String coinFluctuation;

    public int getCoinId() {
        return coinId;
    }

    public String getCoinSymbol() {
        return coinSymbol;
    }

    public String getCoinUrl() {
        return coinUrl;
    }

    public String getCoinName() {
        return coinName == null ? coinSymbol : coinName;
    }

    public String getCoinPriceRmb() {
        return "￥" + String.valueOf(coinPriceRmb);
    }

    public String getCoinPriceUsdt() {
        return "$" + coinPriceUsdt;
    }

    public String getCoinFluctuation() {
        return coinFluctuation;
    }
}
