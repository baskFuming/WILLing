package com.xxx.willing.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.config.EventBusConfig;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.model.utils.LocalManageUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class LanguageActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, LanguageActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.language_simple_zh_check)
    TextView mSimpleZh;
    @BindView(R.id.language_en_check)
    TextView mEn;

    @Override
    protected String initTitle() {
        return getString(R.string.language_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_language;
    }

    @Override
    protected void initData() {
        String nowLanguage = SharedPreferencesUtil.getInstance().getString(SharedConst.CONSTANT_LAUNCHER);
        switch (nowLanguage) {
            case LocalManageUtil.LANGUAGE_CN:
                mSimpleZh.setText("√");
                mEn.setText("");
                break;
            case LocalManageUtil.LANGUAGE_US:
                mSimpleZh.setText("");
                mEn.setText("√");
                break;
        }
    }

    @OnClick({R.id.language_simple_zh, R.id.language_en})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.language_simple_zh:
                mSimpleZh.setText("√");
                mEn.setText("");
                SharedPreferencesUtil.getInstance().saveString(SharedConst.CONSTANT_LAUNCHER, LocalManageUtil.LANGUAGE_CN);
                EventBus.getDefault().post(EventBusConfig.EVENT_LANGUAGE_TAG);
                break;
            case R.id.language_en:
                mSimpleZh.setText("");
                mEn.setText("√");
                SharedPreferencesUtil.getInstance().saveString(SharedConst.CONSTANT_LAUNCHER, LocalManageUtil.LANGUAGE_US);
                EventBus.getDefault().post(EventBusConfig.EVENT_LANGUAGE_TAG);
                break;
        }
    }
}
