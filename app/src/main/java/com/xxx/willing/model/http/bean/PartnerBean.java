package com.xxx.willing.model.http.bean;

import java.util.List;

/**
 * Time: ${Date}
 * Author : mac
 * Des:
 */
public class PartnerBean {


    /**
     * level : 2
     * nickName : 1
     * price : 101500.0
     * avatar : <null>
     * userId : 1
     */

    private int level;
    private String nickName;
    private double price;
    private String avatar;
    private int userId;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;

    }
}
