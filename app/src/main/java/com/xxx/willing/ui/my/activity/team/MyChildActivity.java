package com.xxx.willing.ui.my.activity.team;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.MyTeamBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author FM
 * @desc 我的团队子集页面
 * @date 2019-11-28
 */
public class MyChildActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    public static void actionStart(Activity activity, int userId) {
        Intent intent = new Intent(activity, MyChildActivity.class);
        intent.putExtra("userId", userId);
        activity.startActivity(intent);
    }

    private void initBundle() {
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
    }

    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.main_linear)
    LinearLayout mLinear;

    private int page = UIConfig.PAGE_DEFAULT;
    private MyTeamAdapter mAdapter;
    private List<MyTeamBean.ListBean> mList = new ArrayList<>();
    private int userId;

    @Override
    protected String initTitle() {
        return getString(R.string.my_team);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_child;
    }

    @Override
    protected void initData() {
        initBundle();

        mAdapter = new MyTeamAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecycler);

        showLoading();
        loadData();
    }


    @Override
    public void onRefresh() {
        page = UIConfig.PAGE_DEFAULT;
        loadData();
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        loadData();
    }

    private void loadData() {
        Api.getInstance().getMyTeamList(userId, page, UIConfig.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<MyTeamBean>(this) {

                    @Override
                    public void onSuccess(BaseBean<MyTeamBean> bean) {
                        MyTeamBean data = bean.getData();
                        if (data == null) {
                            mNotData.setVisibility(View.VISIBLE);
                            mLinear.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
//                        double totalAchievement = data.getTotalAchievement();
                        List<MyTeamBean.ListBean> list = data.getList();
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
