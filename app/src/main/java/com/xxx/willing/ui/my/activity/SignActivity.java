package com.xxx.willing.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.ui.my.activity.window.SignPopWindow;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author FM
 * @desc 任务中心，签到
 * @date 2019-11-27
 */

public class SignActivity extends BaseTitleActivity implements SignPopWindow.Callback {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, SignActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.main_content)
    TextView mContent;
    @BindView(R.id.text_rules)
    TextView mTeRules;
    @BindView(R.id.te_trip)
    TextView mTrip;
    @BindView(R.id.task_progress)
    ProgressBar mProgress;
    private SignPopWindow signPopWindow;

    @Override
    protected String initTitle() {
        return getString(R.string.sign_task);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign;
    }

    @Override
    protected void initData() {
        mContent.setVisibility(View.VISIBLE);
        mContent.setText(getString(R.string.sign_task));
        mTeRules.setText(Html.fromHtml(String.format(getResources().getString(R.string.sign_rules))));
        mTrip.setText(Html.fromHtml(String.format(getResources().getString(R.string.task_trip))));
    }

    @OnClick({R.id.main_content, R.id.te_sign_btn, R.id.te_invite_friend, R.id.te_go_vote})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_content://任务规则
                TaskRulesActivity.actionStart(this);
                break;
            case R.id.te_sign_btn://签到弹框
                if (signPopWindow == null || !signPopWindow.isShowing()) {
                    signPopWindow = SignPopWindow.getInstance(this);
                    signPopWindow.setCallback(this);
                    signPopWindow.show();
                }
                break;
            case R.id.te_invite_friend:
                InviteFriendActivity.actionStart(this);
                break;
            case R.id.te_go_vote:
                MyVoteActivity.actionStart(this);
                break;
        }
    }

    @Override
    public void callback() {
        if (signPopWindow != null) {
            signPopWindow.dismiss();
            signPopWindow = null;
        }
    }
}
