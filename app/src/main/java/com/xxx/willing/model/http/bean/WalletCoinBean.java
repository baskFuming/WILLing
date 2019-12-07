package com.xxx.willing.model.http.bean;

import java.util.List;

public class WalletCoinBean {

    /**
     * gviTargetFee : 0.02
     * bvseTargetFee : 0.01
     * gviBaseFee : 0.0
     * list : [{"isTarget":false,"coinId":10000001,"coinSymbol":"BTC","coinUrl":"img/btc_default_pic.png","coinPriceUsdt":"7562.58","isBase":true},{"isTarget":false,"coinId":10000002,"coinSymbol":"ETH","coinUrl":"img/eth_default_pic.png","coinPriceUsdt":"197.50","isBase":true},{"isTarget":false,"coinId":10000003,"coinSymbol":"USDT","coinUrl":"img/usdt_default_pic.png","coinPriceUsdt":"1.00","isBase":true},{"isTarget":true,"coinId":10000004,"coinSymbol":"GVI","coinUrl":"img/usdt_default_pic.png","coinPriceUsdt":"1","isBase":false},{"isTarget":true,"coinId":10000005,"coinSymbol":"BVSE","coinUrl":"img/usdt_default_pic.png","coinPriceUsdt":"0.005608","isBase":true}]
     * bvseBaseFee : 0.0
     */

    private double gviTargetFee;
    private double bvseTargetFee;
    private double gviBaseFee;
    private double bvseBaseFee;
    private List<ListBean> list;

    public double getGviTargetFee() {
        return gviTargetFee;
    }

    public void setGviTargetFee(double gviTargetFee) {
        this.gviTargetFee = gviTargetFee;
    }

    public double getBvseTargetFee() {
        return bvseTargetFee;
    }

    public void setBvseTargetFee(double bvseTargetFee) {
        this.bvseTargetFee = bvseTargetFee;
    }

    public double getGviBaseFee() {
        return gviBaseFee;
    }

    public void setGviBaseFee(double gviBaseFee) {
        this.gviBaseFee = gviBaseFee;
    }

    public double getBvseBaseFee() {
        return bvseBaseFee;
    }

    public void setBvseBaseFee(double bvseBaseFee) {
        this.bvseBaseFee = bvseBaseFee;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * isTarget : false
         * coinId : 10000001
         * coinSymbol : BTC
         * coinUrl : img/btc_default_pic.png
         * coinPriceUsdt : 7562.58
         * isBase : true
         */

        private int coinId;
        private String coinSymbol;
        private String coinUrl;
        private String coinPriceUsdt;
        private boolean isBase;
        private boolean isTarget;

        public boolean isTarget() {
            return isTarget;
        }

        public int getCoinId() {
            return coinId;
        }


        public String getCoinSymbol() {
            return coinSymbol;
        }

        public String getCoinUrl() {
            return coinUrl;
        }

        public double getCoinPriceUsdt() {
            try {
                return Double.parseDouble(coinPriceUsdt);
            } catch (Exception e) {
                return 0.0;
            }
        }


        public boolean isBase() {
            return isBase;
        }
    }
}
