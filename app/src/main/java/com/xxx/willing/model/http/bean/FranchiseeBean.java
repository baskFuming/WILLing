package com.xxx.willing.model.http.bean;

import java.util.List;

public class FranchiseeBean {

    /**
     * imgUrl : ["http://img3.imgtn.bdimg.com/it/u=1267490802,3326052181&fm=26&gp=0.jpg","http://img3.imgtn.bdimg.com/it/u=1267490802,3326052181&fm=26&gp=0.jpg"]
     * franName : 优e学山东枣庄大学城店
     * releaseTime : 2019-12-09 19:34:53
     * voteNum : 0
     * releaseStatus : null
     * endTime : 2019-12-29 19:34:53
     * status : 0
     */

    private int id;
    private String imgUrl;
    private String franName;
    private String franId;
    private String detail;
    private String releaseTime;
    private String endTime;
    private String nowTime;
    private int voteNum;
    private int status;

    public int getId() {
        return id;
    }

    public String getDetail() {
        return detail;
    }

    public String getFranName() {
        return franName;
    }

    public String getFranId() {
        return franId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getNowTime() {
        return nowTime;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getVoteNum() {
        return voteNum;
    }

    public int getStatus() {
        return status;
    }
}
