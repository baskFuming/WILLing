package com.xxx.willing.model.http.bean;

import java.io.Serializable;
import java.util.List;

public class MyVoteBean implements Serializable{


    /**
     * voteAmount : 20000.0
     * franName : 驱蚊器
     * franNum : QQC2019080888
     * createTime : 2019-12-11 14:21:04
     * franId : 2
     * list : [{"voteAmount":10000,"createTime":"2019-12-11 14:21:04"},{"voteAmount":10000,"createTime":"2019-12-11 14:21:04"}]
     * voteCycle : 90
     */

    private double voteAmount;
    private String franName;
    private String franNum;
    private String createTime;
    private int franId;
    private int voteCycle;
    private List<ListBean> list;

    public double getVoteAmount() {
        return voteAmount;
    }

    public void setVoteAmount(double voteAmount) {
        this.voteAmount = voteAmount;
    }

    public String getFranName() {
        return franName;
    }

    public void setFranName(String franName) {
        this.franName = franName;
    }

    public String getFranNum() {
        return franNum;
    }

    public void setFranNum(String franNum) {
        this.franNum = franNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getFranId() {
        return franId;
    }

    public void setFranId(int franId) {
        this.franId = franId;
    }

    public int getVoteCycle() {
        return voteCycle;
    }

    public void setVoteCycle(int voteCycle) {
        this.voteCycle = voteCycle;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * voteAmount : 10000.0
         * createTime : 2019-12-11 14:21:04
         */

        private double voteAmount;
        private String createTime;

        public double getVoteAmount() {
            return voteAmount;
        }

        public void setVoteAmount(double voteAmount) {
            this.voteAmount = voteAmount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
