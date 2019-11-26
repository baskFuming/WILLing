package com.xxx.willing.model.http.utils;

/**
 * 网络请求 需要特殊处理的返回值code
 */
public class ApiCode {

    public static final int RESPONSE_CODE = 0;          //响应成功码
    public static final int SERVICE_ERROR_CODE = 500;          //服务器异常码


    /**
     * 特殊处理
     */
    public static final int TOKEN_INVALID = 100001; //UC接口Token失效

    public static final int PAY_NOT_SETTING = -1063; //未设置交易密码

}
