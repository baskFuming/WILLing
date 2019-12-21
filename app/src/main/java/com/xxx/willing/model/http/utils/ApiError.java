package com.xxx.willing.model.http.utils;

import android.app.Activity;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.ActivityManager;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.login.activity.LoginActivity;
import com.xxx.willing.ui.main.MainActivity;

public class ApiError {

    /**
     * 服务器返回的错误码处理
     *
     * @param code 状态码
     */
    public static void ServiceCodeErrorFun(int code) {
        switch (code) {
            case ApiCode.TOKEN_INVALID:
                tokenError();
                break;
        }
    }


    /**
     * Token失效跳转页面
     */
    private static void tokenError() {
        //判断是否跳转过去
        SharedPreferencesUtil instance = SharedPreferencesUtil.getInstance();
        boolean isLogin = instance.getBoolean(SharedConst.IS_LOGIN);
        if (isLogin) {
            Activity activity = ActivityManager.getInstance().getActivity(MainActivity.class.getName());
            LoginActivity.actionStart(activity);
            ToastUtil.showToast(R.string.token_error);
        }
        SharedPreferencesUtil.getInstance().cleanAll();
    }
}
