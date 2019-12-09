package com.xxx.willing.model.http.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageBean {

    private String name;

    private String content;

    private String createTime;

    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getCreateTime() {
        return createTime;
    }
}
