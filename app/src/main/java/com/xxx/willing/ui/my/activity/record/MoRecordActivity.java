package com.xxx.willing.ui.my.activity.record;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.journeyapps.barcodescanner.ViewfinderView;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.ui.my.activity.join.JoinApplyActivity;

import butterknife.OnClick;

/**
 * @author FM
 * @desc 资金记录
 * @date 2019-11-28
 */

public class MoRecordActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MoRecordActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected String initTitle() {
        return getString(R.string.my_money);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mo_record;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.re_left, R.id.re_right})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.re_left:
                break;
            case R.id.re_right:
                break;
        }
    }
}
