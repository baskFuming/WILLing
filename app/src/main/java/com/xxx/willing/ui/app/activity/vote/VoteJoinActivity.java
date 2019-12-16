package com.xxx.willing.ui.app.activity.vote;

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
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.FranchiseeBean;
import com.xxx.willing.model.http.bean.TotalFranchiseeBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.PageBean;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.vote.adapter.VoteItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
    @BindView(R.id.main_linear)
    LinearLayout mLinear;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;

    private int page = UIConfig.PAGE_DEFAULT;
    private VoteItemAdapter mAdapter;
    private List<FranchiseeBean> mList = new ArrayList<>();

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
        mAdapter = new VoteItemAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
        mRefresh.setRefreshing(true);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
        mAdapter.setOnItemChildClickListener(this);

        getFranchiseeList();
    }

    @OnClick({R.id.main_content})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_content:
                BaseWebActivity.actionStart(this, "", getString(R.string.vote_details));
                break;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        FranchiseeBean franchiseeBean = mList.get(position);
        JoinDetailsActivity.actionStart(this, franchiseeBean.getId());
    }

    public void onRefresh() {
        page = UIConfig.PAGE_DEFAULT;
        getFranchiseeList();
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        getFranchiseeList();
    }

    /**
     * @Model 获取投票加盟列表
     */
    private void getFranchiseeList() {
        Api.getInstance().getFranchiseeList(page, UIConfig.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<TotalFranchiseeBean>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(BaseBean<TotalFranchiseeBean> bean) {
                        if (bean == null) {
                            mNotData.setVisibility(View.VISIBLE);
                            mLinear.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        TotalFranchiseeBean data1 = bean.getData();
                        if (data1 == null) {
                            mNotData.setVisibility(View.VISIBLE);
                            mLinear.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        double totalVoteAmount = data1.getTotalVoteAmount();
                        double totalVoteRelease = data1.getTotalVoteRelease();
                        mTotalVote.setText(totalVoteAmount + "");
                        mTotalRelease.setText(totalVoteRelease + "");

                        PageBean<FranchiseeBean> data = data1.getBean();
                        if (data == null) {
                            mNotData.setVisibility(View.VISIBLE);
                            mLinear.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        List<FranchiseeBean> list = data.getList();
                        if (list == null || list.size() == 0 && page == UIConfig.PAGE_DEFAULT) {
                            mNotData.setVisibility(View.VISIBLE);
                            mLinear.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        mNotData.setVisibility(View.GONE);
                        mLinear.setVisibility(View.VISIBLE);
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
                            mNotData.setVisibility(View.VISIBLE);
                            mLinear.setVisibility(View.GONE);
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
