package com.xxx.willing.ui.app.vote.activity.rank;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.base.activity.BaseWebActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author FM
 * @desc 竞赛排名
 * @date 2019-12-03
 */

public class CmpRankActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener{

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, CmpRankActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.total_vote)
    TextView mTotalVote;
    @BindView(R.id.total_release)
    TextView mTotalRelease;
    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.main_not_data)
    LinearLayout mNoteDate;
    @BindView(R.id.main_content)
    TextView mContent;

    @Override
    protected String initTitle() {
        return getString(R.string.competition_rank);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cmp_rank;
    }

    @Override
    protected void initData() {
        mContent.setVisibility(View.VISIBLE);
        mContent.setText(R.string.competition_rules);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreRequested() {

    }
    @OnClick({R.id.main_content})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_content:
                BaseWebActivity.actionStart(this, "", getString(R.string.competition_rules));
                break;
        }
    }
}
