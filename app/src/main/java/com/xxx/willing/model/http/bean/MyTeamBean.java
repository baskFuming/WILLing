package com.xxx.willing.model.http.bean;

import java.util.List;

public class MyTeamBean {

    private double totalAchievement;
    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public double getTotalAchievement() {
        return totalAchievement;
    }


    public static class ListBean {

        private int id;
        private String telphone;
        private String nickname;
        private double achievement;
        private String createTime;

        public String getGviAmount() {
            return gviAmount;
        }

        private String gviAmount;

        public int getId() {
            return id;
        }

        public String getTelphone() {
            return telphone;
        }

        public String getNickname() {
            return nickname;
        }

        public double getAchievement() {
            return achievement;
        }

        public String getCreateTime() {
            return createTime;
        }
    }

}
