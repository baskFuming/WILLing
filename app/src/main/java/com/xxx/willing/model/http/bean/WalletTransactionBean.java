package com.xxx.willing.model.http.bean;

public class WalletTransactionBean {

    /**
     * amount : 100.0
     * types : 2
     * coinUrl : img/eth_default_pic.png
     * createTime : 2019-12-11 14:39:07
     * status : 0
     */

    private double amount;
    private int types;
    private String coinUrl;
    private String address;
    private String createTime;
    private int status;

    public String getAddress() {
        return address;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTypes() {
        return types;
    }

    public void setTypes(int types) {
        this.types = types;
    }

    public String getCoinUrl() {
        return coinUrl;
    }

    public void setCoinUrl(String coinUrl) {
        this.coinUrl = coinUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
