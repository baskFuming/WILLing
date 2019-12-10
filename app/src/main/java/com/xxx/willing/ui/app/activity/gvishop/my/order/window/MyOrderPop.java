package com.xxx.willing.ui.app.vote.activity.gvishop.my.order.window;

import android.view.View;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseActivity;
import com.xxx.willing.base.dialog.BaseDialog;

import butterknife.OnClick;

/**
 * @author FM
 * @desc 确认支付
 * @date 2019-12-04
 */

public class MyOrderPop extends BaseDialog {

    private Callback callback;
    private BaseActivity activity;
    private String orderStr;

    public static MyOrderPop getInstance(BaseActivity context) {
        return new MyOrderPop(context);
    }

    private MyOrderPop(BaseActivity context) {
        super(context);
        this.activity = context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.my_order_pop;
    }

    @Override
    protected void initData() {
        setCancelable(true); // 是否可以按“返回键”消失
    }

    @Override
    protected double setWidth() {
        return 0.8;
    }

    @OnClick({R.id.window_confirm_cancel, R.id.window_pay_order})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.window_confirm_cancel:
                dismiss();
                break;
            case R.id.window_pay_order:
                if (callback != null) {
                    callback.callback(orderStr);
                }
                break;
        }
    }

    public interface Callback {
        void callback(String orderStr);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

}
