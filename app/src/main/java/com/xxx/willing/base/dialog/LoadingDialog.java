package com.xxx.willing.base.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xxx.willing.R;

import butterknife.ButterKnife;


/**
 * 加载中的Dialog
 */
public class LoadingDialog extends AlertDialog {

    public static LoadingDialog getInstance(Context context) {
        return new LoadingDialog(context);
    }

    private View view;

    private LoadingDialog(Context context) {
        super(context);
    }

    /**
     * 展示Dialog
     */
    @SuppressLint("InflateParams")
    public void show() {
        setCancelable(true); // 是否可以按“返回键”消失
        setCanceledOnTouchOutside(false); // 点击框以外的区域
        super.show();   //展示
        //渲染布局
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.window_loading, null);
            ButterKnife.bind(this, view);
            setContentView(view);

            //设置大小
            final Window window = getWindow();
            if (window != null) {
                final WindowManager.LayoutParams lp = window.getAttributes();
                window.setBackgroundDrawableResource(android.R.color.transparent);
                window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                window.getDecorView().post(() -> {
                    lp.width = (int) (window.getDecorView().getWidth() * 0.45);
                    window.setGravity(Gravity.CLIP_HORIZONTAL);
                    window.setAttributes(lp);
                });
            }
        }
    }
}