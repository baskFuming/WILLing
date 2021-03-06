package com.xxx.willing.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xxx.willing.R;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.ui.login.activity.LoginActivity;
import com.xxx.willing.ui.main.wheels.GuidePageActivity;

import java.lang.ref.WeakReference;

/**
 * @Page 闪屏页
 * @Author xxx
 */
public class SplashActivity extends AppCompatActivity {

    /**
     * 延迟时间
     */
    private final MyHandler mHandler = new MyHandler(this);

    //是否登录
    private boolean isLogin;

    //判断是否第一次进入
    private boolean isFirst = true;

    private SharedPreferences pref;

    //是否执行了跳转页面
    private boolean isStartActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View decorView = getWindow().getDecorView();
        decorView.post(() -> {
            double width = decorView.getWidth();
            double height = decorView.getHeight();
            if (height / width >= 1.8) {
                decorView.setBackgroundResource(R.mipmap.splash_bg);
            }
        });

        //是否登录
        isLogin = SharedPreferencesUtil.getInstance().getBoolean(SharedConst.IS_LOGIN);
        // 利用消息处理器实现延迟跳转到登录窗口
        mHandler.sendEmptyMessageDelayed(0, UIConfig.SPLASH_DELAY_TIME);
    }

    @SuppressLint("ApplySharedPref")
    private void checkIsFirst() {
        pref = getSharedPreferences(SharedConst.IS_FIRST, MODE_PRIVATE);
        isFirst = pref.getBoolean(SharedConst.IS_FIRST, true);
        SharedPreferences.Editor editor = pref.edit();
        if (isFirst) {
            editor.putBoolean(SharedConst.IS_FIRST, false);
            editor.commit();
            GuidePageActivity.actionStart(this);
        } else {
            editor.putBoolean(SharedConst.IS_FIRST, false);
            editor.commit();
            checkUI();
        }
    }

    private void checkUI() {
        if (isLogin) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            LoginActivity.actionStart(this);
        }
        isStartActivity = true;
        finish();
    }

    private static class MyHandler extends Handler {

        //弱引用对象
        private final WeakReference<SplashActivity> mActivity;

        MyHandler(SplashActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                SplashActivity activity = mActivity.get();
                if (activity != null && !activity.isStartActivity) {
                    activity.checkIsFirst();
                }
            }
        }
    }
}
