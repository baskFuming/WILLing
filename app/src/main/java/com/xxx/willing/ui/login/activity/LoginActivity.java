package com.xxx.willing.ui.login.activity;

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
import com.xxx.willing.base.activity.ActivityManager;
import com.xxx.willing.base.activity.BaseActivity;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.LoginBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.model.utils.KeyBoardUtil;
import com.xxx.willing.model.utils.LocalManageUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.main.MainActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 登录页面
 * @Author xxx
 */
public class LoginActivity extends BaseActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.login_selector_phone)
    TextView mSelectorPhone;
    @BindView(R.id.login_account_edit)
    EditText mAccountEdit;
    @BindView(R.id.login_password_edit)
    EditText mPasswordEdit;
    @BindView(R.id.login_password_eye)
    CheckBox mPasswordEye;

    private String area = "86";
    private String language;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //保存记录
        String phone = SharedPreferencesUtil.getInstance().getString(SharedConst.VALUE_USER_PHONE);
        mAccountEdit.setText(phone);
        KeyBoardUtil.closeKeyBord(this, mAccountEdit);
    }

    @OnClick({R.id.login_return, R.id.login_password_eye, R.id.login_register, R.id.login_forger_password, R.id.login_btn, R.id.switch_language
            , R.id.login_selector_phone})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.login_return:
                finish();
                break;
            case R.id.login_password_eye:
                KeyBoardUtil.setInputTypePassword(mPasswordEye.isChecked(), mPasswordEdit);
                break;
            case R.id.login_forger_password:
                startActivityForResult(new Intent(this, ForgetLoginPswActivity.class), ConfigClass.REQUEST_CODE);
                break;
            case R.id.login_register:
                startActivityForResult(new Intent(this, RegisterActivity.class), ConfigClass.REQUEST_CODE);
                break;
            case R.id.login_btn:
                login();
                break;
            case R.id.switch_language:
                String nowLanguage = SharedPreferencesUtil.getInstance().getString(SharedConst.CONSTANT_LAUNCHER);
                if (nowLanguage.equals(LocalManageUtil.LANGUAGE_CN)) {
                    SharedPreferencesUtil.getInstance().saveString(SharedConst.CONSTANT_LAUNCHER, LocalManageUtil.LANGUAGE_US);
                    EventBus.getDefault().post(ConfigClass.EVENT_LANGUAGE_TAG);
                } else if (nowLanguage.equals(LocalManageUtil.LANGUAGE_US)) {
                    SharedPreferencesUtil.getInstance().saveString(SharedConst.CONSTANT_LAUNCHER, LocalManageUtil.LANGUAGE_CN);
                    EventBus.getDefault().post(ConfigClass.EVENT_LANGUAGE_TAG);
                }
                break;
            case R.id.login_selector_phone:
                SelectCountyActivity.actionStart(this);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        KeyBoardUtil.closeKeyBord(this, mAccountEdit, mPasswordEdit);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ConfigClass.RESULT_CODE) {
            if (data != null) {
                String name = data.getStringExtra(SelectCountyActivity.RESULT_NAME_KRY);
                area = data.getStringExtra(SelectCountyActivity.RESULT_CODE_KRY);
                mSelectorPhone.setText(name);
            }
        }
    }

    @Override
    public void onBackPressed() {
        ActivityManager.getInstance().AppExit();
    }


    /**
     * @Model 登录
     */
    private void login() {
        final String account = mAccountEdit.getText().toString();
        final String password = mPasswordEdit.getText().toString();

        if (account.isEmpty()) {
            ToastUtil.showToast(getString(R.string.login_error_1));
            showEditError(mAccountEdit);
            return;
        }
        if (!account.matches(ConfigClass.MATCHES_PHONE)) {
            ToastUtil.showToast(getString(R.string.register_error_4));
            showEditError(mAccountEdit);
            return;
        }
        if (password.isEmpty()) {
            ToastUtil.showToast(getString(R.string.login_error_2));
            showEditError(mPasswordEdit);
            return;
        }
        if (!password.matches(ConfigClass.MATCHES_PASSWORD)) {
            ToastUtil.showToast(getString(R.string.login_error_4));
            showEditError(mPasswordEdit);
            return;
        }

        Api.getInstance().login(account, password, area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<LoginBean>(this) {
                    @Override
                    public void onSuccess(BaseBean<LoginBean> bean) {
                        if (bean != null) {
                            LoginBean data = bean.getData();
                            if (data != null) {
                                ToastUtil.showToast(bean.getMessage());
                                SharedPreferencesUtil util = SharedPreferencesUtil.getInstance();
                                util.saveString(SharedConst.VALUE_USER_PHONE, account);
//                                util.saveString(SharedConst.VALUE_USER_NAME, data.getUsername());
                                util.saveString(SharedConst.VALUE_INVITE_CODE, data.getPromotionCode());
                                util.saveString(SharedConst.VALUE_USER_ID, String.valueOf(data.getId()));
                                util.saveBoolean(SharedConst.IS_LOGIN, true);
                                LoginBean.CountryBean country = data.getCountry();
                                if (country != null) {
                                    util.saveString(SharedConst.VALUE_COUNTY_CITY, country.getZhName());
                                }
                                //x-token
                                util.saveString(SharedConst.ENCRYPT_VALUE_TOKEN, data.getToken());

                                //判断下是否进入过首页
                                Activity activity = ActivityManager.getInstance().getActivity(MainActivity.class.getName());
                                if (activity != null) {
                                    //发送eventBus
                                    EventBus.getDefault().post(ConfigClass.EVENT_LOGIN);
                                    finish();
                                } else {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
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
                        hideLoading();
                    }
                });
    }
}
