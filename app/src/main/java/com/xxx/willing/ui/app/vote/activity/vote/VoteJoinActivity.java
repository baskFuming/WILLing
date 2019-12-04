package com.xxx.willing.ui.app.vote.activity.vote;

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

import butterknife.BindView;

/**
 * @author FM
 * @desc 投票加盟
 * @date 2019-12-03
 */

public class VoteJoinActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, VoteJoinActivity.class);
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


    @Override
    protected String initTitle() {
        return getString(R.string.app_vote);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vote_join;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        JoinDetailsActivity.actionStart(this);
    }

    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    public void onRefresh() {

    }
}
