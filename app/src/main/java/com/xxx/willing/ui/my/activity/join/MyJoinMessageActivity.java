package com.xxx.willing.ui.my.activity.join;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.FranchiseeBean;
import com.xxx.willing.model.http.bean.VoteDetailBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.vote.adapter.VoteItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyJoinMessageActivity extends BaseTitleActivity  implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MyJoinMessageActivity.class);
        activity.startActivity(intent);
    }
    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;

    private int page = UIConfig.PAGE_DEFAULT;
    private VoteItemAdapter mAdapter;
    private List<FranchiseeBean> mList = new ArrayList<>();

    @Override
    protected String initTitle() {
        return getString(R.string.my_join_message);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_join_message;
    }

    @Override
    protected void initData() {

        mAdapter = new VoteItemAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
        getVoteDetail();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreRequested() {

    }

    /**
     * @Model 获取投票加盟详情
     */
    private void getVoteDetail() {
        Api.getInstance().getVoteDetail(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<VoteDetailBean>(this) {

                    @Override
                    public void onSuccess(BaseBean<VoteDetailBean> bean) {

                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        if (mList.size() == 0) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                        }
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
