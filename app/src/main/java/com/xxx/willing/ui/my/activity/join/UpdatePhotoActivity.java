package com.xxx.willing.ui.my.activity.join;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author FM
 * @desc 宣传图
 * @date 2019-11-28
 */

public class UpdatePhotoActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, UpdatePhotoActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.up_img_1)
    ImageView mImg1;

    @Override
    protected String initTitle() {
        return getString(R.string.up_photo);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_phont;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.img_click_up, R.id.btn_save})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.img_click_up:

                break;
            case R.id.btn_save:
                break;
        }
    }
}

