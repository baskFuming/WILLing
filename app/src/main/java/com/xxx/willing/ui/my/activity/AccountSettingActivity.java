package com.xxx.willing.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.AppVersionBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.utils.SystemUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.login.activity.PasswordSettingActivity;
import com.xxx.willing.ui.main.UpdateWindow;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 账户设置页
 * @Author xxx
 */
public class AccountSettingActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, AccountSettingActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.account_setting_version_code)
    TextView mVersionCode;

    private String versionName;
    private UpdateWindow instance;


    @Override
    protected String initTitle() {
        return getString(R.string.account_setting_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_setting;
    }

    @Override
    protected void initData() {
        versionName = SystemUtil.getVersionName(this);
        mVersionCode.setText(versionName);
    }

    @OnClick({R.id.account_setting_set_password, R.id.account_setting_address_manager, R.id.account_setting_check_version, R.id.call_my})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.account_setting_set_password:
                PasswordSettingActivity.actionStart(this);
                break;
            case R.id.account_setting_address_manager:   //使用帮助
                UseHelpActivity.actionStart(this);
                break;
            case R.id.call_my:
                CallMeActivity.actionStart(this);//联系我们
                break;
            case R.id.account_setting_check_version:
                checkAppVersion();
                break;
        }
    }

    /**
     * @Model 检查App版本
     */
    private void checkAppVersion() {
        Api.getInstance().checkAppVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<AppVersionBean>(this) {
                    @Override
                    public void onSuccess(BaseBean<AppVersionBean> bean) {
                        if (bean != null) {
                            AppVersionBean data = bean.getData();
                            if (data != null) {
                                String version = data.getVersion();
                                if (versionName.equals(version)) {
                                    ToastUtil.showToast(getString(R.string.check_version_not));
                                } else {
                                    instance = UpdateWindow.getInstance(AccountSettingActivity.this, data.getDownloadUrl());
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        showLoading();
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        instance.dismiss();
                        hideLoading();
                    }
                });
    }
}
