package com.xxx.willing;

import android.widget.CompoundButton;

public class ConfigClass {
    /**
     * 服务器地址
     */
//    public static final String BASE_URL = "https://noahglobal.me/";   //正式服务器地址
//    public static final String BASE_URL_PATH = "/NoahWallet";

    public static final String BASE_URL = "http://192.168.31.95:8080";   //测试服务器地址
    public static final String BASE_URL_PATH = "";

    /**
     * 正则判断区域
     */
    public static final String MATCHES_PHONE = "^\\d{11}$";  //手机号规则
    public static final String MATCHES_PASSWORD = "^(?=.*\\d)(?=.*[a-zA-Z])[\\da-zA-Z]{8,20}$";  //密码规则 必须同时包含字母和数字  而且是8-20位
    public static final String MATCHES_SMS_CODE = "^\\d{6}$";//验证码规则
    public static final String MATCHES_JY_PASSWORD = "^\\d{6}$";//交易密码规则

    /**
     * 网络请求基本参数配置
     */
    public static final String USE_HELP_URL = BASE_URL + "/download/CommonProblems-cn.html?lang=";   //使用帮助地址
    public static final String INVITE_URL = BASE_URL + "/download/register.html";   //邀请好友地址
    public static final String IMAGE_URL = BASE_URL + "/CT/"; //图片地址
    public static final long CACHE_TIME = 2 * 60 * 60; //缓存时间 毫秒
    public static final long CACHE_SIZE = 100 * 1024 * 1024; //缓存大小 Bit
    public static final long HTTP_TIME_OUT = 100 * 1000; //网络请求超时时间配置 毫秒
    public static final String HTTP_CONVERSION = "conversion"; //转向Base标注

    /**
     * EventBus传递类型
     */
    public static final String EVENT_LANGUAGE_TAG = "event_language_tag";   //语言切换传递
    public static final String EVENT_UPDATE_WALLET = "event_update_wallet";   //更新资产
    public static final String EVENT_LOGIN = "event_login";   //登录成功
    public static final String EVENT_UPDATE_NODE = "event_update_node";   //更新节点
    public static final String EVENT_UPDATE_SHOP = "event_update_shop";   //商品购买


    public static final String APP_NAME = "minine";   //app名称
    public static final String SP_NAME = APP_NAME + "_sp";   //SP名称

    /**
     * UI页面基础参数配置
     */
    public static final int PAGE_SIZE = 20; //分页加载的大小
    public static final int PAGE_SIZE_MAX = 9999; //分页加载的大小
    public static final int PAGE_DEFAULT = 1; //分页默认的起点 0
    public static final int REQUEST_CODE = 1; //跳转页面的code
    public static final int RESULT_CODE = 100; //关闭页面返回的
    public static final int SMS_CODE_DOWN_TIME = 60; //短信验证码倒计时时间 秒
    public static final int SPLASH_DELAY_TIME = 3 * 1000 + 500;  //闪屏页倒计时
    public static final int DOUBLE_AMOUNT_NUMBER = 4; //小数量位数

    /**
     * 单配
     */
    public static final int LOGIN_RESULT_CODE = 102; //关闭页面返回的
    public static final int DEPOSIT_IN_RESULT_CODE = 103; //关闭页面返回的
    public static final int DEPOSIT_OUT_RESULT_CODE = 104; //关闭页面返回的
    
    public static final String APP_ETH_BROWSER = "https://ethplorer.io/";   //ETH浏览器
    public static final String APP_BTC_BROWSER = "https://m.btc.com/";    //比特币浏览器
    public static final String APP_EOS_BROWSER = "https://eospark.com/";    //比特币浏览器


}
