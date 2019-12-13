package com.xxx.willing.ui.wallet.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.client.android.Intents;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.config.EventBusConfig;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.BooleanBean;
import com.xxx.willing.model.http.utils.ApiCode;
import com.xxx.willing.model.utils.KeyBoardUtil;
import com.xxx.willing.model.utils.PermissionUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.login.activity.PasswordSettingActivity;
import com.xxx.willing.ui.main.SweepActivity;
import com.xxx.willing.ui.wallet.window.PasswordWindow;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 提现页
 * @Author xxx
 */
public class WithdrawalActivity extends BaseTitleActivity implements PasswordWindow.Callback {

    public static void actionStart(Activity activity, int coinId, String symbol, double balance, double fee) {
        Intent intent = new Intent(activity, WithdrawalActivity.class);
        intent.putExtra("coinId", coinId);
        intent.putExtra("symbol", symbol);
        intent.putExtra("balance", balance);
        intent.putExtra("fee", fee);
        activity.startActivityForResult(intent, UIConfig.REQUEST_CODE);
    }

    private void initBundle() {
        Intent intent = getIntent();
        coinId = intent.getIntExtra("coinId", 0);
        symbol = intent.getStringExtra("symbol");
        balance = intent.getDoubleExtra("balance", 0);
        fee = intent.getDoubleExtra("fee", 0);
    }

    @BindView(R.id.withdrawal_address)
    EditText mAddress;
    @BindView(R.id.withdrawal_amount)
    EditText mAmount;
    @BindView(R.id.withdrawal_balance)
    TextView mBalance;
    @BindView(R.id.withdrawal_fee)
    TextView mFee;

    private int coinId;
    private String symbol;
    private double balance;
    private double fee;

    private String address;
    private double amount;

    private PasswordWindow mPasswordWindow;

    @Override
    protected String initTitle() {
        return symbol + getString(R.string.withdrawal_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdrawal;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        initBundle();
        mBalance.setText(getString(R.string.withdrawal_balance) + balance);
        mFee.setText(getString(R.string.withdrawal_fee) + (fee * 100) + "% BVSE");

        //初始化密码弹框
        mPasswordWindow = PasswordWindow.getInstance(this);
        mPasswordWindow.setCallback(this);

        //限定
        KeyBoardUtil.setFilters(mAmount, UIConfig.DOUBLE_AMOUNT_NUMBER);
    }

    @OnClick({R.id.main_return, R.id.withdrawal_btn, R.id.withdrawal_sweep})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_return:
                finish();
                break;
            case R.id.withdrawal_btn:
                showWithdrawalDialog();
                break;
            case R.id.withdrawal_sweep:
                if (!PermissionUtil.checkPermission(this, PermissionUtil.READ_PERMISSION, PermissionUtil.WRITE_PERMISSION, PermissionUtil.CAMERA_PERMISSION)) {
                    startActivityForResult(new Intent(this, SweepActivity.class), UIConfig.REQUEST_CODE);
                }
                break;
        }
    }

    @Override
    public void finish() {
        KeyBoardUtil.closeKeyBord(this, mAddress);
        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            String content = data.getStringExtra(Intents.Scan.RESULT);
            mAddress.setText(content);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean result = PermissionUtil.onRequestPermissionsResult(permissions, grantResults);
        if (result) {
            startActivityForResult(new Intent(this, CaptureActivity.class), UIConfig.REQUEST_CODE);
        } else {
            if (!PermissionUtil.checkPermission(this, PermissionUtil.READ_PERMISSION, PermissionUtil.WRITE_PERMISSION, PermissionUtil.CAMERA_PERMISSION)) {
                startActivityForResult(new Intent(this, CaptureActivity.class), UIConfig.REQUEST_CODE);
            }
        }
    }


    /**
     * 提现验证
     */
    private void showWithdrawalDialog() {
        address = mAddress.getText().toString();
        String amount = mAmount.getText().toString();

        if (address.isEmpty()) {
            ToastUtil.showToast(R.string.withdrawal_error_1);
            showEditError(mAddress);
            return;
        }
        if (amount.isEmpty()) {
            ToastUtil.showToast(R.string.withdrawal_error_2);
            showEditError(mAmount);
            return;
        }
        try {
//            if (Double.parseDouble(amount) + fee > balance) {
//                ToastUtil.showToast(R.string.withdrawal_error_3);
//                showEditError(mAmount);
//                return;
//            }
            this.amount = Double.parseDouble(amount);
        } catch (Exception e) {
            ToastUtil.showToast(R.string.withdrawal_error_4);
            showEditError(mAmount);
            return;
        }

        if (mPasswordWindow != null && !mPasswordWindow.isShowing()) {
            mPasswordWindow.show();
        }
    }

    @Override
    public void callback(String password) {
        if (password.isEmpty()) {
            ToastUtil.showToast(R.string.withdrawal_error_5);
            return;
        }
        withdrawal(password);
    }

    /**
     * @Model 提现
     */
    private void withdrawal(String password) {
        Api.getInstance().withdrawal(coinId, amount,address, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<BooleanBean>(this) {

                    @Override
                    public void onSuccess(BaseBean<BooleanBean> bean) {
                        balance -= amount;

                        if (mPasswordWindow != null) {
                            mPasswordWindow.dismiss();
                            mPasswordWindow = null;
                        }

                        EventBus.getDefault().post(EventBusConfig.EVENT_UPDATE_WALLET);
                        ToastUtil.showToast("转账成功");
                        setResult(UIConfig.RESULT_CODE);
                        finish();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        if (errorCode == ApiCode.PAY_NOT_SETTING) {
                            ToastUtil.showToast(getString(R.string.jy_password_error));
                            PasswordSettingActivity.actionStart(WithdrawalActivity.this);
                            return;
                        }
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
