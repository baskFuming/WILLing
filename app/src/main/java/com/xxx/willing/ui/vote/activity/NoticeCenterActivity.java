package com.xxx.willing.ui.vote.activity;

import android.os.BaseBundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseTitleActivity;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.PageBean;
import com.xxx.willing.ui.vote.adapter.NoticeCenterAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 *  @desc   公告中心
 *  @author FM
 *  @date   2019-12-06
 */

public class NoticeCenterActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;
    private NoticeCenterAdapter mAdapter;
    private List<BaseBean> mList = new ArrayList<>();

    private int page = UIConfig.PAGE_DEFAULT;

    @Override
    protected String initTitle() {
        return getString(R.string.notice_detail);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice_center;
    }

    @Override
    protected void initData() {

        mAdapter = new NoticeCenterAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
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
    //加载数据
    private void loadData() {
//        Api.getInstance().getNoticeCenterList(page, UIConfig.PAGE_SIZE)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new ApiCallback<PageBean<NoticeCenterBean>>(this) {
//
//                    @Override
//                    public void onSuccess(BaseBean<PageBean<NoticeCenterBean>> bean) {
//                        if (bean == null) {
//                            mNotData.setVisibility(View.VISIBLE);
//                            mAdapter.loadMoreEnd(true);
//                            return;
//                        }
//                        PageBean<NoticeCenterBean> data = bean.getData();
//                        if (data == null) {
//                            mNotData.setVisibility(View.VISIBLE);
//                            mRecycler.setVisibility(View.GONE);
//                            mAdapter.loadMoreEnd(true);
//                            return;
//                        }
//
//                        List<NoticeCenterBean> list = data.getList();
//                        if (list == null || list.size() == 0 && page == ConfigClass.PAGE_DEFAULT) {
//                            mNotData.setVisibility(View.VISIBLE);
//                            mRecycler.setVisibility(View.GONE);
//                            mAdapter.loadMoreEnd(true);
//                            return;
//                        }
//                        mRecycler.setVisibility(View.VISIBLE);
//                        mNotData.setVisibility(View.GONE);
//                        if (page == ConfigClass.PAGE_DEFAULT) {
//                            mList.clear();
//                        }
//
//                        mList.addAll(list);
//                        if (list.size() < ConfigClass.PAGE_SIZE) {
//                            mAdapter.loadMoreEnd(true);
//                        } else {
//                            mAdapter.loadMoreComplete();
//                        }
//                        mAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onError(int errorCode, String errorMessage) {
//
//                    }
//
//                    @Override
//                    public void onStart(Disposable d) {
//                        super.onStart(d);
//                        if (mRefresh != null && page == ConfigClass.PAGE_DEFAULT) {
//                            mRefresh.setRefreshing(true);
//                        }
//                    }
//
//                    @Override
//                    public void onEnd() {
//                        super.onEnd();
//                        if (mRefresh != null) {
//                            mRefresh.setRefreshing(false);
//                        }
//                    }
//                });
    }
}
