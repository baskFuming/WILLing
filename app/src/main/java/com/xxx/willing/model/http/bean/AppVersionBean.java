package com.xxx.willing.model.http.bean;

import java.util.List;

public class AppVersionBean {

    /**
     * app_name : noah.apk
     * flag : 1
     * version_code : 1.0.0
     * update_msg : ["1. 测试版本"]
     * url : http://noahglobal.me/download/index.html
     */

    private String app_name;
    private int flag;
    private String version_code;
    private String url;
    private List<String> update_msg;

    public String getVersion() {
        return version_code;
    }

    public String getDownloadUrl() {
        return url;
    }

}