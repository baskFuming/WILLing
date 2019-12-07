package com.xxx.willing.model.http.utils;

/**
 * 网络请求 返回值type
 */
public class ApiType {

    public static final String HOME_LOCATION = "0";   //轮播图位置
    //地址管理默认地址标识
    public static final int FLAG_ADDRESS_DEFAULT = 1;

    //转账 收款类型
    public static final int TRANSFER_TYPE = 0;
    public static final int RECHARGE_TYPE = 1;
    //转账状态
    public static final int TRANSFER_WAIT_STATUS = 0;   //等待打包
    public static final int RECHARGE_SUCCESS_STATUS = 1;       //交易成功
    public static final int TRANSFER_FAIL_TYPE = 2;          //交易失败

    //合伙人列表
    public static final int PARTNER_LIST_ALL = 0;  //全部
    public static final int PARTNER_LIST_AERA_ALL = 1;  //区级合伙人
    public static final int PARTNER_LIST_CITY_ALL = 2;  //市级合伙人
    public static final int PARTNER_LIST_DIRECTOR_ALL = 4;  //董事

    //订单交易状态
    public static final int ORDER_COMMUNITY_ALL = 0;  //全部
    public static final int ORDER_COMMUNITY_UNPAID = 1;    //待付款
    public static final int ORDER_COMMUNITY_DELIVERY = 2;   //待发货
    public static final int ORDER_COMMUNITY_GOODS = 3;   //待收货
    public static final int ORDER_COMMUNITY_COMPLAINING = 4; //已收货

    //订单交易状态
    public static final int WALLET_RELEASE_TYPE_1 = 1;  //投票释放
    public static final int WALLET_RELEASE_TYPE_2 = 2;    //团队投票收益
    public static final int WALLET_RELEASE_TYPE_3 = 3;   //合伙人收益
    public static final int WALLET_RELEASE_TYPE_4 = 4;   //加盟店收益
    public static final int WALLET_RELEASE_TYPE_5 = 5;  //品牌收益
}