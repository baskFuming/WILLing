package com.xxx.willing.ui.my.activity.team;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;

/**
 *  @desc   我的团队子集页面
 *  @author FM
 *  @date   2019-11-28
 */

public class MyChildActivity extends BaseTitleActivity {
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MyChildActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected String initTitle() {
        return getString(R.string.my_team);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_child;
    }

    @Override
    protected void initData() {

    }
}
