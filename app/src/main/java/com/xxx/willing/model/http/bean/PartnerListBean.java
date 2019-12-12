package com.xxx.willing.model.http.bean;

import java.io.Serializable;

/**
 * Time: ${Date}
 * Author : mac
 * Des:
 */
public class PartnerListBean implements Serializable {
    /**
     * id : 1
     * name : 全部
     * price : 0.0
     * ratio : 0.005
     * newRatio : 0.0
     * createTime : 2019-12-10 15:15:33
     * lastUpdateTime : 2019-12-10 15:15:33
     */

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
