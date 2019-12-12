package com.xxx.willing.ui.app.activity.rank;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.base.activity.BaseWebActivity;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.FranchiseeBean;
import com.xxx.willing.model.http.bean.RankBean;
import com.xxx.willing.model.http.bean.TotalFranchiseeBean;
import com.xxx.willing.model.http.bean.TotalRankBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.PageBean;
import com.xxx.willing.model.utils.DateUtils;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.app.adapter.RankAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author FM
 * @desc 竞赛排名
 * @date 2019-12-03
 */

public class CmpRankActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, CmpRankActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.total_vote)
    TextView mTotalVote;
    @BindView(R.id.total_release)
    TextView mTotalRelease;
    @BindView(R.id.date_end_time)
    TextView mdate;

    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.main_not_data)
    LinearLayout mNoteDate;

    @BindView(R.id.main_content)
    TextView mContent;

    private int page = UIConfig.PAGE_DEFAULT;
    private List<RankBean> mList = new ArrayList<>();
    private RankAdapter mAdapter;

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

        mAdapter = new RankAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
        mRefresh.setRefreshing(true);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
        loadDate();
    }

    @Override
    public void onRefresh() {
        page = UIConfig.PAGE_DEFAULT;
        loadDate();
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        loadDate();
    }

    @OnClick({R.id.main_content})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_content:
                BaseWebActivity.actionStart(this, HttpConfig.COMPETITION_URL, getString(R.string.competition_rules));
                break;
        }
    }

    /**
     * @Model 获取竞赛排名
     */
    private void loadDate() {
        Api.getInstance().getrankings(page, UIConfig.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<TotalRankBean>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(BaseBean<TotalRankBean> bean) {
                        if (bean == null) {
                            mNoteDate.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        TotalRankBean data1 = bean.getData();
                        if (data1 == null) {
                            mNoteDate.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        int totalVoteAmount = data1.getAmount();
                        double totalVoteRelease = data1.getReward();
                        mTotalVote.setText(totalVoteAmount + "");
                        mTotalRelease.setText(totalVoteRelease + "");
                        mdate.setText(getString(R.string.date_end_time) + data1.getDate());

                        PageBean<RankBean> data = data1.getBean();
                        if (data == null) {
                            mNoteDate.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        List<RankBean> list = data.getList();
                        if (list == null || list.size() == 0 && page == UIConfig.PAGE_DEFAULT) {
                            mNoteDate.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        mNoteDate.setVisibility(View.GONE);
                        mRecycler.setVisibility(View.VISIBLE);
                        if (page == UIConfig.PAGE_DEFAULT) {
                            mList.clear();
                        }
                        mList.addAll(list);
                        if (list.size() < UIConfig.PAGE_SIZE) {
                            mAdapter.loadMoreEnd(true);
                        } else {
                            mAdapter.loadMoreComplete();
                        }
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        if (mList.size() == 0) {
                            mNoteDate.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                        }
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        if (mRefresh != null && page == UIConfig.PAGE_DEFAULT) {
                            mRefresh.setRefreshing(true);
                        }
                        showLoading();
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        if (mRefresh != null) {
                            mRefresh.setRefreshing(false);
                        }
                        hideLoading();
                    }
                });
    }
}
