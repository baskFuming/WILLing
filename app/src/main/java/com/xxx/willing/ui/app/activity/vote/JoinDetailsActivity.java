package com.xxx.willing.ui.app.activity.vote;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xw.banner.Banner;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.base.activity.BaseWebActivity;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.VoteDetailBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.BooleanBean;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.model.utils.BannerUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.app.adapter.TeamMesAdapter;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

public class JoinDetailsActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemChildClickListener, VoteWindow.Callback {

    public static void actionStart(Activity activity, int franId) {
        Intent intent = new Intent(activity, JoinDetailsActivity.class);
        intent.putExtra("franId", franId);
        activity.startActivity(intent);
    }

    public static void actionStarts(Activity activity, int franId) {
        Intent intent = new Intent(activity, JoinDetailsActivity.class);
        intent.putExtra("franId", franId);
        intent.putExtra("isMy", true);
        activity.startActivity(intent);
    }

    private void initBundle() {
        Intent intent = getIntent();
        franId = intent.getIntExtra("franId", 0);
        boolean isMy = intent.getBooleanExtra("isMy", false);
        if (isMy) {
            //自己的不显示按钮
            mBtn.setVisibility(View.GONE);
        }
    }

    private VoteWindow mVoteWindow;

    @BindView(R.id.main_content)
    TextView mContent;
    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;

    @BindView(R.id.vote_btn)
    Button mBtn;
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
    @BindView(R.id.item_notice_center_btn)
    CheckBox mCheck;
    @BindView(R.id.notice_img)
    ImageView mImage;

    private TeamMesAdapter mAdapter;
    private List<VoteDetailBean.ListBean> mList = new ArrayList<>();
    private Integer franId;

    @Override
    protected String initTitle() {
        return getString(R.string.vote_details);
    }

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

        mVoteWindow = VoteWindow.getInstance(this);
        mVoteWindow.setCallback(this);
//
//        if (mCheck.isChecked()) {
//            mCheck.setText(getString(R.string.pack_content));
//            mImage.setBackgroundResource(R.mipmap.vote_pack_up);
//        } else {
//            mCheck.setText(getString(R.string.all_text));
//            mImage.setBackgroundResource(R.mipmap.vote_down);
//        }

        getVoteDetail();
    }

    @OnClick({R.id.main_content, R.id.vote_btn, R.id.text_click})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_content:
                BaseWebActivity.actionStart(this, HttpConfig.VOTE_URL, getString(R.string.vote_rules));
                break;
            case R.id.vote_btn:
                if (mVoteWindow != null) {
                    mVoteWindow.show();
                }
                break;
            case R.id.text_click:
                boolean checked = mCheck.isChecked();
                if (!checked) {
                    mCheck.setText(getString(R.string.all_text));
                    mImage.setBackgroundResource(R.mipmap.vote_down);
                    mDetail.setMaxLines(4);
                    mDetail.invalidate();
                } else {
                    mCheck.setText(getString(R.string.pack_content));
                    mImage.setBackgroundResource(R.mipmap.vote_pack_up);
                    mDetail.setMaxLines(10000);
                    mDetail.invalidate();
                }

        }
    }

    @Override
    public void callback(int amount) {
        if (amount <= 0) {
            ToastUtil.showToast(getString(R.string.vote_error_1));
            return;
        }
        vote(amount);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onRefresh() {
        getVoteDetail();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVoteWindow != null) {
            mVoteWindow.dismiss();
            mVoteWindow = null;
        }
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
                        String string = new BigDecimal(((double) progress / (double) total * 100)).setScale(2, RoundingMode.DOWN).toPlainString();
                        mProgressNumber.setText(string + "%");

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
                        if (mRefresh != null) {
                            mRefresh.setRefreshing(false);
                        }
                    }
                });
    }

    /**
     * @Model 投票
     */
    private void vote(int amount) {
        Api.getInstance().vote(franId, amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<BooleanBean>(this) {
                    @Override
                    public void onSuccess(BaseBean<BooleanBean> bean) {
                        if (bean != null) {
                            BooleanBean data = bean.getData();
                            if (data != null && data.isResult()) {
                                ToastUtil.showToast(getString(R.string.vote_success));

                                if (mVoteWindow != null) {
                                    mVoteWindow.dismiss();
                                }
                                getVoteDetail();
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

}
