package com.xxx.willing.ui.my.activity.team;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;

import butterknife.BindView;

/**
 * @author FM
 * @desc 我的团队
 * @date 2019-11-27
 */

public class MyTeamActivity extends BaseTitleActivity implements BaseQuickAdapter.OnItemChildClickListener {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MyTeamActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.my_team_count)
    TextView mTeamCount;

    @Override
    protected String initTitle() {
        return getString(R.string.my_team);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_team;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        MyChildActivity.actionStart(this);
    }
}
