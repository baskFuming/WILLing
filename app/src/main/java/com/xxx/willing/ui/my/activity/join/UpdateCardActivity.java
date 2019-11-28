package com.xxx.willing.ui.my.activity.join;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author FM
 * @desc 上传证件照
 * @date 2019-11-28
 */

public class UpdateCardActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, UpdateCardActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.img_positive)
    ImageView mImgPos;
    @BindView(R.id.img_reverse)
    ImageView mImgRev;
    @BindView(R.id.img_hand)
    ImageView mImgHand;

    @Override
    protected String initTitle() {
        return getString(R.string.update_card);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_card;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.img_positive, R.id.img_reverse, R.id.img_hand})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.img_positive:
                break;
            case R.id.img_reverse:
                break;
            case R.id.img_hand:
                break;
        }
    }
}
