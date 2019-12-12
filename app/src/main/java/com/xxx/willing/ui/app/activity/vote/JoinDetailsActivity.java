package com.xxx.willing.ui.app.activity.vote;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xw.banner.Banner;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.base.activity.BaseWebActivity;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.VoteDetailBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.BooleanBean;
import com.xxx.willing.model.utils.BannerUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.app.adapter.TeamMesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author FM
 * @desc 加盟详情
 * @date 2019-12-03
 */

public class JoinDetailsActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemChildClickListener {

    public static void actionStart(Activity activity, int franId) {
        Intent intent = new Intent(activity, JoinDetailsActivity.class);
        intent.putExtra("franId", franId);
        activity.startActivity(intent);
    }

    private void initBundle() {
        Intent intent = getIntent();
        franId = intent.getIntExtra("franId", 0);
    }

    @Override
    protected String initTitle() {
        return getString(R.string.vote_details);
    }

    @BindView(R.id.main_content)
    TextView mContent;
    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;

    @BindView(R.id.join_banner)
    Banner mBanner;
    @BindView(R.id.join_name)
    TextView mName;
    @BindView(R.id.join_company_id)
    TextView mCompanyId;
    @BindView(R.id.task_progress)
    ProgressBar mProgress;
    @BindView(R.id.task_progress_number)
    TextView mProgressNumber;
    @BindView(R.id.vote_number)
    TextView mNumber;
    @BindView(R.id.vote_brand)
    TextView mBrand;
    @BindView(R.id.vote_water)
    TextView mWater;
    @BindView(R.id.vote_time)
    TextView mTime;
    @BindView(R.id.vote_cycle)
    TextView mCycle;
    @BindView(R.id.vote_earnings)
    TextView mEarnings;
    @BindView(R.id.vote_join_cycle)
    TextView mJoinCycle;
    @BindView(R.id.vote_content)
    TextView mDetail;

    private TeamMesAdapter mAdapter;
    private List<VoteDetailBean.ListBean> mList = new ArrayList<>();
    private Integer franId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_join_details;
    }

    @Override
    protected void initData() {
        initBundle();

        mContent.setVisibility(View.VISIBLE);
        mContent.setText(R.string.vote_rules);

        mAdapter = new TeamMesAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
        mAdapter.setOnItemChildClickListener(this);

        getVoteDetail();
    }

    @OnClick({R.id.main_content, R.id.vote_btn})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_content:
                BaseWebActivity.actionStart(this, "", getString(R.string.vote_details));
                break;
            case R.id.vote_btn:
                vote();
                break;
        }
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onRefresh() {
        getVoteDetail();
    }


    /**
     * @Model 获取投票加盟详情
     */
    private void getVoteDetail() {
        Api.getInstance().getVoteDetail(franId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<VoteDetailBean>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(BaseBean<VoteDetailBean> bean) {
                        VoteDetailBean data = bean.getData();
                        int total = data.getQuota();
                        int progress = (int) data.getVoteNum();
                        mProgress.setMax(total);
                        mProgress.setProgress(progress);
                        mNumber.setText(progress + "/" + total + "票");
                        mProgressNumber.setText((progress / total * 100) + "%");

                        BannerUtil.init(mBanner, data.getBanner(), position -> {
                            //TODO 等待跳转
                        });
                        mName.setText(data.getFranName());
                        mCompanyId.setText(data.getFranNum());
                        mBrand.setText(data.getBrand());
                        mWater.setText(data.getExTurnover());
                        mTime.setText(data.getReleaseTime() + "至\n" + data.getEndTime());
                        mCycle.setText(data.getReCycle() + "天");
                        mEarnings.setText(data.getExIncome());
                        mJoinCycle.setText(data.getPeriod() + "年");
                        mDetail.setText(data.getDetails());
                        List<VoteDetailBean.ListBean> list = data.getList();
                        if (list != null && list.size() != 0) {
                            mList.clear();
                            mList.addAll(list);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        showLoading();
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        hideLoading();
                    }
                });
    }

    /**
     * @Model 投票
     */
    private void vote() {
        Api.getInstance().vote(franId, 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<BooleanBean>(this) {
                    @Override
                    public void onSuccess(BaseBean<BooleanBean> bean) {
                        if (bean != null) {
                            BooleanBean data = bean.getData();
                            if (data != null && data.isResult()) {
                                ToastUtil.showToast(getString(R.string.vote_success));
                            }
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        showLoading();
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        hideLoading();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.content_open, R.id.vote_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.content_open:
                break;
            case R.id.vote_btn:
                break;
        }
    }
}
