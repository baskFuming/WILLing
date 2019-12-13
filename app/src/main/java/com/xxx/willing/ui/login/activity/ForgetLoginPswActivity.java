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

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.config.MatchesConfig;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.utils.DownTimeUtil;
import com.xxx.willing.model.utils.KeyBoardUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.login.area.AreaCodeModel;
import com.xxx.willing.ui.login.area.SelectPhoneCode;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 忘记密码页
 * @Author xxx
 */
public class ForgetLoginPswActivity extends BaseTitleActivity {

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
    protected String initTitle() {
        return getString(R.string.forget_title);
    }

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


    @OnClick({R.id.forget_login_psw_selector_phone, R.id.forget_login_psw_send_sms_code, R.id.forget_login_psw_btn, R.id.forget_login_psw_password_eye, R.id.forget_login_psw_password_again_eye})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.forget_login_psw_selector_phone:
                SelectPhoneCode.with(ForgetLoginPswActivity.this).select();
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
        if (resultCode == ChoiceActivity.resultCode) {
            if (data != null) {
                AreaCodeModel model = (AreaCodeModel) data.getSerializableExtra(ChoiceActivity.DATAKEY);
                area = model.getTel();
                mSelectorCounty.setText("+ " + area);
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
            ToastUtil.showToast(R.string.login_error_phone_1);
            showEditError(mAccountEdit);
            return;
        }
        if (!account.matches(MatchesConfig.MATCHES_PHONE)) {
            ToastUtil.showToast(R.string.login_error_phone_2);
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
     * @Model 忘记密码
     */
    private void updatePsw() {
        final String account = mAccountEdit.getText().toString();
        String smsCode = mSMSCodeEdit.getText().toString();
        final String password = mPasswordEdit.getText().toString();
        String passwordAgain = mPasswordAgainEdit.getText().toString();

        if (account.isEmpty()) {
            ToastUtil.showToast(R.string.login_error_phone_1);
            showEditError(mAccountEdit);
            return;
        }
        if (!account.matches(MatchesConfig.MATCHES_PHONE)) {
            ToastUtil.showToast(R.string.login_error_phone_2);
            showEditError(mSMSCodeEdit);
            return;
        }
        if (smsCode.isEmpty()) {
            ToastUtil.showToast(R.string.login_error_code_1);
            showEditError(mSMSCodeEdit);
            return;
        }
        if (!smsCode.matches(MatchesConfig.MATCHES_SMS_CODE)) {
            ToastUtil.showToast(R.string.login_error_code_2);
            showEditError(mPasswordEdit);
            return;
        }
        if (password.isEmpty()) {
            ToastUtil.showToast(R.string.login_error_psw_1);
            showEditError(mPasswordEdit);
            return;
        }
        if (!password.matches(MatchesConfig.MATCHES_PASSWORD)) {
            ToastUtil.showToast(R.string.login_error_psw_2);
            showEditError(mPasswordEdit);
            return;
        }
        if (!password.equals(passwordAgain)) {
            ToastUtil.showToast(R.string.login_error_psw_again_1);
            showEditError(mPasswordEdit, mPasswordAgainEdit);
            return;
        }

        Api.getInstance().forgetPsw(account, password, smsCode, area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {

                    @Override
                    public void onSuccess(BaseBean<Object> bean) {
                        if (bean != null) {
                            ToastUtil.showToast(getString(R.string.forget_success));
                            Intent intent = new Intent(ForgetLoginPswActivity.this, LoginActivity.class);
                            intent.putExtra("account", account);
                            setResult(UIConfig.RESULT_CODE, intent);
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
