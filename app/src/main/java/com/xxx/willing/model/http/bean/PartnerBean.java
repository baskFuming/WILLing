package com.xxx.willing.model.http.bean;

import java.util.List;

/**
 * Time: ${Date}
 * Author : mac
 * Des:
 */
public class PartnerBean {
    /**
     * level : 1
     * price : 0
     * avatar : null
     * userId : 1
     */

    private int level;
    private int price;
    private Object avatar;
    private int userId;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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
