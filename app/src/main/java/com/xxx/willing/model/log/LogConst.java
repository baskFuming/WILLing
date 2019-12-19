package com.xxx.willing.model.log;


import com.xxx.willing.BuildConfig;

public class LogConst {

    //网络请求的Log日志拦截
    public static final String HTTP_TAG = BuildConfig.APPLICATION_ID + "_http";

    //用户授权的Log日志拦截
    public static final String PERMISSION_TAG = BuildConfig.APPLICATION_ID + "_permission";

    //初始化App网络请求
    public static final String INIT_HTTP_TAG = BuildConfig.APPLICATION_ID + "_initHttp";

    //webSocket日志拦截
    public static final String WEB_SOCKET_TAG = BuildConfig.APPLICATION_ID + "_webSocket";

    //图片获取日志拦截
    public static final String IMAGE_TAG = BuildConfig.APPLICATION_ID + "_image";

    //网络请求的Log日志拦截
    public static final String SHOP_JS_TAG = BuildConfig.APPLICATION_ID + "_shop_js";
}
