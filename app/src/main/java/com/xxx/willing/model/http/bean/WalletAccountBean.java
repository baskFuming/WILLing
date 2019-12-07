package com.xxx.willing.model.http.bean;

import java.io.Serializable;

public class WalletAccountBean implements Serializable{

    /**
     * symbol : NEO
     * coinId : 10000029
     * amount : 1000000.0
     * usaAmount : 8690000.0
     * address : null
     * fee : 0.01
     * coinIcon : img/usdt_default_pic.png
     */

    private String symbol;
    private int coinId;
    private double amount;
    private double usaAmount;
    private String address;
    private double fee;
    private String coinIcon;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getCoinId() {
        return coinId;
    }

    public void setCoinId(int coinId) {
        this.coinId = coinId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getUsaAmount() {
        return usaAmount;
    }

    public void setUsaAmount(double usaAmount) {
        this.usaAmount = usaAmount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getCoinIcon() {
        return coinIcon;
    }

    public void setCoinIcon(String coinIcon) {
        this.coinIcon = coinIcon;
    }
}
