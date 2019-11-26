package com.xxx.willing.ui.login.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.xxx.willing.ConfigClass;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseActivity;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.utils.DownTimeUtil;
import com.xxx.willing.model.utils.KeyBoardUtil;
import com.xxx.willing.model.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 忘记密码页
 * @Author xxx
 */
public class ForgetLoginPswActivity extends BaseActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, ForgetLoginPswActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.forget_login_psw_account_edit)
    EditText mAccountEdit;
    @BindView(R.id.forget_login_psw_sms_code_edit)
    EditText mSMSCodeEdit;
    @BindView(R.id.forget_login_psw_password_edit)
    EditText mPasswordEdit;
    @BindView(R.id.forget_login_psw_password_again_edit)
    EditText mPasswordAgainEdit;

    @BindView(R.id.forget_login_psw_send_sms_code)
    TextView mSendSMSCode;
    @BindView(R.id.forget_login_psw_selector_phone)
    TextView mSelectorCounty;

    @BindView(R.id.forget_login_psw_password_eye)
    CheckBox mPasswordEye;
    @BindView(R.id.forget_login_psw_password_again_eye)
    CheckBox mPasswordAgainEye;

    private DownTimeUtil mDownTimeUtil;
    private String area = "86";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_login_psw;
    }

    @Override
    protected void initData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mDownTimeUtil = DownTimeUtil.getInstance();
        KeyBoardUtil.closeKeyBord(this, mAccountEdit);
    }


    @OnClick({R.id.forget_login_psw_return, R.id.forget_login_psw_selector_phone, R.id.forget_login_psw_send_sms_code, R.id.forget_login_psw_btn, R.id.forget_login_psw_password_eye, R.id.forget_login_psw_password_again_eye})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.forget_login_psw_return:
                finish();
                break;
            case R.id.forget_login_psw_selector_phone:
                SelectCountyActivity.actionStart(this);
                break;
            case R.id.forget_login_psw_password_eye:
                KeyBoardUtil.setInputTypePassword(mPasswordEye.isChecked(), mPasswordEdit);
                break;
            case R.id.forget_login_psw_password_again_eye:
                KeyBoardUtil.setInputTypePassword(mPasswordAgainEye.isChecked(), mPasswordAgainEdit);
                break;
            case R.id.forget_login_psw_send_sms_code:
                sendSMSCode();
                break;
            case R.id.forget_login_psw_btn:
                updatePsw();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ConfigClass.RESULT_CODE) {
            if (data != null) {
                String name = data.getStringExtra(SelectCountyActivity.RESULT_NAME_KRY);
                area = data.getStringExtra(SelectCountyActivity.RESULT_CODE_KRY);
                mSelectorCounty.setText(name);
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mDownTimeUtil != null) {
            mDownTimeUtil.closeDownTime();
        }
        super.onDestroy();
    }

    /**
     * @Model 发送忘记密码短信验证码
     */
    private void sendSMSCode() {
        String account = mAccountEdit.getText().toString();
        if (account.isEmpty()) {
            ToastUtil.showToast(getString(R.string.forget_login_psw_error_1));
            return;
        }
        if (!account.matches(ConfigClass.MATCHES_PHONE)) {
            ToastUtil.showToast(getString(R.string.forget_login_psw_error_4));
            showEditError(mAccountEdit);
            return;
        }
        Api.getInstance().sendSMSCode(account, area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {

                    @Override
                    public void onSuccess(BaseBean<Object> bean) {
                        ToastUtil.showToast(bean.getMessage());
                        mDownTimeUtil.openDownTime(ConfigClass.SMS_CODE_DOWN_TIME, new DownTimeUtil.Callback() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void run(int nowTime) {
                                if (mSendSMSCode != null)
                                    mSendSMSCode.setText(nowTime + "s");
                            }

                            @Override
                            public void end() {
                                if (mSendSMSCode != null)
                                    mSendSMSCode.setText(getString(R.string.forget_login_psw_send_sms_code));
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
     * @Model 忘记密码
     */
    private void updatePsw() {
        final String account = mAccountEdit.getText().toString();
        String smsCode = mSMSCodeEdit.getText().toString();
        final String password = mPasswordEdit.getText().toString();
        String passwordAgain = mPasswordAgainEdit.getText().toString();

        if (account.isEmpty()) {
            ToastUtil.showToast(getString(R.string.forget_login_psw_error_1));
            showEditError(mAccountEdit);
            return;
        }
        if (!account.matches(ConfigClass.MATCHES_PHONE)) {
            ToastUtil.showToast(getString(R.string.forget_login_psw_error_4));
            showEditError(mSMSCodeEdit);
            return;
        }
        if (smsCode.isEmpty()) {
            ToastUtil.showToast(getString(R.string.forget_login_psw_error_2));
            showEditError(mSMSCodeEdit);
            return;
        }
        if (!smsCode.matches(ConfigClass.MATCHES_SMS_CODE)) {
            ToastUtil.showToast(getString(R.string.forget_login_psw_error_5));
            showEditError(mPasswordEdit);
            return;
        }
        if (password.isEmpty()) {
            ToastUtil.showToast(getString(R.string.forget_login_psw_error_3));
            showEditError(mPasswordEdit);
            return;
        }
        if (!password.matches(ConfigClass.MATCHES_PASSWORD)) {
            ToastUtil.showToast(getString(R.string.forget_login_psw_error_6));
            showEditError(mPasswordEdit);
            return;
        }
        if (!password.equals(passwordAgain)) {
            ToastUtil.showToast(getString(R.string.forget_login_psw_error_7));
            showEditError(mPasswordEdit, mPasswordAgainEdit);
            return;
        }

        Api.getInstance().forgetPsw(account, password, smsCode, area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {

                    @Override
                    public void onSuccess(BaseBean<Object> bean) {
                        ToastUtil.showToast(bean.getMessage());
                        Intent intent = new Intent(ForgetLoginPswActivity.this, LoginActivity.class);
                        intent.putExtra("account", account);
                        setResult(ConfigClass.RESULT_CODE, intent);
                        finish();
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
