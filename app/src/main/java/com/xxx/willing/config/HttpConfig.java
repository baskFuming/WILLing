package com.xxx.willing.config;

public class HttpConfig {

    /**
     * 正式服务器地址
     */
    public static final String BASE_URL = "http://edtcchain.com/";
    public static final String BASE_URL_PATH = "/willing";
    /**
     * 测试服务器地址
     */
//    public static final String BASE_URL_PATH = "";
//    public static final String BASE_URL = "http://192.168.31.96:8080";

    /**
     * 网络请求基本参数配置
     */
    public static final long CACHE_TIME = 2 * 60 * 60; //缓存时间 毫秒
    public static final long CACHE_SIZE = 100 * 1024 * 1024; //缓存大小 Bit
    public static final long HTTP_TIME_OUT = 100 * 1000; //网络请求超时时间配置 毫秒
    public static final String HTTP_CONVERSION = "conversion"; //转向Base标注

    /**
     * 网页地址
     */
    private static final String WEB_URL = "http://edtcchain.com/phone/#";    //网页地址
    public static final String SHOP_DETAIL_URL = WEB_URL + "/goods";    //商品详情页面
    public static final String USE_HELP_URL = WEB_URL + "/help";                      //使用帮助地址
    public static final String JOIN_HELP_URL = WEB_URL + "/joinin";                   //加盟协议地址
    public static final String USER_AGREEMENT_URL = WEB_URL + "/useragreement";       //用户协议
    public static final String VOTE_URL = WEB_URL + "/vote";                          //投票规则
    public static final String PARTNER_URL = WEB_URL + "/partnership";                //合伙人规则
    public static final String COMPETITION_URL = WEB_URL + "/competition";            //竞赛规则
    public static final String EXCHANGE_URL = WEB_URL + "/exchange";                  //兑换须知
    public static final String TASK_URL = WEB_URL + "/task";                          //任务规则

    /**
     * 配置
     */
    public static final String CALL_MY_CODE = "1231231";                             //联系我们微信号
    public static final String INVITE_URL = WEB_URL + "/download/register.html";    //邀请好友地址
}
