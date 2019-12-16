package com.xxx.willing.ui.login.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.config.MatchesConfig;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.model.utils.DownTimeUtil;
import com.xxx.willing.model.utils.KeyBoardUtil;
import com.xxx.willing.model.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 设置支付密码页
 * @Author xxx
 */
public class SettingPayPswActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, SettingPayPswActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.setting_pay_psw_password_edit)
    EditText mPasswordEdit;
    @BindView(R.id.setting_pay_psw_password_again_edit)
    EditText mPasswordAgainEdit;
    @BindView(R.id.setting_pay_psw_sms_code_edit)
    EditText mSmsCodeEdit;
    @BindView(R.id.setting_pay_psw_send_sms_code)
    TextView mSendSMSCode;


    @BindView(R.id.setting_pay_psw_password_eye)
    CheckBox mPasswordEye;
    @BindView(R.id.setting_pay_psw_password_again_eye)
    CheckBox mPasswordAgainEye;

    private DownTimeUtil mDownTimeUtil;

    @Override
    protected String initTitle() {
        return getString(R.string.setting_pay_psw_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_pay_psw;
    }

    @Override
    protected void initData() {
        mDownTimeUtil = DownTimeUtil.getInstance();
    }

    @OnClick({R.id.setting_pay_psw_btn, R.id.setting_pay_psw_password_eye, R.id.setting_pay_psw_password_again_eye, R.id.img_edit_cancel, R.id.setting_pay_psw_send_sms_code})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.setting_pay_psw_password_eye:
                KeyBoardUtil.setInputTypePassword(mPasswordEye.isChecked(), mPasswordEdit);
                break;
            case R.id.setting_pay_psw_password_again_eye:
                KeyBoardUtil.setInputTypePassword(mPasswordAgainEye.isChecked(), mPasswordAgainEdit);
                break;
            case R.id.setting_pay_psw_btn:
                updatePsw();
                break;
            case R.id.img_edit_cancel:
                mPasswordAgainEdit.setText("");
                break;
            case R.id.setting_pay_psw_send_sms_code:
                sendSMSCode();
                break;

        }
    }

    @Override
    public void finish() {
        KeyBoardUtil.closeKeyBord(this, mPasswordEdit);
        KeyBoardUtil.closeKeyBord(this, mSmsCodeEdit);
        if (mDownTimeUtil != null) {
            mDownTimeUtil.closeDownTime();
        }
        super.finish();
    }

    /**
     * @Model 发送修改密码短信验证码
     */
    private void sendSMSCode() {
        Api.getInstance().sendUpdateSMSCode()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {
                    @Override
                    public void onSuccess(BaseBean<Object> bean, boolean todaySign) {
                        ToastUtil.showToast(bean.getMessage());
                        mDownTimeUtil.openDownTime(UIConfig.SMS_CODE_DOWN_TIME, new DownTimeUtil.Callback() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void run(int nowTime) {
                                if (mSendSMSCode != null)
                                    mSendSMSCode.setText(nowTime + "s");
                            }

                            @Override
                            public void end() {
                                if (mSendSMSCode != null)
                                    mSendSMSCode.setText(R.string.login_send_sms_code_btn);
                            }
                        });
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
                        hideLoading();
                    }
                });
    }

    /**
     * @Model 设置支付密码
     */
    private void updatePsw() {
        String newPassword = mPasswordEdit.getText().toString();
        String newPasswordAgain = mPasswordAgainEdit.getText().toString();

        if (newPassword.isEmpty()) {
            ToastUtil.showToast(R.string.login_error_psw_1);
            showEditError(mPasswordEdit);
            return;
        }
        if (!newPassword.matches(MatchesConfig.MATCHES_JY_PASSWORD)) {
            ToastUtil.showToast(R.string.login_error_psw_2);
            showEditError(mPasswordEdit);
            return;
        }
        if (!newPassword.equals(newPasswordAgain)) {
            ToastUtil.showToast(R.string.login_error_psw_again_1);
            showEditError(mPasswordEdit, mPasswordAgainEdit);
            return;
        }

        Api.getInstance().settingPayPsw(newPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {

                    @Override
                    public void onSuccess(BaseBean<Object> bean, boolean todaySign) {
                        if (bean != null) {
                            SharedPreferencesUtil.getInstance().saveBoolean(SharedConst.IS_SETTING_PAY_PSW, true);
                            ToastUtil.showToast(bean.getMessage());
                            finish();
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
                        hideLoading();
                    }
                });
    }
}
