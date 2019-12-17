package com.xxx.willing.model.http.bean;

import java.util.List;

/**
 * Time: ${Date}
 * Author : mac
 * Des:
 */
public class MyOrderBean {


    /**
     * id : 3
     * userId : 1
     * orderNum : 11
     * commId : 1
     * details : 1111111
     * commNum : 1
     * price : 1111
     * freight : 1
     * express : 1
     * address : 1
     * status : 1
     * createTime : 2019-12-17 15:38:15
     * lastUpdateTime : 2019-12-17 15:38:15
     */

    private int id;
    private int userId;
    private String orderNum;
    private int commId;
    private String details;
    private int commNum;
    private int price;
    private int freight;
    private String express;
    private String address;
    private int status;
    private String createTime;
    private String lastUpdateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getCommId() {
        return commId;
    }

    public void setCommId(int commId) {
        this.commId = commId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getCommNum() {
        return commNum;
    }

    public void setCommNum(int commNum) {
        this.commNum = commNum;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFreight() {
        return freight;
    }

    public void setFreight(int freight) {
        this.freight = freight;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
