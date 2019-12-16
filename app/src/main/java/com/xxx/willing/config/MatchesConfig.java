package com.xxx.willing.config;

public class MatchesConfig {

    /**
     * 正则判断区域
     */
    public static final String MATCHES_PHONE = "^\\d{11}$";  //手机号规则
    public static final String MATCHES_PASSWORD = "^(?=.*\\d)(?=.*[a-zA-Z])[\\da-zA-Z]{8,20}$";  //密码规则 必须同时包含字母和数字  而且是8-20位
    public static final String MATCHES_SMS_CODE = "^\\d{6}$";//验证码规则
    public static final String MATCHES_JY_PASSWORD = "^(?=.*\\d)(?=.*[a-zA-Z])[\\da-zA-Z]{8,20}$";//交易密码规则 必须同时包含字母和数字  而且是8-20位

    //输入字母数字下划线汉字
    public static final String MATCHES_DW_PASSWORD = "^[\\w\\u4e00-\\u9fa5]+$";

}
