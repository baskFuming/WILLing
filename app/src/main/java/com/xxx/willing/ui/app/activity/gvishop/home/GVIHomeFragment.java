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
import com.xxx.willing.config.UIConfig;
import com.xxx.willing.model.http.Api;
import com.xxx.willing.model.http.ApiCallback;
import com.xxx.willing.model.http.bean.GviBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.sp.SharedConst;
import com.xxx.willing.model.sp.SharedPreferencesUtil;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.app.activity.gvishop.home.adapter.GviAdapter;
import com.xxx.willing.view.SearchEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnTextChanged;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author FM
 * @desc 商城首页
 * @date 2019-12-04
 */

public class GVIHomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

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
    private List<GviBean> mSearchList = new ArrayList<>();
    private GviAdapter mSearchAdapter;

    private String edSearchName;

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
        mAdapter.setOnClickListener((name, commodityId, brandId) -> {
            //跳转到商城web
            BaseWebShopActivity.actionStart(getActivity(), name, commodityId, brandId);
        });

        mSearchAdapter = new GviAdapter(mSearchList);
        mSearchRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mSearchRecycler.setAdapter(mSearchAdapter);
        mSearchAdapter.setOnLoadMoreListener(this, mSearchRecycler);
        mSearchAdapter.setOnClickListener((name, commodityId, brandId) -> {
            //跳转到商城web
            BaseWebShopActivity.actionStart(getActivity(),  name, commodityId, brandId);
        });

        loadData();

        //搜索商品
        mSearch.setOnSearchClickListener(view -> {
            edSearchName = mSearch.getText().toString();
            if (edSearchName.isEmpty()) {
                ToastUtil.showToast(getString(R.string.search_default_null));
            } else {
                mRefresh.setVisibility(View.GONE);
                mSearchRecycler.setVisibility(View.VISIBLE);
                page = UIConfig.PAGE_DEFAULT;
                loadSearch();
            }
        });
    }

    @OnTextChanged(R.id.gvi_search_shop1)
    public void OnTextChanged(CharSequence charSequence) {
        if (charSequence.length() == 0) {
            mRefresh.setVisibility(View.VISIBLE);
            mSearchRecycler.setVisibility(View.GONE);
        }
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mRefresh.post(this::loadData);
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
                .subscribe(new ApiCallback<List<GviBean>>(getActivity()) {
                    @Override
                    public void onSuccess(BaseBean<List<GviBean>> bean) {
                        List<GviBean> list = bean.getData();
                        if (page == UIConfig.PAGE_DEFAULT) {
                            mSearchList.clear();
                        }
                        mSearchList.addAll(list);
                        if (list.size() < UIConfig.PAGE_SIZE) {
                            mSearchAdapter.loadMoreEnd(true);
                        } else {
                            mSearchAdapter.loadMoreComplete();
                        }
                        mSearchAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }
                });
    }

}

