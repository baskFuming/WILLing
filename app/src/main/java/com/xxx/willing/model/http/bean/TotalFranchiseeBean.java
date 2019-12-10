package com.xxx.willing.model.http.bean;

import com.xxx.willing.model.http.bean.base.PageBean;

import java.util.List;

public class TotalFranchiseeBean {

    private double totalVoteAmount;
    private double totalVoteRelease;
    private PageBean<FranchiseeBean> bean;

    public double getTotalVoteAmount() {
        return totalVoteAmount;
    }

    public double getTotalVoteRelease() {
        return totalVoteRelease;
    }

    public PageBean<FranchiseeBean> getBean() {
        return bean;
    }
}
