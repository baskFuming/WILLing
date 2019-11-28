package com.xxx.willing.ui.my.activity.vote;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author FM
 * @desc 我的投票
 * @date 2019-11-27
 */

public class MyVoteActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MyVoteActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.join_id)
    TextView mJoinId;
    @BindView(R.id.join_name)
    TextView mJoinNane;
    @BindView(R.id.vote_time)
    TextView mVoteTime;
    @BindView(R.id.vote_day)
    TextView mVoteDay;
    @BindView(R.id.vote_number)
    TextView mVoteNumber;
    @BindView(R.id.release_day)
    TextView mReleaseDay;
    @BindView(R.id.release_number)
    TextView mReleaseNumber;


    @Override
    protected String initTitle() {
        return getString(R.string.my_vote);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_vote;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.re_join_name, R.id.re_vote_number})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.re_join_name:
                break;
            case R.id.re_vote_number://投票记录
                VoteRecordActivity.actionStart(this);
                break;
        }
    }
}
