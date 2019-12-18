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
     * nickName : 18800000000
     * price : 101000.0
     * avatar : null
     * userId : 1
     */

    private int level;
    private String nickName;
    private double price;
    private Object avatar;
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

    public Object getAvatar() {
        return avatar;
    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
