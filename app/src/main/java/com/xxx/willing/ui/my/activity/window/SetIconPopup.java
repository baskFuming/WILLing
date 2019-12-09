package com.xxx.willing.ui.my.activity.window;

import android.content.Context;
import android.view.View;

import com.xxx.willing.R;
import com.xxx.willing.base.popup.BasePopup;

import butterknife.OnClick;

/**
 *  @desc   头像
 *  @author FM
 *  @date   2019-12-09
 */

public class SetIconPopup extends BasePopup {

    public static SetIconPopup getInstance(Context context, Callback callback) {
        if (callback == null) {
            return new SetIconPopup(context);
        }
        return new SetIconPopup(context, callback);
    }

    private Callback callback;

    private SetIconPopup(Context context) {
        super(context);
    }

    private SetIconPopup(Context context, Callback callback) {
        super(context);
        this.callback = callback;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.window_set_icon;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.window_set_icon_photo, R.id.window_set_icon_camera, R.id.window_set_icon_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.window_set_icon_photo:
                if (callback != null) callback.onPhoto();
                break;
            case R.id.window_set_icon_camera:
                if (callback != null) callback.onCamera();
                break;
            case R.id.window_set_icon_finish:
                dismiss();
                break;
        }
    }

    public interface Callback {

        void onCamera();

        void onPhoto();

    }

}
