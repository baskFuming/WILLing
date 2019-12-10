package com.xxx.willing.ui.my.activity.vote;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.model.http.bean.MyVoteBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author FM
 * @desc 投票记录
 * @date 2019-11-27
 */

public class VoteRecordActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static void actionStart(Activity activity, List<MyVoteBean.ListBean> listBeans) {
        Intent intent = new Intent(activity, VoteRecordActivity.class);
        intent.putExtra("list", (Serializable) listBeans);
        activity.startActivity(intent);
    }

    private void initBundle() {
        Intent intent = getIntent();
        mList = (List<MyVoteBean.ListBean>) intent.getSerializableExtra("list");
    }

    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.main_linear)
    LinearLayout mLinear;

    private List<MyVoteBean.ListBean> mList = new ArrayList<>();

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
        initBundle();

        VoteRecordAdapter mAdapter = new VoteRecordAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mRefresh.setRefreshing(false);
    }

}
