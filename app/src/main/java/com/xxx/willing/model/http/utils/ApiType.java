package com.xxx.willing.model.http.utils;

/**
 * 网络请求 返回值type
 */
public class ApiType {

    public static final String HOME_LOCATION = "0";   //轮播图位置

    //转账 收款类型
    public static final int TRANSFER_TYPE = 0;
    public static final int RECHARGE_TYPE = 1;
    //转账状态
    public static final int TRANSFER_WAIT_STATUS = 0;   //等待打包
    public static final int RECHARGE_SUCCESS_STATUS = 1;       //交易成功
    public static final int TRANSFER_FAIL_TYPE = 2;          //交易失败
}