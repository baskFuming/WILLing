package com.xxx.willing.model.http.bean;

import java.util.List;

/**
 * Time: ${Date}
 * Author : mac
 * Des:
 */
public class RankBean {

    /**
     * amount : 101000.0
     * nickname : 18800000000
     * ranking : null
     * avatar : null
     */

    private double amount;
    private String nickname;
    private String ranking;
    private String avatar;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
