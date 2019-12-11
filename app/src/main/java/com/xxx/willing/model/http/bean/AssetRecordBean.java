package com.xxx.willing.model.http.bean;

public class AssetRecordBean {

    /**
     * targetSymbol : ETH
     * amount : 100.0
     * types : 2
     * balance : 404.5
     * createTime : 2019-12-11 14:39:07
     * fee : 0.0
     * baseSymbol : ETH
     * status : 0
     */

    private String targetSymbol;
    private double amount;
    private int types;
    private double balance;
    private String createTime;
    private double fee;
    private String baseSymbol;
    private int status;

    public String getTargetSymbol() {
        return targetSymbol;
    }

    public void setTargetSymbol(String targetSymbol) {
        this.targetSymbol = targetSymbol;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getBaseSymbol() {
        return baseSymbol;
    }

    public void setBaseSymbol(String baseSymbol) {
        this.baseSymbol = baseSymbol;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
