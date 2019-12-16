package com.xxx.willing.model.http.bean;

import java.util.List;

public class SignInfoBean {

    private List<ListBean> list;

    private boolean isTodaySign;

    private Integer signProgress;

    public Integer getSignProgress() {
        return signProgress;
    }

    public List<ListBean> getList() {
        return list;
    }

    public boolean isTodaySign() {
        return isTodaySign;
    }

    public static class ListBean {

        private int day;

        private int status; //1签到 0未签到

        public int getStatus() {
            return status;
        }

        public int getDay() {
            return day;
        }
    }

}
