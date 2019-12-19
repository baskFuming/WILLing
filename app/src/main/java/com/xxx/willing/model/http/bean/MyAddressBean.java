package com.xxx.willing.model.http.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Time: ${Date}
 * Author : mac
 * Des:
 */
public class MyAddressBean implements Serializable {

    /**
     * id : 14
     * userId : 1
     * consignee : 答卷
     * phone : 18800000000
     * provinces : 北京
     * cities : 北京
     * counties : 北京
     * address : 北京
     * status : null
     * createTime : 2019-12-19 12:19:53
     * lastUpdateTime : 2019-12-19 12:19:53
     */

    private int id;
    private int userId;
    private String consignee;
    private String phone;
    private String provinces;
    private String cities;
    private String counties;
    private String address;
    private Object status;
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

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvinces() {
        return provinces;
    }

    public void setProvinces(String provinces) {
        this.provinces = provinces;
    }

    public String getCities() {
        return cities;
    }

    public void setCities(String cities) {
        this.cities = cities;
    }

    public String getCounties() {
        return counties;
    }

    public void setCounties(String counties) {
        this.counties = counties;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
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
