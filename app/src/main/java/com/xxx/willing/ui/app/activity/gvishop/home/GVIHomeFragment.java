package com.xxx.willing.ui.app.activity.gvishop.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
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
import com.xxx.willing.model.http.bean.SearchShopBean;
import com.xxx.willing.model.http.bean.base.BaseBean;
import com.xxx.willing.model.http.bean.base.PageBean;
import com.xxx.willing.model.utils.ToastUtil;
import com.xxx.willing.ui.app.activity.gvishop.home.adapter.GviAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author FM
 * @desc 商城首页
 * @date 2019-12-04
 */

public class GVIHomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener,GviAdapter.OnClickListener {

    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.ed_search)
    EditText mEditSearch;

    private int page = UIConfig.PAGE_DEFAULT;
    private GviAdapter adapter;
    private List<GviBean> mList = new ArrayList<>();
    private String edSearchName;

    @Override
    protected int getLayoutId() {
        return R.layout.gvi_home;
    }

    @Override
    protected void initData() {
        adapter = new GviAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(adapter);
        mRefresh.setOnRefreshListener(this);
        adapter.setOnClickListener(this);
        adapter.setOnLoadMoreListener(this, mRecycler);

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

    @Override
    public void onOnClickListener(String name, int id) {
        BaseWebShopActivity.actionStart(getActivity(), HttpConfig.SHOP_DETAIL_URL, name, id);
    }

    @OnClick({R.id.ed_search})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.ed_search:
                edSearchName = mEditSearch.getText().toString();
                if (edSearchName.isEmpty()) {
                    ToastUtil.showToast(getString(R.string.search_default_null));
                } else {
                    loadSearch();
                }
                break;
        }
    }

    //获取商城
    private void loadData() {
        Api.getInstance().getShop()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<List<GviBean>>(getActivity()) {
                    @Override
                    public void onSuccess(BaseBean<List<GviBean>> bean) {
                        if (bean == null) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            adapter.loadMoreEnd(true);
                            return;
                        }

                        List<GviBean> list = bean.getData();
                        if (list == null || list.size() == 0 && page == UIConfig.PAGE_DEFAULT) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            adapter.loadMoreEnd(true);
                            return;
                        }
                        mRecycler.setVisibility(View.VISIBLE);
                        mNotData.setVisibility(View.GONE);
                        if (page == UIConfig.PAGE_DEFAULT) {
                            mList.clear();
                        }
                        mList.addAll(list);
                        if (list.size() < UIConfig.PAGE_SIZE) {
                            adapter.loadMoreEnd(true);
                        } else {
                            adapter.loadMoreComplete();
                        }
                        adapter.notifyDataSetChanged();
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
                .subscribe(new ApiCallback<PageBean<SearchShopBean>>(getActivity()) {
                    @Override
                    public void onSuccess(BaseBean<PageBean<SearchShopBean>> bean) {
                        if (bean != null) {
                            loadData();
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
