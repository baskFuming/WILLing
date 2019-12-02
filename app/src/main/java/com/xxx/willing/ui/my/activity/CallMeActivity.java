package com.xxx.willing.ui.my.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.model.utils.ImageUtil;

import butterknife.OnClick;

public class CallMeActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, CallMeActivity.class);
        activity.startActivity(intent);
    }

    private Bitmap mBitmap;

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
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.wechat_code);
    }

    @OnClick({R.id.call_me_wechat_save})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.call_me_wechat_save:
                ImageUtil.saveImage(this, mBitmap);
                break;
        }
    }
}
