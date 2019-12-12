package com.xxx.willing.model.http.bean;

import com.xxx.willing.model.http.bean.base.PageBean;

import java.util.List;

/**
 * Time: ${Date}
 * Author : mac
 * Des:
 */
public class TotalRankBean {

    /**
     * reward : 15.600000000000001
     * amount : 312
     * list : {"pageNum":1,"pageSize":10,"totalSize":4,"totalPages":1,"list":[{"amount":12,"nickname":"15100000010","ranking":null,"avatar":null},{"amount":0,"nickname":"15100000000","ranking":null,"avatar":null},{"amount":0,"nickname":"15100000010","ranking":null,"avatar":null},{"amount":0,"nickname":"15100000020","ranking":null,"avatar":null}],"firstPage":true,"lastPage":true}
     */

    private double reward;
    private int amount;
    private String date;
    private PageBean<RankBean> bean;

    public double getReward() {
        return reward;
    }

    public String getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }

    public PageBean<RankBean> getBean() {
        return bean;
    }

}
