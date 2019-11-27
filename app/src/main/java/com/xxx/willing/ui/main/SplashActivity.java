package com.xxx.willing.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xxx.willing.ConfigClass;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //是否登录
        isLogin = SharedPreferencesUtil.getInstance().getBoolean(SharedConst.IS_LOGIN);

        // 利用消息处理器实现延迟跳转到登录窗口
        mHandler.sendEmptyMessageDelayed(0, ConfigClass.SPLASH_DELAY_TIME);
    }

    private void checkUI() {
//        if (isLogin) {
//            MainActivity.actionStart(this);
//        } else {
//            LoginActivity.actionStart(this);
//        }
//        finish();

        MainActivity.actionStart(this);
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
                if (activity != null) {
                    activity.checkUI();
                }
            }
        }
    }
}
