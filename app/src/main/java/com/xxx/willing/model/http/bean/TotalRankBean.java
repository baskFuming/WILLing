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
     * reward : 5075.0
     * date : 2019-12-22 23:59:59
     * amount : 101500.0
     * list : {"pageNum":1,"pageSize":20,"totalSize":4,"totalPages":1,"list":[{"amount":101000,"nickname":"18800000000","ranking":null,"avatar":null},{"amount":500,"nickname":"18800000001","ranking":null,"avatar":null}],"firstPage":true,"lastPage":true}
     */

    private double reward;
    private String date;
    private double amount;
    private PageBean<RankBean> list;


    public double getReward() {
        return reward;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public PageBean<RankBean> getList() {
        return list;
    }
}
