package com.xxx.willing.model.http.bean;

public class UserInfo {

    /**
     * star : 0
     * investCode : 123123
     * telphone : 15100000000
     * nickname : 15100000000
     * inviter : null
     * avatar : null
     * areacode : 86
     */

    private int star;
    private String investCode;
    private String telphone;
    private String nickname;
    private String avatar;
    private int franStatus;
    private int franId;
    private String areacode;
    private boolean isSettingPayPsw;
    private boolean isSettingAddress;

    public boolean isSettingAddress() {
        return isSettingAddress;
    }

    public boolean isSettingPayPsw() {
        return isSettingPayPsw;
    }

    public int getFranStatus() {
        return franStatus;
    }

    public int getFranId() {
        return franId;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getInvestCode() {
        return investCode;
    }

    public void setInvestCode(String investCode) {
        this.investCode = investCode;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }
}
