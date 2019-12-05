package com.xxx.willing.base.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.xxx.willing.R;

import butterknife.ButterKnife;

public abstract class BasePopup extends PopupWindow {

    private Context mContext;

    protected BasePopup(Context context) {
        super(context);
        this.mContext = context;
    }

    public void show() {
        View view = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
        Window window = ((Activity)mContext).getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = 0.6f;
        window.setAttributes(params);

        ButterKnife.bind(this, view);
        setAnimationStyle(R.style.showPopupAnimation);

        setBackgroundDrawable(new BitmapDrawable());    //边距BUG解决
        setOutsideTouchable(true);
        setContentView(view);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setFocusable(true);

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                Window window = ((Activity)mContext).getWindow();
                WindowManager.LayoutParams params = window.getAttributes();
                params.alpha = 1f;
                window.setAttributes(params);
            }
        });

        initData();
        showAtLocation(window.getDecorView(), Gravity.BOTTOM,0, 0);
    }

    protected abstract int getLayoutId();

    protected abstract void initData();

}
