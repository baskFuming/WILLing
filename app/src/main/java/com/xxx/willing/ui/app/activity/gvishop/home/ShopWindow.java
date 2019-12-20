package com.xxx.willing.ui.app.activity.gvishop.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.dialog.BaseDialog;
import com.xxx.willing.config.MatchesConfig;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.app.activity.gvishop.my.order.MyOrderActivity;
import com.xxx.willing.ui.main.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopWindow extends BaseDialog {

    public static ShopWindow getInstance(Context context) {
        return new ShopWindow(context);
    }

    public static final int TYPE_PHONE_EDIT = 1;
    public static final int TYPE_PHONE_SUCCESS = 2;
    public static final int TYPE_PHONE_FAIL = 3;
    public static final int TYPE_SHOP_SUCCESS = 4;
    public static final int TYPE_SHOP_FAIL = 5;

    private Callback callback;
    private int type;

    public void setType(int type) {
        this.type = type;
        if (mPhone != null) {
            initData();
        }
    }

    @BindView(R.id.window_shop_phone)
    LinearLayout mPhone;
    @BindView(R.id.window_shop_phone_success)
    LinearLayout mPhoneSuccess;
    @BindView(R.id.window_shop_phone_fail)
    LinearLayout mPhoneFail;
    @BindView(R.id.window_shop_order_success)
    LinearLayout mOrderSuccess;
    @BindView(R.id.window_shop_order_fail)
    LinearLayout mOrderFail;

    @BindView(R.id.window_shop_phone_edit)
    EditText mPhoneEdit;
    @BindView(R.id.window_shop_phone_success_title)
    TextView mPhoneSuccessTitle;

    private ShopWindow(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.window_shop;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        mPhone.setVisibility(View.GONE);
        mPhoneSuccess.setVisibility(View.GONE);
        mPhoneFail.setVisibility(View.GONE);
        mOrderSuccess.setVisibility(View.GONE);
        mOrderFail.setVisibility(View.GONE);

        switch (type) {
            case TYPE_PHONE_EDIT:
                mPhone.setVisibility(View.VISIBLE);
                break;
            case TYPE_PHONE_SUCCESS:
                mPhoneSuccess.setVisibility(View.VISIBLE);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String format = simpleDateFormat.format(new Date(System.currentTimeMillis() + 1800 * 1000));
                mPhoneSuccessTitle.setText(getContext().getString(R.string.window_shop_phone_success) + format);
                break;
            case TYPE_PHONE_FAIL:
                mPhoneFail.setVisibility(View.VISIBLE);
                break;
            case TYPE_SHOP_SUCCESS:
                mOrderSuccess.setVisibility(View.VISIBLE);
                break;
            case TYPE_SHOP_FAIL:
                mOrderFail.setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnClick({R.id.window_shop_phone_cancel_btn, R.id.window_shop_phone_confirm_btn, R.id.window_shop_phone_success_btn, R.id.window_shop_phone_fail_cancel_btn, R.id.window_shop_phone_fail_confirm_btn, R.id.window_shop_order_cancel_btn, R.id.window_shop_order_confirm_btn, R.id.window_shop_order_fail_cancel_btn, R.id.window_shop_order_fail_confirm_btn})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.window_shop_phone_cancel_btn:
                dismiss();
                break;
            case R.id.window_shop_phone_confirm_btn:
                String phone = mPhoneEdit.getText().toString();
                if (phone.isEmpty()) {
                    ToastUtil.showToast(R.string.login_error_phone_1);
                    return;
                }
                if (!phone.matches(MatchesConfig.MATCHES_PHONE)) {
                    ToastUtil.showToast(R.string.login_error_phone_2);
                    return;
                }
                if (callback != null) callback.callbackPhone(phone);
                break;
            case R.id.window_shop_phone_success_btn:
                dismiss();
                break;
            case R.id.window_shop_phone_fail_cancel_btn:
                dismiss();
                break;
            case R.id.window_shop_phone_fail_confirm_btn:
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("type", MainActivity.WALLET_TYPE);
                getContext().startActivity(intent);
                break;
            case R.id.window_shop_order_cancel_btn:
                dismiss();
                break;
            case R.id.window_shop_order_confirm_btn:
                MyOrderActivity.actionStart(getContext());
                dismiss();
                break;
            case R.id.window_shop_order_fail_cancel_btn:
                dismiss();
                break;
            case R.id.window_shop_order_fail_confirm_btn:
                Intent intent1 = new Intent(getContext(), MainActivity.class);
                intent1.putExtra("type", MainActivity.WALLET_TYPE);
                getContext().startActivity(intent1);
                break;
        }
    }

    public interface Callback {
        void callbackPhone(String phone);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
