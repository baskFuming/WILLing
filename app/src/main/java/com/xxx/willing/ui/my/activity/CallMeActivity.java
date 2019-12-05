package com.xxx.willing.ui.my.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.model.utils.ImageUtil;
import com.xxx.willing.model.utils.KeyBoardUtil;
import com.xxx.willing.model.utils.ZXingUtil;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class CallMeActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, CallMeActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.call_me_wechat_code)
    ImageView mCode;

    private Bitmap bitmap;
    private String content;

    @Override
    protected String initTitle() {
        return getString(R.string.call_my_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_call_me;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initData() {
        content = HttpConfig.CALL_MY_CODE;
        bitmap = ZXingUtil.createQRCode(content, (int) getResources().getDimension(R.dimen.zxCode_size));
        mCode.setImageBitmap(bitmap);
    }

    @OnClick({R.id.call_me_wechat_save})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.call_me_wechat_save:
                KeyBoardUtil.copy(this, content);
                break;
        }
    }

    @OnLongClick({R.id.call_me_wechat_code})
    public boolean OnLongClick(View view) {
        switch (view.getId()) {
            case R.id.call_me_wechat_save:
                if (bitmap != null) {
                    ImageUtil.saveImage(this, bitmap);
                }
                break;
        }
        return false;
    }
}
