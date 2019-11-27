package com.xxx.willing.ui.my.activity.window;

import android.content.Context;
import android.view.View;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseActivity;
import com.xxx.willing.base.dialog.BaseDialog;

import butterknife.OnClick;

/**
 * Time: ${Date}
 * Author : mac
 * Des:
 */
public class SignPopWindow extends BaseDialog {

    private Callback callback;
    private BaseActivity activity;

    protected SignPopWindow(BaseActivity context) {
        super(context);
        this.activity = context;

    }

    public static SignPopWindow getInstance(BaseActivity context) {
        return new SignPopWindow(context);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.sign_pop_window;
    }

    @Override
    protected double setWidth() {
        return 0.8;
    }


    @Override
    protected void initData() {
        setCancelable(true); // 是否可以按“返回键”消失
    }

    @OnClick({R.id.window_dis,R.id.window_close})
    public void OnClick(View view){
        switch (view.getId()){
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
