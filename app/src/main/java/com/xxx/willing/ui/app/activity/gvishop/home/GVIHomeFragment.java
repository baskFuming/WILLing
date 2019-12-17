package com.xxx.willing.ui.app.activity.gvishop.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.willing.R;
import com.xxx.willing.base.activity.BaseWebShopActivity;
import com.xxx.willing.base.fragment.BaseFragment;
import com.xxx.willing.config.HttpConfig;
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.GviBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.PageBean;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.app.activity.gvishop.home.adapter.GviAdapter;
import com.xxx.willing.ui.app.activity.gvishop.home.adapter.GviChildAdapter;
import com.xxx.willing.view.SearchEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author FM
 * @desc 商城首页
 * @date 2019-12-04
 */

public class GVIHomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener, GviAdapter.OnClickListener {

    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.search_recycler)
    RecyclerView mSearchRecycler;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.gvi_search_shop1)
    SearchEditText mSearch;

    private int page = UIConfig.PAGE_DEFAULT;
    private GviAdapter mAdapter;
    private List<GviBean> mList = new ArrayList<>();
    private List<GviBean.ListBean> mSearchList = new ArrayList<>();
    private String edSearchName;
    private GviChildAdapter mSearchAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.gvi_home;
    }

    @Override
    protected void initData() {
        mAdapter = new GviAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
        mAdapter.setOnClickListener(this);

        mSearchAdapter = new GviChildAdapter(mSearchList);
        mSearchRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mSearchRecycler.setAdapter(mSearchAdapter);
        mSearchAdapter.setOnLoadMoreListener(this, mSearchRecycler);
        mSearchAdapter.setOnItemClickListener(this);

        loadData();

        //搜索商品
        mSearch.setOnSearchClickListener(view -> {
            edSearchName = mSearch.getText().toString();
            if (edSearchName.isEmpty()) {
                ToastUtil.showToast(getString(R.string.search_default_null));
                mRefresh.setVisibility(View.VISIBLE);
                mSearchRecycler.setVisibility(View.GONE);
            } else {
                mRefresh.setVisibility(View.GONE);
                mSearchRecycler.setVisibility(View.VISIBLE);
                page = UIConfig.PAGE_DEFAULT;
                loadSearch();
            }
        });
    }

    @Override
    public void onOnClickListener(String name, int id) {
        //跳转到商城web
        BaseWebShopActivity.actionStart(getActivity(), name, id);
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        //跳转到商城web
        BaseWebShopActivity.actionStart(getActivity(),
                mSearchList.get(position).getName(),
                mSearchList.get(position).getId());
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        loadSearch();
    }

    //获取商城
    private void loadData() {
        Api.getInstance().getShop()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<List<GviBean>>(getActivity()) {

                    @Override
                    public void onSuccess(BaseBean<List<GviBean>> bean) {
                        if (bean != null) {
                            List<GviBean> list = bean.getData();
                            mRecycler.setVisibility(View.VISIBLE);
                            if (page == UIConfig.PAGE_DEFAULT) {
                                mList.clear();
                            }
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
                    }
                });
    }

    //查询商品
    private void loadSearch() {
        Api.getInstance().getCommodities(edSearchName, page, UIConfig.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PageBean<GviBean.ListBean>>(getActivity()) {
                    @Override
                    public void onSuccess(BaseBean<PageBean<GviBean.ListBean>> bean) {
                        List<GviBean.ListBean> list = bean.getData().getList();
                        if (page == UIConfig.PAGE_DEFAULT) {
                            mSearchList.clear();
                        }
                        mSearchList.addAll(list);
                        if (list.size() < UIConfig.PAGE_SIZE) {
                            mAdapter.loadMoreEnd(true);
                        } else {
                            mAdapter.loadMoreComplete();
                        }
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }
                });
    }

}
