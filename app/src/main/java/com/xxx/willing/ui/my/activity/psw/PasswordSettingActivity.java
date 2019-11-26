package com.xxx.willing.ui.my.activity.psw;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.ui.my.activity.AccountSettingActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Page 密码设置页
 * @Author xxx
 */
public class PasswordSettingActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, PasswordSettingActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.password_setting_pay_text)
    TextView mPayText;

    private boolean isHavePayPassword;

    @Override
    protected String initTitle() {
        return getString(R.string.password_setting_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_password_setting;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        isHavePayPassword = SharedPreferencesUtil.getInstance().getBoolean(SharedConst.IS_SETTING_PAY_PSW);
        if (isHavePayPassword) {
            mPayText.setText(R.string.password_setting_modify_pay_psw);
        } else {
            mPayText.setText(R.string.password_setting_setting_pay_psw);
        }
    }

    @OnClick({R.id.password_setting_modify_login, R.id.password_setting_modify_pay})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.password_setting_modify_login:
                ModifyLoginPswActivity.actionStart(this);
                break;
            case R.id.password_setting_modify_pay:
                if (isHavePayPassword) {
                    ModifyPayPswActivity.actionStart(this);
                } else {
                    SettingPayPswActivity.actionStart(this);
                }
                break;
        }
    }
}
