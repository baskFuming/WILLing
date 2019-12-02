package com.xxx.willing.ui.my.activity.sign;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.ui.my.activity.InviteFriendActivity;
import com.xxx.willing.ui.my.activity.sign.view.StepBean;
import com.xxx.willing.ui.my.activity.sign.view.StepCallback;
import com.xxx.willing.ui.my.activity.sign.view.StepsView;
import com.xxx.willing.ui.my.activity.vote.MyVoteActivity;
import com.xxx.willing.ui.my.activity.window.SignPopWindow;

import java.util.ArrayList;
import java.util.List;

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
    //    @BindView(R.id.recycler_progress)
//    RecyclerView mRecycler;
    @BindView(R.id.step_view)
    StepsView mStepView;


    private List<StepBean> mList = new ArrayList<>();
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

        //TODO 假数据测试
        mList.add(new StepBean(StepBean.STEP_COMPLETED, 1));
        mList.add(new StepBean(StepBean.STEP_COMPLETED, 2));
        mList.add(new StepBean(StepBean.STEP_UNDO, 3));
        mList.add(new StepBean(StepBean.STEP_UNDO, 4));
        mList.add(new StepBean(StepBean.STEP_UNDO, 5));
        mList.add(new StepBean(StepBean.STEP_UNDO, 6, R.mipmap.ic_launcher));
        mList.add(new StepBean(StepBean.STEP_UNDO, 7));
        mStepView.setStepNum(mList);
    }

    @OnClick({R.id.main_content, R.id.te_sign_btn, R.id.te_invite_friend, R.id.te_go_vote})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_content://任务规则
                TaskRulesActivity.actionStart(this);
                break;
            case R.id.te_sign_btn://签到弹框
                //执行签到动画
                mStepView.startSignAnimation(() -> {
                    if (signPopWindow == null || !signPopWindow.isShowing()) {
                        signPopWindow = SignPopWindow.getInstance(this);
                        signPopWindow.setCallback(this);
                        signPopWindow.show();
                    }
                });
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
