package com.xxx.willing.model.http.utils;

/**
 * 网络请求 返回值type
 */
public class ApiType {

    public static final String HOME_LOCATION = "0";   //轮播图位置
    //地址管理默认地址标识
    public static final int FLAG_ADDRESS_DEFAULT = 1;

    //加盟申请审核成功
    public static final int VOTE_SUCCESS_STATUS = 1;

    //转账状态
    public static final int TRANSFER_WAIT_STATUS = 0;   //等待打包
    public static final int RECHARGE_SUCCESS_STATUS = 1;       //交易成功
    public static final int TRANSFER_FAIL_TYPE = 2;          //交易失败
    public static final int TRANSFER_AUDIT_CURRENCY_TYPE = 3;          //审核放币

    //合伙人列表
    public static final int PARTNER_LIST_ALL = 0;  //全部
    public static final int PARTNER_LIST_AREA_ALL = 1;  //区域合伙人
    public static final int PARTNER_LIST_CITY_ALL = 2;  //市级合伙人
    public static final int PARTNER_LIST_LEVEL_ALL = 3;  //省级合伙人
    public static final int PARTNER_LIST_DIRECTOR_ALL = 4;  //董事
    public static final int PARTNER_LIST_BOSS_ALL = 5;  //总裁


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

    //投票状态
    public static final int VOTE_PROGRESS_STATUE = 1;  //进行中
    public static final int VOTE_SUCCESS_STATUE = 2;    //已授权
    public static final int VOTE_NOT_START_STATUE = 3;   //未开始
    public static final int VOTE_END_STATUE = 4;   //结束

    //资金记录状态
    public static final int ASSET_RECORD_ALL_TYPE = 0;  //全部
    public static final int ASSET_RECORD_RECHARGE_TYPE = 2;  //充值
    public static final int ASSET_RECORD_TRANSFER_TYPE = 1;    //转账
    public static final int ASSET_RECORD_EXCHANGE_TYPE = 3;   //兑换
    public static final int ASSET_RECORD_VOTE_TYPE = 4;   //投票释放
    public static final int ASSET_RECORD_TEAM_VOTE_TYPE = 5;  //团队投票
    public static final int ASSET_RECORD_SIGN_TYPE = 6;  //签到
    public static final int ASSET_RECORD_JOIN_TYPE = 100;    //加盟店 暂无
    public static final int ASSET_RECORD_BRAND_TYPE = 101;   //品牌 暂无
    public static final int ASSET_RECORD_TASK_TYPE = 7;   //任务
    public static final int ASSET_RECORD_RANK_TYPE = 8;   //排名
    public static final int ASSET_RECORD_CEO_TYPE = 9;   //总裁

    //币种
    public static final int ASSET_RECORD_COIN_ALL_TYPE = 0;  //全部
    public static final int ASSET_RECORD_GVI_TYPE = 10000004;  //
    public static final int ASSET_RECORD_BVSE_TYPE = 10000005;    //
    public static final int ASSET_RECORD_BTC_TYPE = 10000001;   //
    public static final int ASSET_RECORD_ETH_TYPE = 10000002;   //
    public static final int ASSET_RECORD_USDT_VOTE_TYPE = 10000003;  //
}