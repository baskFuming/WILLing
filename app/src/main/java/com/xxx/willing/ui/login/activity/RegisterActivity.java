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
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.model.utils.DownTimeUtil;
import com.xxx.willing.model.utils.KeyBoardUtil;
import com.xxx.willing.model.utils.LocalManageUtil;
import com.xxx.willing.model.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 注册页面
 * @Author xxx
 */
public class RegisterActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.register_account_edit)
    EditText mAccountEdit;
    @BindView(R.id.register_sms_code_edit)
    EditText mSMSCodeEdit;
    @BindView(R.id.register_password_edit)
    EditText mPasswordEdit;

    @BindView(R.id.register_selector_phone)
    TextView mSelectorCounty;
    @BindView(R.id.register_send_sms_code)
    TextView mSMSCodeText;

    @BindView(R.id.register_password_eye)
    CheckBox mPasswordEye;

    private DownTimeUtil mDownTimeUtil;
    private String area = "86";

    @Override
    protected String initTitle() {
        return getString(R.string.register_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mDownTimeUtil = DownTimeUtil.getInstance();
        KeyBoardUtil.closeKeyBord(this, mPasswordEdit);
    }

    @OnClick({R.id.register_account_return, R.id.register_selector_phone,
            R.id.register_password_eye, R.id.register_send_sms_code,
            R.id.register_btn, R.id.switch_language, R.id.register_login, R.id.register_forger_password})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.register_login:
                LoginActivity.actionStart(this);
                break;
            case R.id.register_forger_password:
                ForgetLoginPswActivity.actionStart(this);
                break;
            case R.id.register_account_return:
                finish();
                break;
            case R.id.register_selector_phone:
                SelectCountyActivity.actionStart(this);
                break;
            case R.id.register_password_eye:
                KeyBoardUtil.setInputTypePassword(mPasswordEye.isChecked(), mPasswordEdit);
                break;
            case R.id.register_send_sms_code:
                sendSMSCode();
                break;
            case R.id.register_btn:
                register();
                break;
            case R.id.switch_language:
                String nowLanguage = SharedPreferencesUtil.getInstance().getString(SharedConst.CONSTANT_LAUNCHER);
                switch (nowLanguage) {
                    case LocalManageUtil.LANGUAGE_CN:
                        SharedPreferencesUtil.getInstance().saveString(SharedConst.CONSTANT_LAUNCHER, LocalManageUtil.LANGUAGE_CN);
                        EventBus.getDefault().post(ConfigClass.EVENT_LANGUAGE_TAG);
                        break;
                    case LocalManageUtil.LANGUAGE_US:
                        SharedPreferencesUtil.getInstance().saveString(SharedConst.CONSTANT_LAUNCHER, LocalManageUtil.LANGUAGE_US);
                        EventBus.getDefault().post(ConfigClass.EVENT_LANGUAGE_TAG);
                        break;
                }
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
        KeyBoardUtil.closeKeyBord(this, mAccountEdit);
        if (mDownTimeUtil != null) {
            mDownTimeUtil.closeDownTime();
        }
        super.onDestroy();
    }

    /**
     * @Model 发送短信验证码
     */
    private void sendSMSCode() {
        String account = mAccountEdit.getText().toString();
        if (account.isEmpty()) {
            ToastUtil.showToast(getString(R.string.register_error_1));
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
                                if (mSMSCodeText != null)
                                    mSMSCodeText.setText(nowTime + "s");
                            }

                            @Override
                            public void end() {
                                if (mSMSCodeText != null)
                                    mSMSCodeText.setText(getString(R.string.register_send_sms_code));
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
     * @Model 注册
     */
    private void register() {
        final String account = mAccountEdit.getText().toString();
        String smsCode = mSMSCodeEdit.getText().toString();
        final String password = mPasswordEdit.getText().toString();

        if (account.isEmpty()) {
            ToastUtil.showToast(getString(R.string.register_error_1));
            showEditError(mAccountEdit);
            return;
        }
        if (!account.matches(ConfigClass.MATCHES_PHONE)) {
            ToastUtil.showToast(getString(R.string.login_error_3));
            showEditError(mAccountEdit);
            return;
        }
        if (smsCode.isEmpty()) {
            ToastUtil.showToast(getString(R.string.register_error_2));
            showEditError(mSMSCodeEdit);
            return;
        }
        if (!smsCode.matches(ConfigClass.MATCHES_SMS_CODE)) {
            ToastUtil.showToast(getString(R.string.register_error_5));
            showEditError(mSMSCodeEdit);
            return;
        }
        if (password.isEmpty()) {
            ToastUtil.showToast(getString(R.string.register_error_3));
            showEditError(mPasswordEdit);
            return;
        }
        if (!password.matches(ConfigClass.MATCHES_PASSWORD)) {
            ToastUtil.showToast(getString(R.string.register_error_6));
            showEditError(mPasswordEdit);
            return;
        }

        Api.getInstance().register(account, password, smsCode, area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {
                    @Override
                    public void onSuccess(BaseBean<Object> bean) {
                        ToastUtil.showToast(bean.getMessage());
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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
