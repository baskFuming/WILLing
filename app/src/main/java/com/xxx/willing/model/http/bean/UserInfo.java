package com.xxx.willing.model.http.bean;

public class UserInfo {
    /**
     * node : false
     * userName : 18310005980
     * value : 欢迎来到Noah钱包
     */

    private boolean node;
    private String userName;
    private String value;

    public boolean isNode() {
        return node;
    }

    public void setNode(boolean node) {
        this.node = node;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
