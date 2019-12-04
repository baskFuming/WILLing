package com.xxx.willing.ui.app.vote.activity.vote;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.base.activity.BaseWebActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author FM
 * @desc 加盟详情
 * @date 2019-12-03
 */

public class JoinDetailsActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, JoinDetailsActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected String initTitle() {
        return getString(R.string.vote_details);
    }

    @BindView(R.id.main_content)
    TextView mContent;




    @Override
    protected int getLayoutId() {
        return R.layout.activity_join_details;
    }

    @Override
    protected void initData() {
        mContent.setVisibility(View.VISIBLE);
        mContent.setText(R.string.vote_rules);
    }

    @OnClick({R.id.main_content,R.id.vote_btn})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_content:
                BaseWebActivity.actionStart(this, "", getString(R.string.vote_details));
                break;
            case R.id.vote_btn://投票

                break;
        }
    }
}
