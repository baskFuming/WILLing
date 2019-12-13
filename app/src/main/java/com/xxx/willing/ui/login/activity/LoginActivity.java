package com.xxx.willing.ui.login.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.ActivityManager;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.config.EventBusConfig;
import com.xxx.willing.config.MatchesConfig;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.LoginBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.model.utils.KeyBoardUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.login.area.AreaCodeModel;
import com.xxx.willing.ui.login.area.SelectPhoneCode;
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
public class LoginActivity extends BaseTitleActivity {

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
    @BindView(R.id.main_return)
    ImageButton mReturn;

    private String area = "86";

    @Override
    protected String initTitle() {
        return getString(R.string.login_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        mReturn.setVisibility(View.GONE);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @OnClick({R.id.login_password_eye, R.id.login_account_clean, R.id.login_register, R.id.login_forger_password, R.id.login_btn, R.id.login_selector_phone})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.login_selector_phone:
                SelectPhoneCode.with(LoginActivity.this).select();
                break;
            case R.id.login_account_clean:
                mAccountEdit.setText("");
                break;
            case R.id.login_password_eye:
                KeyBoardUtil.setInputTypePassword(mPasswordEye.isChecked(), mPasswordEdit);
                break;
            case R.id.login_forger_password:
                startActivityForResult(new Intent(this, ForgetLoginPswActivity.class), UIConfig.REQUEST_CODE);
                break;
            case R.id.login_register:
                startActivityForResult(new Intent(this, RegisterActivity.class), UIConfig.REQUEST_CODE);
                break;
            case R.id.login_btn:
                login();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        KeyBoardUtil.closeKeyBord(this, mAccountEdit, mPasswordEdit);
        super.onDestroy();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == UIConfig.RESULT_CODE) {
            if (data != null) {
                String account = data.getStringExtra("account");
                String password = data.getStringExtra("password");
                if (account != null && !account.isEmpty()) {
                    mAccountEdit.setText(account);
                    mAccountEdit.setSelection(account.length());
                }
                if (password != null && !password.isEmpty()) {
                    mPasswordEdit.setText(password);
                    mPasswordEdit.setSelection(password.length());
                }
            }
        }
        if (resultCode == ChoiceActivity.resultCode) {
            if (data != null) {
                AreaCodeModel model = (AreaCodeModel) data.getSerializableExtra(ChoiceActivity.DATAKEY);
                area = model.getTel();
                mSelectorPhone.setText("+ " + area);
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
            ToastUtil.showToast(R.string.login_error_phone_1);
            showEditError(mAccountEdit);
            return;
        }
        if (!account.matches(MatchesConfig.MATCHES_PHONE)) {
            ToastUtil.showToast(R.string.login_error_phone_2);
            showEditError(mAccountEdit);
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

        Api.getInstance().login(account, password, area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<LoginBean>(this) {
                    @Override
                    public void onSuccess(BaseBean<LoginBean> bean) {
                        if (bean != null) {
                            LoginBean data = bean.getData();
                            if (data != null) {
                                ToastUtil.showToast(R.string.login_success);
                                SharedPreferencesUtil util = SharedPreferencesUtil.getInstance();
                                util.saveBoolean(SharedConst.IS_LOGIN, true);
                                //x-token
                                util.saveString(SharedConst.ENCRYPT_VALUE_TOKEN, data.getToken());
                                //userId
                                util.saveInt(SharedConst.VALUE_USER_ID, data.getUserId());
                                //判断下是否进入过首页
                                Activity activity = ActivityManager.getInstance().getActivity(MainActivity.class.getName());
                                if (activity != null) {
                                    //发送eventBus
                                    EventBus.getDefault().post(EventBusConfig.EVENT_LOGIN);
                                    finish();
                                } else {
                                    MainActivity.actionStart(LoginActivity.this);
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
