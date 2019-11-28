package com.xxx.willing.ui.my.activity.vote;

import android.app.Activity;
import android.content.Intent;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;

/**
 * @author FM
 * @desc 投票记录
 * @date 2019-11-27
 */

public class VoteRecordActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, VoteRecordActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected String initTitle() {
        return getString(R.string.vote_record);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vote_record;
    }

    @Override
    protected void initData() {

    }
}
