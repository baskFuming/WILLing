package com.xxx.willing.ui.my.activity.window;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseActivity;
import com.xxx.willing.base.dialog.BaseDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Time: ${Date}
 * Author : mac
 * Des:
 */
public class SignPopWindow extends BaseDialog {

    private Callback callback;
    private double amount;

    @BindView(R.id.sign_success)
    TextView mSuccess;

    private SignPopWindow(Context context) {
        super(context);
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public static SignPopWindow getInstance(Context context) {
        return new SignPopWindow(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.sign_pop_window;
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    protected void initData() {
        setCancelable(true); // 是否可以按“返回键”消失

        mSuccess.setText(String.format(getContext().getString(R.string.sign_success), amount));
    }

    @OnClick({R.id.window_dis, R.id.window_close})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.window_dis:
                dismiss();
                break;
            case R.id.window_close:
                if (callback != null) callback.callback();
                break;
        }
    }

    public interface Callback {
        void callback();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
