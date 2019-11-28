package com.xxx.willing.ui.my.activity.sign;

import android.app.Activity;
import android.content.Intent;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;

/**
 * @author FM
 * @desc 任务规则
 * @date 2019-11-28
 */

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
