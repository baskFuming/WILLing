package com.xxx.willing.ui.my.activity.psw;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.xxx.willing.ConfigClass;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.utils.DownTimeUtil;
import com.xxx.willing.model.utils.KeyBoardUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.main.SplashActivity;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 修改登录密码页
 * @Author xxx
 */
public class ModifyLoginPswActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, ModifyLoginPswActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.modify_login_psw_sms_code_edit)
    EditText mSMSCodeEdit;
    @BindView(R.id.modify_login_psw_old_password_edit)
    EditText mOldPasswordEdit;
    @BindView(R.id.modify_login_psw_new_password_edit)
    EditText mNewPasswordEdit;
    @BindView(R.id.modify_login_psw_new_password_again_edit)
    EditText mNewPasswordAgainEdit;

    @BindView(R.id.modify_login_psw_send_sms_code)
    TextView mSendSMSCode;

    @BindView(R.id.modify_login_psw_old_password_eye)
    CheckBox mOldPasswordEye;
    @BindView(R.id.modify_login_psw_new_password_eye)
    CheckBox mNewPasswordEye;
    @BindView(R.id.modify_login_psw_new_password_again_eye)
    CheckBox mNewPasswordAgainEye;

    private DownTimeUtil mDownTimeUtil;

    @Override
    protected String initTitle() {
        return getString(R.string.modify_login_psw_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_login_psw;
    }

    @Override
    protected void initData() {
        mDownTimeUtil = DownTimeUtil.getInstance();
    }

    @OnClick({R.id.modify_login_psw_send_sms_code, R.id.modify_login_psw_btn, R.id.modify_login_psw_old_password_eye, R.id.modify_login_psw_new_password_eye, R.id.modify_login_psw_new_password_again_eye, R.id.img_edit_cancel})
    public void OnClick(View view) {
        KeyBoardUtil.closeKeyBord(this, mSMSCodeEdit);
        switch (view.getId()) {
            case R.id.modify_login_psw_old_password_eye:
                KeyBoardUtil.setInputTypePassword(mOldPasswordEye.isChecked(), mOldPasswordEdit);
                break;
            case R.id.modify_login_psw_new_password_eye:
                KeyBoardUtil.setInputTypePassword(mNewPasswordEye.isChecked(), mNewPasswordEdit);
                break;
            case R.id.modify_login_psw_new_password_again_eye:
                KeyBoardUtil.setInputTypePassword(mNewPasswordAgainEye.isChecked(), mNewPasswordAgainEdit);
                break;
            case R.id.modify_login_psw_send_sms_code:
                sendSMSCode();
                break;
            case R.id.modify_login_psw_btn:
                updatePsw();
                break;
            case R.id.img_edit_cancel:
                mNewPasswordAgainEdit.setText("");
                break;
        }
    }

    @Override
    public void finish() {
        KeyBoardUtil.closeKeyBord(this, mSMSCodeEdit);
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
                                    mSendSMSCode.setText(getString(R.string.modify_login_psw_send_sms_code));
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
     * @Model 修改登录密码
     */
    private void updatePsw() {
        String smsCode = mSMSCodeEdit.getText().toString();
        String oldPassword = mOldPasswordEdit.getText().toString();
        String newPassword = mNewPasswordEdit.getText().toString();
        String newPasswordAgain = mNewPasswordAgainEdit.getText().toString();

        if (smsCode.isEmpty()) {
            ToastUtil.showToast(getString(R.string.modify_login_psw_error_1));
            showEditError(mSMSCodeEdit);
            return;
        }
        if (!smsCode.matches(ConfigClass.MATCHES_SMS_CODE)) {
            ToastUtil.showToast(getString(R.string.modify_login_psw_error_5));
            showEditError(mSMSCodeEdit);
            return;
        }

        if (oldPassword.isEmpty()) {
            ToastUtil.showToast(getString(R.string.modify_login_psw_error_2));
            showEditError(mOldPasswordEdit);
            return;
        }
        if (!oldPassword.matches(ConfigClass.MATCHES_PASSWORD)) {
            ToastUtil.showToast(getString(R.string.modify_login_psw_error_6));
            showEditError(mOldPasswordEdit);
            return;
        }

        if (newPassword.isEmpty()) {
            ToastUtil.showToast(getString(R.string.modify_login_psw_error_3));
            showEditError(mNewPasswordEdit);
            return;
        }
        if (!newPassword.matches(ConfigClass.MATCHES_PASSWORD)) {
            ToastUtil.showToast(getString(R.string.modify_login_psw_error_7));
            showEditError(mNewPasswordEdit);
            return;
        }
        if (!newPassword.equals(newPasswordAgain)) {
            ToastUtil.showToast(getString(R.string.modify_login_psw_error_4));
            showEditError(mNewPasswordEdit, mNewPasswordAgainEdit);
            return;
        }

        Api.getInstance().modifyLoginPsw(oldPassword, newPassword, smsCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {

                    @Override
                    public void onSuccess(BaseBean<Object> bean) {
                        ToastUtil.showToast(bean.getMessage());
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
