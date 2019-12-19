package com.xxx.willing.model.http.js;

import java.io.Serializable;

public class ShopJsVo implements Serializable{

    private int id;

    private int num;

    private int color;

    private int sizeId;

    private double zgviPrice;

    public double getPrice() {
        return zgviPrice;
    }

    public void setPrice(double price) {
        this.zgviPrice = price;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public int getId() {
        return id;
    }

    public int getColor() {
        return color;
    }

    public int getNum() {
        return num;
    }

    public int getSizeId() {
        return sizeId;
    }
}


