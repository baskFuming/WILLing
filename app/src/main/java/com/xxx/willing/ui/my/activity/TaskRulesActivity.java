package com.xxx.willing.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;

public class TaskRulesActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, TaskRulesActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected String initTitle() {
        return getString(R.string.sign_task_rules);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_rules;
    }

    @Override
    protected void initData() {

    }
}
