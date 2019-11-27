package com.xxx.willing.ui.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.xxx.willing.R;
import com.xxx.willing.base.dialog.BaseDialog;

import butterknife.OnClick;

public class UpdateWindow extends BaseDialog {

    private String url;

    private void setUrl(String url) {
        this.url = url;
    }

    public static UpdateWindow getInstance(Context context, String url) {
        UpdateWindow window = new UpdateWindow(context);
        window.setUrl(url);
        return window;
    }

    private UpdateWindow(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.window_update;
    }

    @Override
    protected void initData() {
        setCancelable(false); // 是否可以按“返回键”消失
    }

    @Override
    protected double setWidth() {
        return 0.8;
    }

    @OnClick({R.id.window_update_true})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.window_update_true:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(url));
                getContext().startActivity(intent);
                break;
        }
    }
}
